package com.example.fahad.newp;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;

public class ParseApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "zkBKLjeSqSP7Oimm5ffOhRYOh9GZMcLzNJi9QaOE", "SrZoPcDRKkPV1LgRU0LpRiYAnokYQh1yHfCxGXEL");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
