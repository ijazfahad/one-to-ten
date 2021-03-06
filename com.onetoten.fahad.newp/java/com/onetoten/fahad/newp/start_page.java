package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class start_page extends ActionBarActivity {
    public static String LPassword;
    public static String LUsername;
    public ProgressDialog mProgressDialog;
    public boolean switcher;
    public String uname;
    public String upass;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            start_page.this.mProgressDialog = new ProgressDialog(start_page.this);
            start_page.this.mProgressDialog.setTitle("Logging In");
            start_page.this.mProgressDialog.setMessage("Loading...");
            start_page.this.mProgressDialog.setIndeterminate(false);
            start_page.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            EditText lpassword = (EditText) start_page.this.findViewById(R.id.password_login);
            start_page.LUsername = ((EditText) start_page.this.findViewById(R.id.username_login)).getText().toString();
            start_page.LPassword = lpassword.getText().toString();
            ParseUser.logInInBackground(start_page.LUsername, start_page.LPassword, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        start_page.this.switcher = true;
                        Toast.makeText(start_page.this, "User logged in", 1).show();
                        start_page.this.on();
                        return;
                    }
                    Toast.makeText(start_page.this, "User NOT logged in", 1).show();
                }
            });
            return null;
        }

        protected void onPostExecute(Void result) {
            start_page.this.mProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        haveNetworkConnection();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        for (NetworkInfo ni : ((ConnectivityManager) getSystemService("connectivity")).getAllNetworkInfo()) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected()) {
                haveConnectedWifi = true;
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()) {
                haveConnectedMobile = true;
            }
        }
        if (!(haveConnectedWifi || haveConnectedMobile)) {
            startActivity(new Intent(this, noInternetPage.class));
            finish();
        }
        if (haveConnectedWifi || haveConnectedMobile) {
            return true;
        }
        return false;
    }

    public void RLogIn(View view) {
        if (haveNetworkConnection()) {
            new RemoteDataTask().execute(new Void[0]);
        }
    }

    public void on() {
        if (this.switcher) {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    public void SignUp(View view) {
        startActivity(new Intent(this, SignUpFirst.class));
        finish();
    }

    public static String getLUsername() {
        return LUsername;
    }
}
