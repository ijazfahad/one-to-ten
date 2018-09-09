package com.onetoten.fahad.newp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class splashScreen extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Thread() {
            public void run() {
                try {
                    AnonymousClass1.sleep(800);
                    splashScreen.this.startActivity(new Intent(splashScreen.this, MainActivity.class));
                    splashScreen.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
