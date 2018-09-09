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
import com.onetoten.fahad.newp.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Upload_Image extends ActionBarActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public String Caption;
    public ParseFile fifi;
    public String h;
    public int height;
    public int height_temp1;
    public Bitmap image;
    private ImageView imgPicture;
    public int orientation;
    public ProgressDialog uProgressDialog;
    public EditText ucaption;
    public String w;
    public int width;
    public int width_temp1;

    private class CustomTask extends AsyncTask<Void, Void, Void> {
        private CustomTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Upload_Image.this.uProgressDialog = new ProgressDialog(Upload_Image.this);
            Upload_Image.this.uProgressDialog.setProgressStyle(0);
            Upload_Image.this.uProgressDialog.setTitle("Uploading Image...");
            Upload_Image.this.uProgressDialog.setIndeterminate(false);
            Upload_Image.this.uProgressDialog.show();
        }

        protected Void doInBackground(Void... param) {
            Upload_Image.this.Caption = Upload_Image.this.ucaption.getText().toString();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Upload_Image.this.image.compress(CompressFormat.PNG, 100, stream);
            ParseFile file = new ParseFile("newfile.png", stream.toByteArray());
            file.saveInBackground();
            ParseObject imgupload = new ParseObject(ParseUser.getCurrentUser().get("SchoolClass").toString());
            imgupload.put("pictures", file);
            String zero = "0";
            ParseUser CU = ParseUser.getCurrentUser();
            imgupload.put("CUobjectId", CU.getObjectId());
            imgupload.put("Name", CU.get("Name"));
            imgupload.put("username", CU.getUsername());
            imgupload.put("school", CU.get("SchoolName"));
            imgupload.put("dp", CU.get("ProPicture"));
            imgupload.put("caption", Upload_Image.this.Caption);
            imgupload.put("AvgRating", zero);
            imgupload.put("TotalRatings", zero);
            imgupload.saveInBackground();
            return null;
        }

        protected void onPostExecute(Void param) {
            Upload_Image.this.uProgressDialog.dismiss();
            Toast.makeText(Upload_Image.this, "Image Uploaded", 0).show();
            Upload_Image.this.startActivity(new Intent(Upload_Image.this, MainActivity.class));
            Upload_Image.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        this.ucaption = (EditText) findViewById(R.id.imgV);
        this.imgPicture = (ImageView) findViewById(R.id.Upload_image);
        Toast.makeText(this, "Click the gallery or Camera icon to select the image", 0).show();
    }

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            Uri imageUri;
            InputStream inputStream;
            int width_tmp;
            int height_tmp;
            int scale;
            Matrix m;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE /*1*/:
                    imageUri = data.getData();
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);
                        getOrientation(imageUri);
                        this.image = BitmapFactory.decodeStream(inputStream);
                        width_tmp = this.image.getWidth();
                        height_tmp = this.image.getHeight();
                        scale = REQUEST_IMAGE_CAPTURE;
                        while (width_tmp / 2 >= 200 && height_tmp / 2 >= 200) {
                            width_tmp /= 2;
                            height_tmp /= 2;
                            scale *= 2;
                        }
                        this.image = Bitmap.createScaledBitmap(this.image, width_tmp, height_tmp, true);
                        this.width_temp1 = width_tmp;
                        this.height_temp1 = height_tmp;
                        if (this.orientation != 270) {
                            if (this.orientation != 90) {
                                this.imgPicture.setImageBitmap(this.image);
                                break;
                            }
                            m = new Matrix();
                            m.postRotate(90.0f);
                            this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                            this.imgPicture.setImageBitmap(this.image);
                            break;
                        }
                        m = new Matrix();
                        m.postRotate(270.0f);
                        this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                        this.imgPicture.setImageBitmap(this.image);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Unable to open image", REQUEST_IMAGE_CAPTURE).show();
                        break;
                    }
                    break;
                case IMAGE_GALLERY_REQUEST /*20*/:
                    imageUri = data.getData();
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);
                        getOrientation(imageUri);
                        this.image = BitmapFactory.decodeStream(inputStream);
                        this.height = this.image.getHeight();
                        this.h = String.valueOf(this.height);
                        this.width = this.image.getWidth();
                        this.w = String.valueOf(this.width);
                        width_tmp = this.image.getWidth();
                        height_tmp = this.image.getHeight();
                        scale = REQUEST_IMAGE_CAPTURE;
                        while (width_tmp / 2 >= 600 && height_tmp / 2 >= 600) {
                            width_tmp /= 2;
                            height_tmp /= 2;
                            scale *= 2;
                        }
                        this.image = Bitmap.createScaledBitmap(this.image, width_tmp, height_tmp, true);
                        this.width_temp1 = width_tmp;
                        this.height_temp1 = height_tmp;
                        if (this.orientation != 90) {
                            if (this.orientation != 270) {
                                this.imgPicture.setImageBitmap(this.image);
                                break;
                            }
                            m = new Matrix();
                            m.postRotate(270.0f);
                            this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                            this.imgPicture.setImageBitmap(this.image);
                            break;
                        }
                        m = new Matrix();
                        m.postRotate(90.0f);
                        this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
                        this.imgPicture.setImageBitmap(this.image);
                        break;
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                        Toast.makeText(this, "Unable to open image", REQUEST_IMAGE_CAPTURE).show();
                        break;
                    }
                    break;
            }
            this.Caption = this.ucaption.getText().toString();
        }
    }

    public void Uploader(View view) {
        if (this.imgPicture.getDrawable() != null) {
            new CustomTask().execute(new Void[0]);
        } else {
            Toast.makeText(this, "Please select a picture to proceed", REQUEST_IMAGE_CAPTURE).show();
        }
    }

    public void home(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

    public void Rotate(View view) {
        Matrix m = new Matrix();
        m.postRotate(90.0f);
        this.image = Bitmap.createBitmap(this.image, 0, 0, this.width_temp1, this.height_temp1, m, true);
        this.imgPicture.setImageBitmap(this.image);
    }

    public void camera(View view) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera found", REQUEST_IMAGE_CAPTURE).show();
        }
    }

    public int getOrientation(Uri selectedImage) {
        this.orientation = 0;
        String[] projection = new String[REQUEST_IMAGE_CAPTURE];
        projection[0] = "orientation";
        Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
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
}
