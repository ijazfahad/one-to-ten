package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class EditProfile extends ActionBarActivity {
    public String Bio;
    public String ChangePassword;
    public String Name;
    public String UBio;
    public String UChangePassword;
    public String UName;
    public String Ugender;
    public String Urelationship;
    public EditText bio;
    public EditText cpassword;
    public EditText gender;
    public ParseObject imgupload;
    ProgressDialog mProgressDialog;
    public EditText name;
    public Object obj;
    public ParseFile p;
    public EditText relationship;
    public ProgressDialog uProgressDialog;

    private class CustomTask extends AsyncTask<Void, Void, Void> {
        private CustomTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            EditProfile.this.uProgressDialog = new ProgressDialog(EditProfile.this);
            EditProfile.this.uProgressDialog.setProgressStyle(0);
            EditProfile.this.uProgressDialog.setTitle("Updating Profile...");
            EditProfile.this.uProgressDialog.setIndeterminate(false);
            EditProfile.this.uProgressDialog.show();
        }

        protected Void doInBackground(Void... param) {
            String email = ParseUser.getCurrentUser().getUsername();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("pic");
            query.whereEqualTo("username", email);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed." + e);
                        return;
                    }
                    object.getObjectId();
                    EditProfile.this.obj = object;
                    EditProfile.this.p = (ParseFile) object.get("ProPicture");
                    ParseUser yo = ParseUser.getCurrentUser();
                    yo.put("ProPicture", EditProfile.this.p);
                    yo.saveInBackground();
                    Log.d("score", "Retrieved the object." + object.getObjectId());
                }
            });
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("pic");
            query1.whereEqualTo("username", email);
            query1.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed." + e);
                        return;
                    }
                    object.getObjectId();
                    EditProfile.this.obj = object;
                    object.deleteInBackground();
                }
            });
            return null;
        }

        protected void onPostExecute(Void param) {
            EditProfile.this.uProgressDialog.dismiss();
            EditProfile.this.startActivity(new Intent(EditProfile.this, MainActivity.class));
            EditProfile.this.finish();
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            EditProfile.this.mProgressDialog = new ProgressDialog(EditProfile.this);
            EditProfile.this.mProgressDialog.setTitle("Edit Profile");
            EditProfile.this.mProgressDialog.setMessage("Loading...");
            EditProfile.this.mProgressDialog.setIndeterminate(false);
            EditProfile.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            ParseUser now = ParseUser.getCurrentUser();
            EditProfile.this.Name = now.get("Name").toString();
            EditProfile.this.Bio = now.get("Bio").toString();
            String gen = now.get("gender").toString();
            String rel = now.get("relationship").toString();
            EditProfile.this.ChangePassword = now.get("ConfirmPassword").toString();
            EditProfile.this.name.setText(EditProfile.this.Name);
            EditProfile.this.bio.setText(EditProfile.this.Bio);
            EditProfile.this.cpassword.setText(EditProfile.this.ChangePassword);
            EditProfile.this.gender.setText(gen);
            EditProfile.this.relationship.setText(rel);
            return null;
        }

        protected void onPostExecute(Void result) {
            EditProfile.this.mProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.name = (EditText) findViewById(R.id.editName);
        this.bio = (EditText) findViewById(R.id.editBio);
        this.cpassword = (EditText) findViewById(R.id.editPassword);
        this.gender = (EditText) findViewById(R.id.gender);
        this.relationship = (EditText) findViewById(R.id.relationship);
        new RemoteDataTask().execute(new Void[0]);
    }

    public void back(View view) {
        finish();
    }

    public void UpdateInformation(View view) {
        this.UName = this.name.getText().toString();
        this.UBio = this.bio.getText().toString();
        this.UChangePassword = this.cpassword.getText().toString();
        this.Ugender = this.gender.getText().toString();
        this.Urelationship = this.relationship.getText().toString();
        ParseUser user = ParseUser.getCurrentUser();
        user.put("Name", this.UName);
        user.put("Bio", this.UBio);
        user.setPassword(this.UChangePassword);
        user.put("ConfirmPassword", this.UChangePassword);
        user.put("gender", this.Ugender);
        user.put("relationship", this.Urelationship);
        user.saveInBackground();
        Toast.makeText(this, "Profile Updated. Refresh to see changes", 1).show();
        new CustomTask().execute(new Void[0]);
    }

    public void onPause() {
        super.onPause();
        System.out.println("hellosadasdad");
        String i = ParseUser.getCurrentUser().getEmail();
        System.out.println("email " + i);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("pic");
        query1.whereEqualTo("username", i);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed." + e);
                    return;
                }
                object.getObjectId();
                EditProfile.this.obj = object;
                object.deleteInBackground();
            }
        });
        finish();
    }

    public void onStop() {
        super.onStop();
        System.out.println("hellosadasdad");
        String i = ParseUser.getCurrentUser().getEmail();
        System.out.println("email " + i);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("pic");
        query1.whereEqualTo("username", i);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed." + e);
                    return;
                }
                object.getObjectId();
                EditProfile.this.obj = object;
                object.deleteInBackground();
            }
        });
        finish();
    }
}
