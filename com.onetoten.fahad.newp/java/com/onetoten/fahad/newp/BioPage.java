package com.onetoten.fahad.newp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.parse.ParseUser;

public class BioPage extends ActionBarActivity {
    public TextView bgender;
    public String bio;
    public TextView biop;
    public TextView brelation;
    public String gender;
    public String relation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_page);
        this.biop = (TextView) findViewById(R.id.PageBio);
        this.brelation = (TextView) findViewById(R.id.Brelation);
        this.bgender = (TextView) findViewById(R.id.Bgender);
        ParseUser user = ParseUser.getCurrentUser();
        this.bio = user.get("Bio").toString();
        this.relation = user.get("relationship").toString();
        this.gender = user.get("gender").toString();
        this.biop.setText(this.bio);
        this.brelation.setText(this.relation);
        this.bgender.setText(this.gender);
    }

    public void skip(View view) {
        startActivity(new Intent(this, SkipEditProfile.class));
        finish();
    }

    public void back(View view) {
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bio_page, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
