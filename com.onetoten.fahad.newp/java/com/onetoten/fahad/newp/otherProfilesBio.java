package com.onetoten.fahad.newp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class otherProfilesBio extends ActionBarActivity {
    public String Bio;
    public TextView Gender;
    public TextView RelationShip;
    public TextView bio;
    public String gender;
    public String rela;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profiles_bio);
        this.bio = (TextView) findViewById(R.id.PageBio);
        this.RelationShip = (TextView) findViewById(R.id.rela);
        this.Gender = (TextView) findViewById(R.id.gen);
        Intent i = getIntent();
        this.Bio = i.getStringExtra("Bio");
        this.rela = i.getStringExtra("Relation");
        this.gender = i.getStringExtra("Gender");
        this.bio.setText(this.Bio);
        this.RelationShip.setText(this.rela);
        this.Gender.setText(this.gender);
    }

    public void back(View view) {
        finish();
    }
}
