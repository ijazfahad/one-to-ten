package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseUser;

public class SkipEditProfile extends ActionBarActivity {
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
    ProgressDialog mProgressDialog;
    public EditText name;
    public EditText relationship;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            SkipEditProfile.this.mProgressDialog = new ProgressDialog(SkipEditProfile.this);
            SkipEditProfile.this.mProgressDialog.setTitle("Edit Profile");
            SkipEditProfile.this.mProgressDialog.setMessage("Loading...");
            SkipEditProfile.this.mProgressDialog.setIndeterminate(false);
            SkipEditProfile.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            ParseUser now = ParseUser.getCurrentUser();
            SkipEditProfile.this.Name = now.get("Name").toString();
            SkipEditProfile.this.Bio = now.get("Bio").toString();
            System.out.print(SkipEditProfile.this.Bio);
            SkipEditProfile.this.ChangePassword = now.get("ConfirmPassword").toString();
            String gen = now.get("gender").toString();
            String rel = now.get("relationship").toString();
            SkipEditProfile.this.name.setText(SkipEditProfile.this.Name);
            SkipEditProfile.this.bio.setText(SkipEditProfile.this.Bio);
            SkipEditProfile.this.cpassword.setText(SkipEditProfile.this.ChangePassword);
            SkipEditProfile.this.gender.setText(gen);
            SkipEditProfile.this.relationship.setText(rel);
            return null;
        }

        protected void onPostExecute(Void result) {
            SkipEditProfile.this.mProgressDialog.dismiss();
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
        Toast.makeText(this, "Profile Updated. Refresh to see changes", 1).show();
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
