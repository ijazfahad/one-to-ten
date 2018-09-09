package com.example.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.onetoten.fahad.newp.BuildConfig;
import com.onetoten.fahad.newp.R;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    public static int checker = 1;
    public static int counter = 0;
    public static TextView notify;
    public static int skip = 0;
    public String Fname;
    public String ObjectId;
    ListViewAdapter adapter;
    public ArrayList<Double> arrr = new ArrayList();
    public ParseFile f;
    public Object fileObject1;
    ListView listview;
    ProgressDialog mProgressDialog;
    List<ParseObject> ob;
    List<ParseObject> ob1;
    public String objectid;
    private List<HomePost> post = null;
    public boolean set;
    public boolean tick = false;

    private class RemoteDataTask2 extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask2() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.mProgressDialog = new ProgressDialog(MainActivity.this);
            MainActivity.this.mProgressDialog.setTitle("Trending");
            MainActivity.this.mProgressDialog.setMessage("Loading...");
            MainActivity.this.mProgressDialog.setIndeterminate(false);
            MainActivity.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            MainActivity.this.post = new ArrayList();
            try {
                ParseQuery<ParseObject> query = new ParseQuery(ParseUser.getCurrentUser().get("SchoolClass").toString());
                query.orderByDescending("TotalRatings1");
                query.setLimit(150);
                MainActivity.this.ob = query.find();
                for (ParseObject country : MainActivity.this.ob) {
                    ParseFile image = (ParseFile) country.get("pictures");
                    ParseFile dp = (ParseFile) country.get("dp");
                    HomePost map = new HomePost();
                    map.setAvgRating((String) country.get("AvgRating"));
                    map.setSchool((String) country.get("school"));
                    map.setUsername((String) country.get("username"));
                    map.setName((String) country.get("Name"));
                    MainActivity.this.Fname = country.get("Name").toString();
                    MainActivity.this.objectid = country.getObjectId();
                    map.setCUobjectID((String) country.get("CUobjectId"));
                    map.setCreatedAt(country.getCreatedAt());
                    map.setCaption((String) country.get("caption"));
                    map.setObjectId(country.getObjectId());
                    MainActivity.this.ObjectId = country.getObjectId();
                    map.setPicture(image.getUrl());
                    map.setPpicture(dp.getUrl());
                    MainActivity.this.post.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            MainActivity.this.listview = (ListView) MainActivity.this.findViewById(R.id.imageView45);
            MainActivity.this.adapter = new ListViewAdapter(MainActivity.this, MainActivity.this.post);
            MainActivity.this.listview.setAdapter(MainActivity.this.adapter);
            int f = MainActivity.this.listview.getCount();
            System.out.println("f" + f);
            if (f == 0) {
                MainActivity.skip = -10;
                Toast.makeText(MainActivity.this, "No more pictures, All pictures will be reloaded on the next click", 1).show();
            }
            MainActivity.this.mProgressDialog.dismiss();
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.mProgressDialog = new ProgressDialog(MainActivity.this);
            MainActivity.this.mProgressDialog.setTitle("Home");
            MainActivity.this.mProgressDialog.setMessage("Loading...");
            MainActivity.this.mProgressDialog.setIndeterminate(false);
            MainActivity.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            MainActivity.this.post = new ArrayList();
            try {
                ParseQuery<ParseObject> query = new ParseQuery(ParseUser.getCurrentUser().get("SchoolClass").toString());
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.setSkip(MainActivity.skip);
                MainActivity.this.ob = query.find();
                if (MainActivity.this.ob.equals(BuildConfig.FLAVOR)) {
                    Toast.makeText(MainActivity.this, "no photos uploaded yet", 1).show();
                } else {
                    for (ParseObject country : MainActivity.this.ob) {
                        ParseFile image = (ParseFile) country.get("pictures");
                        ParseFile dp = (ParseFile) country.get("dp");
                        HomePost map = new HomePost();
                        map.setAvgRating((String) country.get("AvgRating"));
                        map.setSchool((String) country.get("school"));
                        map.setUsername((String) country.get("username"));
                        map.setName((String) country.get("Name"));
                        MainActivity.this.Fname = country.get("Name").toString();
                        MainActivity.this.objectid = country.getObjectId();
                        map.setCUobjectID((String) country.get("CUobjectId"));
                        map.setCreatedAt(country.getCreatedAt());
                        map.setCaption((String) country.get("caption"));
                        map.setObjectId(country.getObjectId());
                        MainActivity.this.ObjectId = country.getObjectId();
                        map.setPicture(image.getUrl());
                        map.setPpicture(dp.getUrl());
                        MainActivity.this.post.add(map);
                    }
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            MainActivity.this.listview = (ListView) MainActivity.this.findViewById(R.id.imageView45);
            MainActivity.this.adapter = new ListViewAdapter(MainActivity.this, MainActivity.this.post);
            MainActivity.this.listview.setAdapter(MainActivity.this.adapter);
            int f = MainActivity.this.listview.getCount();
            System.out.println("f" + f);
            if (f == 0) {
                MainActivity.skip = -10;
                Toast.makeText(MainActivity.this, "No more pictures, All pictures will be reloaded on the next click", 1).show();
            }
            MainActivity.this.mProgressDialog.dismiss();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hottest_single_item);
        ListView listview = (ListView) findViewById(R.id.imageView45);
        notify = (TextView) findViewById(R.id.relativeLayout3);
        Parse.initialize(this, "zkBKLjeSqSP7Oimm5ffOhRYOh9GZMcLzNJi9QaOE", "SrZoPcDRKkPV1LgRU0LpRiYAnokYQh1yHfCxGXEL");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        if (ParseUser.getCurrentUser() != null) {
            Toast.makeText(this, "tap on the picture to rate it", 0).show();
            new RemoteDataTask().execute(new Void[0]);
            new ParseQuery("anon").getInBackground("vsInpHMdjx", new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    final ParseFile fileObject = (ParseFile) object.get("anon");
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                MainActivity.this.f = new ParseFile("file.png", data);
                                MainActivity.this.fileObject1 = fileObject.getUrl();
                                System.out.println("fileobject" + MainActivity.this.fileObject1);
                                return;
                            }
                            Log.d("test", "There was a problem downloading the data.");
                        }
                    });
                }
            });
            return;
        }
        startActivity(new Intent(this, start_page.class));
        finish();
    }

    public void trending(View view) {
        new RemoteDataTask2().execute(new Void[0]);
        skip = 0;
        counter = 0;
        String u = ParseUser.getCurrentUser().get("Name").toString();
        ParseQuery<ParseObject> q = new ParseQuery("RatingsAndNames");
        q.whereEqualTo("value", "1");
        q.whereEqualTo("CreatedFor", u);
        try {
            this.ob1 = q.find();
            for (ParseObject c1 : this.ob1) {
                counter++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        notify.setText(String.valueOf(counter));
    }

    public void profileclick(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        startActivity(new Intent(this, Profile.class));
        finish();
    }

    public void logout(View view) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseUser.logOut();
        ParseUser.logOut();
        startActivity(new Intent(this, start_page.class));
        finish();
    }

    public void popUp(View v) {
    }

    public void Upload(View view) {
        startActivity(new Intent(this, Upload_Image.class));
        finish();
    }

    public void resend(View view) {
        ParseUser u = ParseUser.getCurrentUser();
        String original = u.getEmail();
        u.setEmail("hello@hellohello.com");
        u.setUsername("hellomello@yellow");
        u.saveInBackground();
        u.setUsername(original);
        u.setEmail(original);
        u.saveInBackground();
    }

    public void home(View view) {
        new RemoteDataTask().execute(new Void[0]);
        skip = 0;
        counter = 0;
        String u = ParseUser.getCurrentUser().get("Name").toString();
        ParseQuery<ParseObject> q = new ParseQuery("RatingsAndNames");
        q.whereEqualTo("value", "1");
        q.whereEqualTo("CreatedFor", u);
        try {
            this.ob1 = q.find();
            for (ParseObject c1 : this.ob1) {
                counter++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        notify.setText(String.valueOf(counter));
    }

    public void Search(View view) {
        startActivity(new Intent(this, Search.class));
    }

    public void loadmore(View view) {
        new RemoteDataTask().execute(new Void[0]);
        skip += 10;
    }

    public void HottestInSchool(View view) {
        startActivity(new Intent(this, HottestInSchool.class));
        finish();
    }

    public void checkBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        this.tick = checked;
        if (checked) {
            checker = 1;
        } else {
            checker = 0;
        }
        System.out.println("check box" + checked);
        System.out.println("check box" + this.tick);
        System.out.println("Checkersss" + checker);
    }

    public void notifications(View view) {
        startActivity(new Intent(this, Notifications.class));
        counter = 0;
        String u = ParseUser.getCurrentUser().get("Name").toString();
        String m = "0";
        ParseQuery<ParseObject> q = new ParseQuery("RatingsAndNames");
        q.whereEqualTo("value", "1");
        q.whereEqualTo("CreatedFor", u);
        try {
            this.ob1 = q.find();
            for (ParseObject c1 : this.ob1) {
                c1.put("value", m);
                c1.saveInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String c = String.valueOf(counter);
    }

    public void onResume() {
        super.onResume();
        counter = 0;
        String u = ParseUser.getCurrentUser().get("Name").toString();
        ParseQuery<ParseObject> q = new ParseQuery("RatingsAndNames");
        q.whereEqualTo("value", "1");
        q.whereEqualTo("CreatedFor", u);
        try {
            this.ob1 = q.find();
            for (ParseObject c1 : this.ob1) {
                counter++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        notify.setText(String.valueOf(counter));
    }

    public void onRestart() {
        super.onRestart();
    }
}
