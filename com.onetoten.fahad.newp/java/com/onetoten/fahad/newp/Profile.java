package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Profile extends ActionBarActivity {
    public static float avg = 0.0f;
    public static int skip = 0;
    public static float sum = 0.0f;
    public String Bio;
    public String Name;
    public String School;
    ProfileAdapter adapter1;
    public ArrayList<Double> arrr = new ArrayList();
    TextView bio;
    public int c;
    public Context context;
    public int counter = 0;
    public int counter1 = 0;
    ListView listview;
    ProgressDialog mProgressDialog;
    ProfilePost name;
    List<ParseObject> ob;
    TextView pRating;
    private List<ProfilePost> post1 = null;
    TextView school;
    TextView userName;
    ImageView verified;

    private class CustomDataTask extends AsyncTask<Void, Void, Void> {
        private CustomDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            ParseUser current = ParseUser.getCurrentUser();
            String Everify = current.get("emailVerified").toString();
            String g = "false";
            System.out.println(Everify);
            if (Everify.equals("true")) {
                Profile.this.verified.setImageResource(R.drawable.ic_verified_user_black_36dp);
            }
            new ParseQuery("_User").getInBackground(current.getObjectId(), new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    final ParseFile fileObject = (ParseFile) object.get("ProPicture");
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                fileObject.getUrl();
                                ImageView image = (ImageView) Profile.this.findViewById(R.id.Ppicture);
                                ((ImageView) Profile.this.findViewById(R.id.smallnew)).setImageBitmap(bmp);
                                Picasso.with(Profile.this).load(fileObject.getUrl()).transform(new Blur(Profile.this, 10)).into(image);
                                return;
                            }
                            Log.d("test", "There was a problem downloading the data.");
                        }
                    });
                }
            });
            return null;
        }

        protected void onPostExecute(Void result) {
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Profile.this.mProgressDialog = new ProgressDialog(Profile.this);
            Profile.this.mProgressDialog.setTitle("Profile");
            Profile.this.mProgressDialog.setMessage("Loading...");
            Profile.this.mProgressDialog.setIndeterminate(false);
            Profile.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            Profile.this.post1 = new ArrayList();
            try {
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseQuery<ParseObject> query = new ParseQuery(currentUser.get("SchoolClass").toString());
                query.whereEqualTo("username", currentUser.getUsername());
                query.orderByDescending("createdAt");
                query.setLimit(1000);
                Profile.this.ob = query.find();
                for (ParseObject country : Profile.this.ob) {
                    ParseFile image = (ParseFile) country.get("pictures");
                    ParseFile dp = (ParseFile) country.get("dp");
                    ProfilePost map = new ProfilePost();
                    map.setPicObjectId(country.getObjectId());
                    map.setName((String) country.get("Name"));
                    map.setpDate(country.getCreatedAt());
                    map.setpAverageRating((String) country.get("AvgRating"));
                    map.setPcaption((String) country.get("caption"));
                    map.setpObjectId(country.getObjectId());
                    map.setpPicture(image.getUrl());
                    map.setDp(dp.getUrl());
                    Object a = country.get("AvgRating");
                    Object b = country.get("TotalRatings");
                    String F = String.valueOf(a);
                    String G = String.valueOf(b);
                    Double h = Double.valueOf(Double.parseDouble(F));
                    Double I = Double.valueOf(Double.parseDouble(G));
                    Profile.this.arrr.add(Profile.this.counter, h);
                    Profile.this.post1.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Profile.this.listview = (ListView) Profile.this.findViewById(R.id.PlistView);
            Profile.this.adapter1 = new ProfileAdapter(Profile.this, Profile.this.post1);
            Profile.this.listview.setAdapter(Profile.this.adapter1);
            int f = Profile.this.listview.getCount();
            System.out.println("f" + f);
            if (f == 0) {
                Profile.skip = -10;
                Toast.makeText(Profile.this, "No pictures uploaded yet", 1).show();
            }
            System.out.println("listview" + Profile.this.listview);
            Profile.this.mProgressDialog.dismiss();
            Profile.this.c = Profile.this.listview.getCount();
            String d = String.valueOf(Profile.this.c);
            Profile.this.mProgressDialog.dismiss();
            System.out.println("avg" + Profile.this.arrr);
            Profile.sum = 0.0f;
            for (int j = 0; j < Profile.this.arrr.size(); j++) {
                Profile.sum = (float) (((Double) Profile.this.arrr.get(j)).doubleValue() + ((double) Profile.sum));
            }
            System.out.println("summ" + Profile.sum);
            Profile.avg = Profile.sum / ((float) Profile.this.c);
            System.out.println("avgggg" + Profile.avg);
            String pavg = String.valueOf(Profile.avg);
            Profile.avg = 0.0f;
            Profile.sum = 0.0f;
            Profile.this.arrr.clear();
            System.out.println("clear array" + Profile.this.arrr);
            ((TextView) Profile.this.findViewById(R.id.pRating)).setText(pavg);
            ParseUser rating = ParseUser.getCurrentUser();
            rating.put("ProfileRating", pavg);
            System.out.println(pavg);
            rating.saveInBackground();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView PFname = (TextView) findViewById(R.id.sFname);
        this.pRating = (TextView) findViewById(R.id.pRating);
        this.school = (TextView) findViewById(R.id.School);
        this.userName = (TextView) findViewById(R.id.pUserName);
        this.verified = (ImageView) findViewById(R.id.verificationBadge);
        ParseUser current = ParseUser.getCurrentUser();
        this.Name = current.get("Name").toString();
        PFname.setText(this.Name);
        this.School = current.get("SchoolName").toString();
        this.school.setText(this.School);
        this.userName.setText(current.getUsername());
        new CustomDataTask().execute(new Void[0]);
        new RemoteDataTask().execute(new Void[0]);
    }

    public void logout(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseUser.logOut();
        ParseUser.logOut();
        startActivity(new Intent(this, start_page.class));
        finish();
    }

    public void refresh(View view) {
        avg = 0.0f;
        sum = 0.0f;
        skip = 0;
        new RemoteDataTask().execute(new Void[0]);
    }

    public void bio(View view) {
        startActivity(new Intent(this, BioPage.class));
    }

    public void loadmore(View view) {
        new RemoteDataTask().execute(new Void[0]);
        skip += 10;
    }

    public void Upload(View view) {
        startActivity(new Intent(this, Upload_Image.class));
        finish();
    }

    public void home(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void profileclick(View view) {
        new RemoteDataTask().execute(new Void[0]);
    }

    public void EditProfile(View view) {
        startActivity(new Intent(this, EditProfile1.class));
    }

    public void HottestInSchool(View view) {
        startActivity(new Intent(this, HottestInSchool.class));
        finish();
    }

    public void Search(View view) {
        startActivity(new Intent(this, Search.class));
    }

    public void onPause() {
        super.onPause();
    }
}
