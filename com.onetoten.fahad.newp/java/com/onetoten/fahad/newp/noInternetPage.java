package com.onetoten.fahad.newp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;
import com.parse.ParseUser;

public class noInternetPage extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_page);
    }

    public void internet(View view) {
        if (!haveNetworkConnection()) {
            return;
        }
        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this, start_page.class));
        finish();
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
            Toast.makeText(this, "No connection found", 1).show();
        }
        if (haveConnectedWifi || haveConnectedMobile) {
            return true;
        }
        return false;
    }
}
