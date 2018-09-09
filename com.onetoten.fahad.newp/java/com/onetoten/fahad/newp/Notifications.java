package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends ActionBarActivity {
    public static int skip = 0;
    notificationAdapter adapter;
    public ArrayList<Double> arrr = new ArrayList();
    ListView listview;
    ProgressDialog mProgressDialog;
    List<ParseObject> ob;
    public String objectid;
    private List<notificationPost> post = null;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Notifications.this.mProgressDialog = new ProgressDialog(Notifications.this);
            Notifications.this.mProgressDialog.setTitle("Notification");
            Notifications.this.mProgressDialog.setMessage("Loading...");
            Notifications.this.mProgressDialog.setIndeterminate(false);
            Notifications.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            Notifications.this.post = new ArrayList();
            try {
                String u = ParseUser.getCurrentUser().get("Name").toString();
                ParseQuery<ParseObject> query = new ParseQuery("RatingsAndNames");
                query.whereEqualTo("CreatedFor", u);
                query.orderByDescending("createdAt");
                Notifications.this.ob = query.find();
                for (ParseObject country : Notifications.this.ob) {
                    ParseFile dp = (ParseFile) country.get("dp");
                    String r = country.getObjectId();
                    country.saveInBackground();
                    notificationPost map = new notificationPost();
                    map.setnRating((String) country.get("Rating"));
                    map.setCreatedby((String) country.get("CreatedBy"));
                    map.setCreatedbyDp(dp.getUrl());
                    Notifications.this.post.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Notifications.this.listview = (ListView) Notifications.this.findViewById(R.id.nlistView);
            Notifications.this.adapter = new notificationAdapter(Notifications.this, Notifications.this.post);
            Notifications.this.listview.setAdapter(Notifications.this.adapter);
            Notifications.this.mProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        new RemoteDataTask().execute(new Void[0]);
    }

    public void back(View view) {
        finish();
    }
}
