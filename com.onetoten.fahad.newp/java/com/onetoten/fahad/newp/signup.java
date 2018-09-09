package com.onetoten.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signup extends ActionBarActivity {
    public static String SConfirmPassword;
    public static String SEmailAddress;
    public static String SFirstName;
    public static String SLastName;
    public static String SPassword;
    public static String SSchoolName;
    public String SchoolClass;
    public boolean i;
    public Bitmap image;
    public Object obj;
    public ParseFile p;
    public TextView schoolname;
    public TextView semailaddress;
    public ProgressDialog uProgressDialog;

    private class CustomTask extends AsyncTask<Void, Void, Void> {
        private CustomTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            signup.this.uProgressDialog = new ProgressDialog(signup.this);
            signup.this.uProgressDialog.setProgressStyle(0);
            signup.this.uProgressDialog.setTitle("Signing Up...");
            signup.this.uProgressDialog.setIndeterminate(false);
            signup.this.uProgressDialog.show();
        }

        protected Void doInBackground(Void... param) {
            EditText spassword = (EditText) signup.this.findViewById(R.id.password_signup);
            EditText sfirstname = (EditText) signup.this.findViewById(R.id.first_name);
            EditText sconfirmpassword = (EditText) signup.this.findViewById(R.id.confirm_password);
            signup.SEmailAddress = signup.this.semailaddress.getText().toString();
            signup.SPassword = spassword.getText().toString();
            signup.SFirstName = sfirstname.getText().toString();
            signup.SSchoolName = signup.this.schoolname.getText().toString();
            signup.SConfirmPassword = sconfirmpassword.getText().toString();
            String Bio = "NA";
            ParseQuery<ParseObject> query = ParseQuery.getQuery("pic");
            query.whereEqualTo("username", signup.SEmailAddress);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed." + e);
                        return;
                    }
                    object.getObjectId();
                    signup.this.obj = object;
                    signup.this.p = (ParseFile) object.get("ProPicture");
                    ParseUser yo = new ParseUser();
                    yo.setUsername(signup.SEmailAddress);
                    yo.put("Name", signup.SFirstName);
                    yo.setPassword(signup.SPassword);
                    yo.put("SchoolName", signup.SSchoolName);
                    yo.put("SchoolClass", signup.this.SchoolClass);
                    yo.put("email", signup.SEmailAddress);
                    yo.put("ConfirmPassword", signup.SConfirmPassword);
                    yo.put("Bio", "NA");
                    yo.put("gender", "NA");
                    yo.put("relationship", "NA");
                    yo.put("ProPicture", signup.this.p);
                    yo.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                signup.this.i = true;
                                Toast.makeText(signup.this, "User Signed Up, Please Login to finalise your account", 1).show();
                                return;
                            }
                            String j = e.toString();
                            Toast.makeText(signup.this, "User NOT Signed Up", 1).show();
                            Log.d("MyApp", j);
                        }
                    });
                }
            });
            return null;
        }

        protected void onPostExecute(Void param) {
            signup.this.uProgressDialog.dismiss();
            signup.this.startActivity(new Intent(signup.this, start_page.class));
            signup.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Bundle SignupFirstData = getIntent().getExtras();
        String userid = SignupFirstData.getString("username");
        String Schoolname = SignupFirstData.getString("SchoolName");
        this.SchoolClass = SignupFirstData.getString("SchoolClass");
        this.semailaddress = (TextView) findViewById(R.id.email_address);
        this.schoolname = (TextView) findViewById(R.id.school_name);
        this.semailaddress.setText(userid);
        this.schoolname.setText(Schoolname);
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

    public void RSignUp(View view) {
        if (haveNetworkConnection()) {
            EditText pass = (EditText) findViewById(R.id.password_signup);
            EditText confirmpass = (EditText) findViewById(R.id.confirm_password);
            String na = ((EditText) findViewById(R.id.first_name)).getText().toString();
            String s1 = pass.getText().toString();
            if (!s1.equals(confirmpass.getText().toString())) {
                Toast.makeText(this, "Passwords do not match ", 1).show();
            } else if (s1.equals(BuildConfig.FLAVOR)) {
                Toast.makeText(this, "Please enter Password", 1).show();
            } else if (na.equals(BuildConfig.FLAVOR)) {
                Toast.makeText(this, "Please enter name", 1).show();
            } else {
                new CustomTask().execute(new Void[0]);
            }
        }
    }

    public void Login(View view) {
        startActivity(new Intent(this, start_page.class));
        finish();
    }
}
