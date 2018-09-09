package com.onetoten.fahad.newp;

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
import android.widget.ImageView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfile1 extends ActionBarActivity {
    public static final int IMAGE_GALLERY_REQUEST = 21;
    public ImageView dp;
    public int height_temp1;
    public boolean i;
    public Bitmap image;
    public ParseObject imgupload;
    public int orientation;
    public ProgressDialog uProgressDialog;
    public int width_temp1;

    private class CustomTask extends AsyncTask<Void, Void, Void> {
        private CustomTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(EditProfile1.this, "Please wait while we connect to server...", 1).show();
        }

        protected Void doInBackground(Void... param) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            EditProfile1.this.image.compress(CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            String email = ParseUser.getCurrentUser().getUsername();
            ParseFile file = new ParseFile("newfile.png", image);
            file.saveInBackground();
            EditProfile1.this.imgupload = new ParseObject("pic");
            EditProfile1.this.imgupload.put("ProPicture", file);
            EditProfile1.this.imgupload.put("username", email);
            EditProfile1.this.imgupload.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        ProgressDialog p = new ProgressDialog(EditProfile1.this);
                        Toast.makeText(EditProfile1.this, "Success", 1).show();
                        return;
                    }
                    Toast.makeText(EditProfile1.this, "Request failed, please try again", 1).show();
                }
            });
            return null;
        }

        protected void onPostExecute(Void param) {
            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            EditProfile1.this.startActivity(new Intent(EditProfile1.this, EditProfile.class));
            EditProfile1.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile1);
        this.dp = (ImageView) findViewById(R.id.eImage);
    }

    public void Next(View view) {
        if (this.dp.getDrawable() != null) {
            new CustomTask().execute(new Void[0]);
        } else {
            Toast.makeText(this, "Please select a picture to proceed or press SKIP", 1).show();
        }
    }

    public void openGallery(View view) {
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
                while (width_tmp / 2 >= 200 && height_tmp / 2 >= 200) {
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

    public void back(View view) {
        finish();
    }

    public void skip(View view) {
        startActivity(new Intent(this, SkipEditProfile.class));
        finish();
    }
}
