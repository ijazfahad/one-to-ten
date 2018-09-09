package com.example.fahad.newp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.onetoten.fahad.newp.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class Search extends ActionBarActivity {
    public String SnameOrUserName;
    searchAdapter adapter;
    ListView listview;
    ProgressDialog mProgressDialog;
    List<ParseObject> ob;
    private List<searchPost> post2 = null;
    public EditText sNameOrUserName;
    public boolean set;

    private class RemoteDataTask2 extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask2() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Search.this.mProgressDialog = new ProgressDialog(Search.this);
            Search.this.mProgressDialog.setTitle("Searching");
            Search.this.mProgressDialog.setIndeterminate(false);
            Search.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            Search.this.post2 = new ArrayList();
            try {
                String SchoolName = ParseUser.getCurrentUser().get("SchoolName").toString();
                ParseQuery<ParseObject> query = new ParseQuery("_User");
                query.whereEqualTo("SchoolName", SchoolName);
                query.whereContains("username", Search.this.SnameOrUserName);
                Search.this.ob = query.find();
                for (ParseObject country : Search.this.ob) {
                    ParseFile dp = (ParseFile) country.get("ProPicture");
                    String r = country.getObjectId();
                    searchPost map = new searchPost();
                    map.setSearchUserName((String) country.get("username"));
                    map.setSearchRating((String) country.get("ProfileRating"));
                    map.setSearchName((String) country.get("Name"));
                    map.setSearchBio((String) country.get("Bio"));
                    map.setSearchSchool((String) country.get("SchoolName"));
                    map.setSearchObjectId(r);
                    map.setSearchDp(dp.getUrl());
                    Search.this.post2.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Search.this.listview = (ListView) Search.this.findViewById(R.id.SearchlistView);
            Search.this.adapter = new searchAdapter(Search.this, Search.this.post2);
            Search.this.listview.setAdapter(Search.this.adapter);
            Search.this.mProgressDialog.dismiss();
        }
    }

    private class RemoteDataTask3 extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask3() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Search.this.mProgressDialog = new ProgressDialog(Search.this);
            Search.this.mProgressDialog.setTitle("Searching");
            Search.this.mProgressDialog.setIndeterminate(false);
            Search.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            Search.this.post2 = new ArrayList();
            try {
                String SchoolName = ParseUser.getCurrentUser().get("SchoolName").toString();
                ParseQuery<ParseObject> query = new ParseQuery("_User");
                query.whereContains("Name", Search.this.SnameOrUserName);
                Search.this.ob = query.find();
                for (ParseObject country : Search.this.ob) {
                    ParseFile dp = (ParseFile) country.get("ProPicture");
                    String r = country.getObjectId();
                    searchPost map = new searchPost();
                    map.setSearchUserName((String) country.get("username"));
                    map.setSearchRating((String) country.get("ProfileRating"));
                    map.setSearchName((String) country.get("Name"));
                    map.setSearchBio((String) country.get("Bio"));
                    map.setSearchSchool((String) country.get("SchoolName"));
                    map.setSearchObjectId(r);
                    map.setSearchDp(dp.getUrl());
                    Search.this.post2.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Search.this.listview = (ListView) Search.this.findViewById(R.id.SearchlistView);
            Search.this.adapter = new searchAdapter(Search.this, Search.this.post2);
            Search.this.listview.setAdapter(Search.this.adapter);
            Search.this.mProgressDialog.dismiss();
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Search.this.mProgressDialog = new ProgressDialog(Search.this);
            Search.this.mProgressDialog.setTitle("Searching");
            Search.this.mProgressDialog.setIndeterminate(false);
            Search.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            Search.this.post2 = new ArrayList();
            try {
                String SchoolName = ParseUser.getCurrentUser().get("SchoolName").toString();
                ParseQuery<ParseObject> query = new ParseQuery("_User");
                query.whereEqualTo("SchoolName", SchoolName);
                query.whereContains("Name", Search.this.SnameOrUserName);
                Search.this.ob = query.find();
                for (ParseObject country : Search.this.ob) {
                    ParseFile dp = (ParseFile) country.get("ProPicture");
                    String r = country.getObjectId();
                    searchPost map = new searchPost();
                    map.setSearchUserName((String) country.get("username"));
                    map.setSearchRating((String) country.get("ProfileRating"));
                    map.setSearchName((String) country.get("Name"));
                    map.setSearchBio((String) country.get("Bio"));
                    map.setSearchSchool((String) country.get("SchoolName"));
                    map.setSearchObjectId(r);
                    map.setSearchDp(dp.getUrl());
                    Search.this.post2.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Search.this.listview = (ListView) Search.this.findViewById(R.id.SearchlistView);
            Search.this.adapter = new searchAdapter(Search.this, Search.this.post2);
            Search.this.listview.setAdapter(Search.this.adapter);
            Search.this.mProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.sNameOrUserName = (EditText) findViewById(R.id.SearchText);
    }

    public void searchDatabase(View view) {
        this.SnameOrUserName = this.sNameOrUserName.getText().toString();
        new RemoteDataTask().execute(new Void[0]);
    }

    public void byUserName(View view) {
        this.SnameOrUserName = this.sNameOrUserName.getText().toString();
        new RemoteDataTask2().execute(new Void[0]);
    }

    public void allSchools(View view) {
        this.SnameOrUserName = this.sNameOrUserName.getText().toString();
        new RemoteDataTask3().execute(new Void[0]);
    }

    public void back(View view) {
        finish();
    }
}
