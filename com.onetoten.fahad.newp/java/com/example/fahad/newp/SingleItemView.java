package com.example.fahad.newp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.onetoten.fahad.newp.BuildConfig;
import com.onetoten.fahad.newp.R;
import com.onetoten.fahad.newp.Upload_Image;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SingleItemView extends ActionBarActivity {
    public static int checker = 1;
    public static int skip = 0;
    public String AvgRating;
    String Fname;
    String Lname;
    String ObjectId;
    String Rating;
    RatingsAdapter adapter;
    public ArrayList<Double> arrr = new ArrayList();
    TextView average;
    public byte[] b;
    public int c;
    String caption;
    public int counter = 0;
    String date;
    String dp;
    ImageLoader dpLoader = new ImageLoader(this);
    public ParseFile f;
    public Object fileObject1;
    ImageLoader imageLoader = new ImageLoader(this);
    ListView listview;
    ProgressDialog mProgressDialog;
    List<ParseObject> ob;
    String picture;
    private List<SingleItemRating> post = null;
    public ParseObject r;
    public boolean set;
    public boolean tick = false;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private RemoteDataTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            SingleItemView.this.mProgressDialog = new ProgressDialog(SingleItemView.this);
            SingleItemView.this.mProgressDialog.setTitle("Loading Ratings...");
            SingleItemView.this.mProgressDialog.setIndeterminate(false);
            SingleItemView.this.mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            SingleItemView.this.post = new ArrayList();
            try {
                ParseQuery<ParseObject> query = new ParseQuery("RatingsAndNames");
                query.whereEqualTo("rate", SingleItemView.this.ObjectId);
                query.orderByAscending("createdAt");
                query.setLimit(1000);
                SingleItemView.this.ob = query.find();
                for (ParseObject country : SingleItemView.this.ob) {
                    ParseFile dp = (ParseFile) country.get("dp");
                    SingleItemRating map = new SingleItemRating();
                    SingleItemView.this.arrr.add(SingleItemView.this.counter, Double.valueOf(Double.parseDouble(String.valueOf(country.get("Rating")))));
                    map.setRating((String) country.get("Rating"));
                    map.setName((String) country.get("CreatedBy"));
                    map.setCreatedAt(country.getCreatedAt());
                    map.setSchool((String) country.get("School"));
                    map.setCUobjectId((String) country.get("CUobjectId"));
                    map.setUserName((String) country.get("UserName"));
                    map.setRatingId(country.getObjectId());
                    Date d = country.getCreatedAt();
                    String k = country.getCreatedAt().toString();
                    String s = d.toString();
                    map.setpPicture(dp.getUrl());
                    SingleItemView.this.post.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            SingleItemView.this.listview = (ListView) SingleItemView.this.findViewById(R.id.rating);
            SingleItemView.this.adapter = new RatingsAdapter(SingleItemView.this, SingleItemView.this.post);
            SingleItemView.this.listview.setAdapter(SingleItemView.this.adapter);
            int f = SingleItemView.this.listview.getCount();
            System.out.println("f" + f);
            if (f == 0) {
                SingleItemView.skip = -10;
                Toast.makeText(SingleItemView.this, "No ratings yet!", 1).show();
            }
            SingleItemView.this.c = SingleItemView.this.listview.getCount();
            String d = String.valueOf(SingleItemView.this.c);
            SingleItemView.this.mProgressDialog.dismiss();
            System.out.print(SingleItemView.this.arrr);
            System.out.println(d);
            float sum = 0.0f;
            for (int j = 0; j < SingleItemView.this.arrr.size(); j++) {
                sum = (float) (((Double) SingleItemView.this.arrr.get(j)).doubleValue() + ((double) sum));
            }
            SingleItemView.this.arrr.clear();
            float avg = sum / ((float) SingleItemView.this.c);
            System.out.println("avg pro rating");
            SingleItemView.this.AvgRating = String.valueOf(avg);
            SingleItemView.this.average.setText(SingleItemView.this.AvgRating);
            ParseQuery.getQuery(ParseUser.getCurrentUser().get("SchoolClass").toString()).getInBackground(SingleItemView.this.ObjectId, new GetCallback<ParseObject>() {
                public void done(ParseObject rating, ParseException e) {
                    if (e == null) {
                        String d = String.valueOf(SingleItemView.this.c);
                        rating.put("AvgRating", SingleItemView.this.AvgRating);
                        rating.put("TotalRatings", d);
                        rating.put("TotalRatings1", Integer.valueOf(SingleItemView.this.c));
                        System.out.println("Avg ratinnng " + SingleItemView.this.AvgRating);
                        System.out.println("toal " + d);
                        rating.saveInBackground();
                    }
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_search_item);
        Intent i = getIntent();
        this.ObjectId = i.getStringExtra("ObjectId");
        this.Fname = i.getStringExtra("Name");
        this.caption = i.getStringExtra("Caption");
        this.picture = i.getStringExtra("picture");
        this.dp = i.getStringExtra("dp");
        this.date = i.getStringExtra("date");
        TextView Name = (TextView) findViewById(R.id.PlistView);
        TextView Caption = (TextView) findViewById(R.id.flag);
        TextView Date = (TextView) findViewById(R.id.Sdp);
        this.average = (TextView) findViewById(R.id.averageLabel);
        ImageView pic = (ImageView) findViewById(R.id.SearchUsername);
        ImageView image = (ImageView) findViewById(R.id.back);
        Name.setText(this.Fname);
        Caption.setText(this.caption);
        Date.setText(this.date);
        this.imageLoader.DisplayImage(this.picture, pic);
        this.dpLoader.DisplayImage(this.dp, image);
        new ParseQuery("anon").getInBackground("vsInpHMdjx", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                final ParseFile fileObject = (ParseFile) object.get("anon");
                fileObject.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Log.d("test", "We've got data in data.");
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            SingleItemView.this.b = data;
                            SingleItemView.this.f = new ParseFile("file.png", data);
                            SingleItemView.this.fileObject1 = fileObject.getUrl();
                            System.out.println("fileobject" + SingleItemView.this.fileObject1);
                            return;
                        }
                        Log.d("test", "There was a problem downloading the data.");
                    }
                });
            }
        });
        new RemoteDataTask().execute(new Void[0]);
    }

    public void checkBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        this.tick = checked;
        if (checked) {
            checker = 1;
        } else {
            checker = 0;
        }
        System.out.println("check box" + checked);
        System.out.println("check box" + this.tick);
        System.out.println("Checkersss" + checker);
    }

    public void back(View view) {
        finish();
    }

    public void add(View view) {
        this.Rating = ((EditText) findViewById(R.id.RlistView)).getText().toString();
        if (this.Rating.equals(BuildConfig.FLAVOR)) {
            Toast.makeText(this, "No number found in rating field", 1).show();
            return;
        }
        float rate = Float.parseFloat(this.Rating);
        System.out.println("rating" + rate);
        if (rate <= 0.0f || rate > 10.0f) {
            Toast.makeText(this, "Rating should be between 1 to 10", 1).show();
            return;
        }
        ParseUser user = ParseUser.getCurrentUser();
        String sameUser = user.get("Name").toString();
        this.c = this.listview.getCount();
        if (!this.Fname.equals(sameUser)) {
            System.out.println("real value" + checker);
            switch (checker) {
                case R.styleable.View_android_focusable /*0*/:
                    ParseObject ratings = new ParseObject("RatingsAndNames");
                    ratings.put("Rating", this.Rating);
                    ratings.put("value", "1");
                    ratings.put("CreatedFor", this.Fname);
                    ratings.put("CreatedBy", user.get("Name"));
                    ratings.put("UserName", user.getUsername());
                    ratings.put("School", user.get("SchoolName"));
                    ratings.put("CUobjectId", user.getObjectId());
                    ratings.put("rate", this.ObjectId);
                    ratings.put("dp", user.get("ProPicture"));
                    ratings.put("UserId", user.getUsername());
                    ratings.put("totalRatings", Integer.valueOf(this.c));
                    ratings.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                switch (Integer.parseInt(SingleItemView.this.Rating)) {
                                    case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
                                        Toast.makeText(SingleItemView.this, "eww eww eww eww EWWW ", 1).show();
                                        break;
                                    case R.styleable.View_paddingEnd /*2*/:
                                        Toast.makeText(SingleItemView.this, "some people shouldn't be allowed to upload things", 1).show();
                                        break;
                                    case R.styleable.Toolbar_subtitle /*3*/:
                                        Toast.makeText(SingleItemView.this, "ooh, that bad han?", 1).show();
                                        break;
                                    case R.styleable.Toolbar_contentInsetStart /*4*/:
                                        Toast.makeText(SingleItemView.this, "That's just plain ugly", 1).show();
                                        break;
                                    case R.styleable.Toolbar_contentInsetEnd /*5*/:
                                        Toast.makeText(SingleItemView.this, "Yeah that's what I thought, average.", 1).show();
                                        break;
                                    case R.styleable.Toolbar_contentInsetLeft /*6*/:
                                        Toast.makeText(SingleItemView.this, "not bad, not bad at all.", 1).show();
                                        break;
                                    case R.styleable.Toolbar_contentInsetRight /*7*/:
                                        Toast.makeText(SingleItemView.this, "oooo, me likey..", 1).show();
                                        break;
                                    case R.styleable.Toolbar_popupTheme /*8*/:
                                        Toast.makeText(SingleItemView.this, "hot. hot. hot. HOT.", 1).show();
                                        break;
                                    case R.styleable.Toolbar_titleTextAppearance /*9*/:
                                        Toast.makeText(SingleItemView.this, "HOT DAMN!! sssssss", 1).show();
                                        break;
                                    case R.styleable.Toolbar_subtitleTextAppearance /*10*/:
                                        Toast.makeText(SingleItemView.this, "OH. MY. F*#@ING. GOD.", 1).show();
                                        break;
                                }
                                Toast.makeText(SingleItemView.this, "Rating Saved", 1).show();
                                SingleItemView.this.finish();
                                return;
                            }
                            Toast.makeText(SingleItemView.this, "Unable to save Rating" + e, 1).show();
                        }
                    });
                    break;
                case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
                    String Uri = this.fileObject1.toString();
                    ParseObject parseObject = new ParseObject("RatingsAndNames");
                    parseObject.put("Rating", this.Rating);
                    parseObject.put("value", "1");
                    parseObject.put("CreatedFor", this.Fname);
                    parseObject.put("CreatedBy", "anonymous");
                    parseObject.put("rate", this.ObjectId);
                    parseObject.put("dp", this.f);
                    parseObject.put("UserId", user.getUsername());
                    parseObject.put("totalRatings", Integer.valueOf(this.c));
                    parseObject.saveInBackground(new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SingleItemView.this, "Rating Saved", 1).show();
                                SingleItemView.this.finish();
                                return;
                            }
                            Toast.makeText(SingleItemView.this, "Unable to save Rating" + e, 1).show();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        int g = new Random().nextInt(18) + 1;
        String numbe = Integer.toString(g);
        System.out.print("random number" + g);
        switch (g) {
            case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
                Toast.makeText(this, "Rating your own pic? Not cool man, not cool at all.", 1).show();
                break;
            case R.styleable.View_paddingEnd /*2*/:
                Toast.makeText(this, "Rating your own pic? That's just sad ...", 1).show();
                break;
            case R.styleable.Toolbar_subtitle /*3*/:
                Toast.makeText(this, "Rating your own pic? Don't, please don't. I beg of you.", 1).show();
                break;
            case R.styleable.Toolbar_contentInsetStart /*4*/:
                Toast.makeText(this, "Rating your own pic? If your parents saw what you're doing, they'd cry.", 1).show();
                break;
            case R.styleable.Toolbar_contentInsetEnd /*5*/:
                Toast.makeText(this, "oh come on, who rates their own picture?", 1).show();
                break;
            case R.styleable.Toolbar_contentInsetLeft /*6*/:
                Toast.makeText(this, "Rating your own pic? You live in your own little world, don't you?", 1).show();
                break;
            case R.styleable.Toolbar_contentInsetRight /*7*/:
                Toast.makeText(this, "Rating your own pic? GOD will judge you for doing that.", 1).show();
                break;
            case R.styleable.Toolbar_popupTheme /*8*/:
                Toast.makeText(this, "Rating your own pic? please don't make me lose all faith in humanity.", 1).show();
                break;
            case R.styleable.Toolbar_titleTextAppearance /*9*/:
                Toast.makeText(this, "Rating your own pic? Not cool man, not cool at all", 1).show();
                break;
            case R.styleable.Toolbar_subtitleTextAppearance /*10*/:
                Toast.makeText(this, "Rating your own pic? Like Seriously?", 1).show();
                break;
            case R.styleable.Toolbar_titleMargins /*11*/:
                Toast.makeText(this, "Rating your own pic? You definitely need help.", 1).show();
                break;
            case R.styleable.Toolbar_titleMarginStart /*12*/:
                Toast.makeText(this, "Rating your own pic? Forever alone kinda situation? ", 1).show();
                break;
            case R.styleable.Toolbar_titleMarginEnd /*13*/:
                Toast.makeText(this, "Can not rate your own picture.", 1).show();
                break;
            case R.styleable.Toolbar_titleMarginTop /*14*/:
                Toast.makeText(this, "Rating your own pic??? Your parents must be so proud.", 1).show();
                break;
            case R.styleable.Toolbar_titleMarginBottom /*15*/:
                Toast.makeText(this, "O.M.G, who rates their own pictures? ", 1).show();
                break;
            case R.styleable.Toolbar_maxButtonHeight /*16*/:
                Toast.makeText(this, "Come on, You're not a loser are you?", 1).show();
                break;
            case R.styleable.Toolbar_theme /*17*/:
                Toast.makeText(this, "Rating your own pic? C'mon, who does that?", 1).show();
                break;
            case R.styleable.Toolbar_collapseIcon /*18*/:
                Toast.makeText(this, "Rating your own pic? I smell desperation.", 1).show();
                break;
        }
        float sum = 0.0f;
        for (int j = 0; j < this.arrr.size(); j++) {
            sum = (float) (((double) sum) + ((Double) this.arrr.get(j)).doubleValue());
        }
        this.AvgRating = String.valueOf(sum / ((float) this.c));
        this.average.setText(this.AvgRating);
        checker = 1;
    }

    public void loadmore(View view) {
        new RemoteDataTask().execute(new Void[0]);
        skip += 10;
    }
}
