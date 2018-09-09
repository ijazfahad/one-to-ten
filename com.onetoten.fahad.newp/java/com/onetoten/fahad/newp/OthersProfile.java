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

public class OthersProfile extends ActionBarActivity {
    public static float avg = 0.0f;
    public static int skip = 0;
    public static float sum = 0.0f;
    public String Bio;
    public String CUobjectID;
    public String Name;
    public String OName;
    public String Oschool;
    public String Ousername;
    public ImageView Ovfied;
    public String School;
    public String Svfied;
    otherProfileAdapter adapter1;
    public ArrayList<Double> arrr = new ArrayList();
    TextView bio;
    public int c;
    public Context context;
    public int counter = 0;
    public int counter1 = 0;
    public String gen;
    ListView listview;
    ProgressDialog mProgressDialog;
    otherProfilePost name;
    List<ParseObject> ob;
    TextView pRating;
    private List<otherProfilePost> post2 = null;
    public String rel;
    TextView school;
    TextView userName;

    private class CustomDataTask extends AsyncTask<Void, Void, Void> {
        private CustomDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            new ParseQuery("_User").getInBackground(OthersProfile.this.CUobjectID, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    OthersProfile.this.Bio = object.get("Bio").toString();
                    OthersProfile.this.gen = object.get("gender").toString();
                    OthersProfile.this.rel = object.get("relationship").toString();
                }
            });
            new ParseQuery("_User").getInBackground(OthersProfile.this.CUobjectID, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    OthersProfile.this.Svfied = object.get("emailVerified").toString();
                    String g = "false";
                    if (OthersProfile.this.Svfied.equals("true")) {
                        OthersProfile.this.Ovfied.setImageResource(R.drawable.ic_verified_user_black_36dp);
                    }
                    final ParseFile fileObject = (ParseFile) object.get("ProPicture");
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                fileObject.getUrl();
                                ImageView image = (ImageView) OthersProfile.this.findViewById(R.id.Ppicture);
                                ((ImageView) OthersProfile.this.findViewById(R.id.smallnew)).setImageBitmap(bmp);
                                Picasso.with(OthersProfile.this).load(fileObject.getUrl()).transform(new Blur(OthersProfile.this, 10)).into(image);
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
            OthersProfile.this.mProgressDialog = new ProgressDialog(OthersProfile.this);
            OthersProfile.this.mProgressDialog.setTitle("Profile");
            OthersProfile.this.mProgressDialog.setMessage("Loading...");
            OthersProfile.this.mProgressDialog.setIndeterminate(false);
            OthersProfile.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            OthersProfile.this.post2 = new ArrayList();
            try {
                ParseQuery<ParseObject> query = new ParseQuery(ParseUser.getCurrentUser().get("SchoolClass").toString());
                query.whereEqualTo("username", OthersProfile.this.Ousername);
                query.orderByDescending("createdAt");
                query.setLimit(1000);
                OthersProfile.this.ob = query.find();
                for (ParseObject country : OthersProfile.this.ob) {
                    ParseFile image = (ParseFile) country.get("pictures");
                    ParseFile dp = (ParseFile) country.get("dp");
                    otherProfilePost map = new otherProfilePost();
                    map.setName((String) country.get("Name"));
                    map.setOpDate(country.getCreatedAt());
                    map.setOpAverageRating((String) country.get("AvgRating"));
                    map.setRelationship((String) country.get("relationship"));
                    map.setGender((String) country.get("gender"));
                    map.setOpcaption((String) country.get("caption"));
                    map.setOpObjectId(country.getObjectId());
                    map.setOpPicture(image.getUrl());
                    map.setOdp(dp.getUrl());
                    Object a = country.get("AvgRating");
                    Object b = country.get("TotalRatings");
                    String F = String.valueOf(a);
                    String G = String.valueOf(b);
                    Double h = Double.valueOf(Double.parseDouble(F));
                    Double I = Double.valueOf(Double.parseDouble(G));
                    OthersProfile.this.arrr.add(OthersProfile.this.counter, h);
                    OthersProfile.this.post2.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            OthersProfile.this.listview = (ListView) OthersProfile.this.findViewById(R.id.PlistView);
            OthersProfile.this.adapter1 = new otherProfileAdapter(OthersProfile.this, OthersProfile.this.post2);
            OthersProfile.this.listview.setAdapter(OthersProfile.this.adapter1);
            int f = OthersProfile.this.listview.getCount();
            System.out.println("f" + f);
            if (f == 0) {
                OthersProfile.skip = -10;
                Toast.makeText(OthersProfile.this, "No pictures uploaded yet", 1).show();
            }
            System.out.println("listview" + OthersProfile.this.listview);
            OthersProfile.this.mProgressDialog.dismiss();
            OthersProfile.this.c = OthersProfile.this.listview.getCount();
            String d = String.valueOf(OthersProfile.this.c);
            OthersProfile.this.mProgressDialog.dismiss();
            System.out.println("avg" + OthersProfile.this.arrr);
            OthersProfile.sum = 0.0f;
            for (int j = 0; j < OthersProfile.this.arrr.size(); j++) {
                OthersProfile.sum = (float) (((Double) OthersProfile.this.arrr.get(j)).doubleValue() + ((double) OthersProfile.sum));
            }
            System.out.println("summ" + OthersProfile.sum);
            OthersProfile.avg = OthersProfile.sum / ((float) OthersProfile.this.c);
            System.out.println("avgggg" + OthersProfile.avg);
            String pavg = String.valueOf(OthersProfile.avg);
            OthersProfile.avg = 0.0f;
            OthersProfile.sum = 0.0f;
            OthersProfile.this.arrr.clear();
            System.out.println("clear array" + OthersProfile.this.arrr);
            ((TextView) OthersProfile.this.findViewById(R.id.pRating)).setText(pavg);
            ParseUser rating = ParseUser.getCurrentUser();
            rating.put("ProfileRating", pavg);
            System.out.println(pavg);
            rating.saveInBackground();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);
        Intent i = getIntent();
        this.CUobjectID = i.getStringExtra("CUobjectId");
        this.OName = i.getStringExtra("Name");
        this.Ousername = i.getStringExtra("UserName");
        this.Oschool = i.getStringExtra("School");
        TextView PFname = (TextView) findViewById(R.id.sFname);
        this.pRating = (TextView) findViewById(R.id.pRating);
        this.school = (TextView) findViewById(R.id.School);
        this.userName = (TextView) findViewById(R.id.pUserName);
        this.Ovfied = (ImageView) findViewById(R.id.verificationBadge);
        PFname.setText(this.OName);
        this.school.setText(this.Oschool);
        this.userName.setText(this.Ousername);
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
        Intent i = new Intent(this, otherProfilesBio.class);
        i.putExtra("Bio", this.Bio);
        i.putExtra("Relation", this.rel);
        i.putExtra("Gender", this.gen);
        startActivity(i);
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
        startActivity(new Intent(this, Profile.class));
    }

    public void EditProfile(View view) {
        startActivity(new Intent(this, EditProfile1.class));
        finish();
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
