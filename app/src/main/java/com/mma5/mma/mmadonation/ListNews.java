package com.mma5.mma.mmadonation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ListNews extends AppCompatActivity {
    private WebView webViewListNews;
    String urlTypeStr,urlCmplet;
    File photo;
    File direct;
    private ImageView imageViewRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        //////////////////////////////////////
        imageViewRound=(ImageView)findViewById(R.id.userImage);
        direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);
        if (direct.exists()) {
            photo = new File(new File("/sdcard"+CommonStrings.ImageDir+"/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
            }
        }

        if (CommonMethods.isNetworkAvailable(getApplicationContext())) {
            //////////////////////////////////////


            Intent i = getIntent();
            int position = i.getExtras().getInt("extraVal");
            if (position == 2) {
                urlTypeStr = "?NewsTypeId=2"; // khutba
            } else if (position == 3) {
                urlTypeStr = "?NewsTypeId=3"; // news
            } else if (position == 8) {
                urlTypeStr = "?NewsTypeId=1"; //LIFE_HISTORY_OF_JANAB
            } else {
                urlTypeStr = "?NewsTypeId=1";//LIFE_HISTORY_OF_JANAB
            }
            urlCmplet = "http://kalbejawadoffice.com/ListKhutba.aspx" + urlTypeStr;
            webViewListNews = (WebView) findViewById(R.id.wvListNews);
            WebSettings webSettings = webViewListNews.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webViewListNews.loadUrl(urlCmplet);
            webViewListNews.setWebViewClient(new WebViewClient());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Network Not Available.", Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed() {
        if(webViewListNews.canGoBack()) {
            webViewListNews.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
