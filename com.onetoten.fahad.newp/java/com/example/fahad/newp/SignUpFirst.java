package com.example.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.onetoten.fahad.newp.BuildConfig;
import com.onetoten.fahad.newp.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SignUpFirst extends ActionBarActivity {
    public static final int IMAGE_GALLERY_REQUEST = 21;
    public static String SEmailAddress;
    public String SchoolClass;
    public String SchoolName;
    public ImageView dp;
    public int height_temp1;
    public boolean i;
    public Bitmap image;
    public ParseObject imgupload;
    public int orientation;
    public EditText semailaddress;
    public ProgressDialog uProgressDialog;
    public int width_temp1;

    private class CustomTask extends AsyncTask<Void, Void, Void> {
        private CustomTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            SignUpFirst.this.uProgressDialog = new ProgressDialog(SignUpFirst.this);
            SignUpFirst.this.uProgressDialog.setProgressStyle(0);
            SignUpFirst.this.uProgressDialog.setIndeterminate(false);
            SignUpFirst.this.uProgressDialog.show();
        }

        protected Void doInBackground(Void... param) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            SignUpFirst.this.image.compress(CompressFormat.PNG, 100, stream);
            ParseFile file = new ParseFile("newfile.png", stream.toByteArray());
            file.saveInBackground();
            SignUpFirst.this.imgupload = new ParseObject("pic");
            SignUpFirst.this.imgupload.put("ProPicture", file);
            SignUpFirst.this.imgupload.put("username", SignUpFirst.SEmailAddress);
            SignUpFirst.this.imgupload.put("SchoolName", SignUpFirst.this.SchoolName);
            SignUpFirst.this.imgupload.put("SchoolClass", SignUpFirst.this.SchoolClass);
            SignUpFirst.this.imgupload.saveInBackground();
            return null;
        }

        protected void onPostExecute(Void param) {
            SignUpFirst.this.uProgressDialog.dismiss();
            Intent j = new Intent(SignUpFirst.this, signup.class);
            j.putExtra("username", SignUpFirst.SEmailAddress);
            j.putExtra("SchoolName", SignUpFirst.this.SchoolName);
            j.putExtra("SchoolClass", SignUpFirst.this.SchoolClass);
            SignUpFirst.this.startActivity(j);
            SignUpFirst.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.dp = (ImageView) findViewById(R.id.textView5);
        this.semailaddress = (EditText) findViewById(R.id.imageView13);
    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == IMAGE_GALLERY_REQUEST) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                getOrientation(imageUri);
                this.image = BitmapFactory.decodeStream(inputStream);
                int width_tmp = this.image.getWidth();
                int height_tmp = this.image.getHeight();
                int scale = 1;
                while (width_tmp / 2 >= 600 && height_tmp / 2 >= 600) {
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
                this.image = Bitmap.createScaledBitmap(this.image, width_tmp, height_tmp, true);
                this.width_temp1 = width_tmp;
                this.height_temp1 = height_tmp;
                Matrix m;
                if (this.orientation == 90) {
                    m = new Matrix();
                    m.postRotate(90.0f);
                    this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                    this.dp.setImageBitmap(this.image);
                } else if (this.orientation == 270) {
                    m = new Matrix();
                    m.postRotate(270.0f);
                    this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                    this.dp.setImageBitmap(this.image);
                } else {
                    this.dp.setImageBitmap(this.image);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", 1).show();
            }
        }
    }

    public int getOrientation(Uri selectedImage) {
        this.orientation = 0;
        Cursor cursor = getContentResolver().query(selectedImage, new String[]{"orientation"}, null, null, null);
        if (cursor != null) {
            int orientationColumnIndex = cursor.getColumnIndex("orientation");
            if (cursor.moveToFirst()) {
                this.orientation = cursor.isNull(orientationColumnIndex) ? 0 : cursor.getInt(orientationColumnIndex);
            }
            cursor.close();
        }
        Log.d("orientation", "orientation is" + String.valueOf(this.orientation));
        return this.orientation;
    }

    public void Login(View view) {
        startActivity(new Intent(this, start_page.class));
        finish();
    }

    public void SignUp(View arg0) {
        SEmailAddress = this.semailaddress.getText().toString();
        if (SEmailAddress.equals(BuildConfig.FLAVOR)) {
            Toast.makeText(this, "Please enter a valid email address to proceed", 1).show();
        } else if (SEmailAddress.contains("@txstate.edu")) {
            this.SchoolName = "Texas State University";
            this.SchoolClass = "TexasState";
        } else if (SEmailAddress.contains("@nu.edu.pk") || SEmailAddress.contains("@nu.edu")) {
            this.SchoolName = "Fast NU";
            this.SchoolClass = "Fast";
        } else {
            Toast.makeText(this, "School is yet to be added in our database", 1).show();
            Toast.makeText(this, "please see our list of school's at play store", 1).show();
        }
        if (this.dp.getDrawable() != null) {
            new CustomTask().execute(new Void[0]);
        } else {
            Toast.makeText(this, "Please select a picture to proceed", 1).show();
        }
    }
}
