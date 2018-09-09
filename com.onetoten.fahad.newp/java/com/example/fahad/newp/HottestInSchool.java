package com.example.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.onetoten.fahad.newp.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class HottestInSchool extends ActionBarActivity {
    HottestInSchoolAdapter adapter;
    ListView listview;
    ProgressDialog mProgressDialog;
    List<ParseObject> ob;
    private List<HottestPost> post2 = null;
    public boolean set;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            HottestInSchool.this.mProgressDialog = new ProgressDialog(HottestInSchool.this);
            HottestInSchool.this.mProgressDialog.setTitle("Hottest In School");
            HottestInSchool.this.mProgressDialog.setMessage("Loading...");
            HottestInSchool.this.mProgressDialog.setIndeterminate(false);
            HottestInSchool.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            HottestInSchool.this.post2 = new ArrayList();
            try {
                String SchoolName = ParseUser.getCurrentUser().get("SchoolName").toString();
                ParseQuery<ParseObject> query = new ParseQuery("_User");
                query.whereEqualTo("SchoolName", SchoolName);
                query.orderByDescending("ProfileRating");
                query.whereNotEqualTo("ProfileRating", "NaN");
                HottestInSchool.this.ob = query.find();
                for (ParseObject country : HottestInSchool.this.ob) {
                    ParseFile dp = (ParseFile) country.get("ProPicture");
                    String r = country.getObjectId();
                    HottestPost map = new HottestPost();
                    map.setHoUsername((String) country.get("username"));
                    map.setHoRating((String) country.get("ProfileRating"));
                    map.setHoName((String) country.get("Name"));
                    map.setHoBio((String) country.get("Bio"));
                    map.setHoSchool((String) country.get("SchoolName"));
                    map.setHoCUobjectId(r);
                    map.setHoDp(dp.getUrl());
                    HottestInSchool.this.post2.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            HottestInSchool.this.listview = (ListView) HottestInSchool.this.findViewById(R.id.button3);
            HottestInSchool.this.adapter = new HottestInSchoolAdapter(HottestInSchool.this, HottestInSchool.this.post2);
            HottestInSchool.this.listview.setAdapter(HottestInSchool.this.adapter);
            HottestInSchool.this.mProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hottest_in_school);
        new RemoteDataTask().execute(new Void[0]);
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

    public void Upload(View view) {
        startActivity(new Intent(this, Upload_Image.class));
        finish();
    }

    public void home(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void Search(View view) {
        startActivity(new Intent(this, Search.class));
    }

    public void hottestInSchool(View view) {
        new RemoteDataTask().execute(new Void[0]);
    }
}
