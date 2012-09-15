package com.terrono.foursquare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FimWebView extends Activity {
	private static final String TAG = "FimWebView";

	public static final String CALLBACK_URL = "http://terrono.com";
	public static final String CLIENT_ID = "SFXAJJXC5SVJRPRX1QU1RO5RXEIR0VINHLDHQPISIQKIZVBO";
	public static final String FILE_NAME = "FimPrefsFile";
	public static final String AUTHENTICATED = "authenticated";
	public static final String TOKEN = "token";
	public static final String USERNAME = "username";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fim_webview);

		String url = 
				"https://foursquare.com/oauth2/authenticate"
				+ "?client_id=" + CLIENT_ID + "&response_type=token"
				+ "&redirect_uri=" + CALLBACK_URL;
		
		WebView webview = (WebView)findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				String fragment = "#access_token=";
				int start = url.indexOf(fragment);
				if(start > -1) {
					String accessToken = url.substring(start + fragment.length(), url.length());
					
					Log.v(TAG, "OAuth complete, token: [" + accessToken + "]");
					
					SharedPreferences settings = getSharedPreferences(FILE_NAME, 0);
					Editor editor = settings.edit();
					editor.putBoolean(AUTHENTICATED, true);
					editor.putString(TOKEN, accessToken);
					editor.commit();
					
					Toast.makeText(FimWebView.this, "Token: " + accessToken, Toast.LENGTH_SHORT).show();
				}
			}
			
			public void onPageFinished(WebView view, String url) {
				finish();
				Toast.makeText(FimWebView.this, "Authenticated!", Toast.LENGTH_SHORT).show();
			}
		});
		webview.loadUrl(url);
	}
}
