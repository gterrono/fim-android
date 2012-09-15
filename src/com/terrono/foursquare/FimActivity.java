package com.terrono.foursquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FimActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button logInBtn = (Button)findViewById(R.id.log_in);

		SharedPreferences settings = getSharedPreferences(FimWebView.FILE_NAME, 0);
		boolean authenticated = settings.getBoolean(FimWebView.AUTHENTICATED, false);
		
		if(authenticated)
			logInBtn.setText("Logged In");
		else
	        logInBtn.setOnClickListener(new OnClickListener() {
	        	public void onClick(View v) {
	        		Intent intent = new Intent(FimActivity.this, FimWebView.class);
	        		startActivity(intent);
	        	}
	        });
    }
}