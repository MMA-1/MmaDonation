package com.mma5.mma.mmadonation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ConditionActivity extends AppCompatActivity {
    private WebView webViewConditions;
    String urlTypeStr, urlCmplet;
    File photo;
    File direct;
    private ImageView imageViewRound;
    Class<?> classVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
////////////////////-------------------------Action Bar------------------------------------//////////////////////////

        imageViewRound = (ImageView) findViewById(R.id.userImage);
        direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);
        if (direct.exists()) {
            photo = new File(new File("/sdcard" + CommonStrings.ImageDir + "/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
            }
        }
        if (CommonMethods.isNetworkAvailable(getApplicationContext())) {
            //////////////////////////////////////
            Intent i = getIntent();
            int position = i.getExtras().getInt("extraVal");
            if (position == 5) {
                urlTypeStr = "khumscondition.html"; //KHUMS
                classVar=DonateKhumsActivity.class;
            } else if (position == 8) {
                urlTypeStr = "zakatcondition.html"; //ZAKAT;
                classVar=DonationZakat.class;
            } else if (position == 4) {
                urlTypeStr = "donationcondition.html";  //DONATION;
                classVar=DonationSingle.class;
            } else if (position == 7) {
                urlTypeStr = "fitracondition.html"; //ZAKAT_E_FITRA
                classVar=DonationFitra.class;
            } else if (position == 6) {
                urlTypeStr = "sadqacondition.html"; //SADAQA
                classVar=DonationSadaqa.class;
            } else {
                urlTypeStr = "donationcondition.html";
                classVar=DonationSingle.class;
            }
            urlCmplet = "http://kalbejawadoffice.com/" + urlTypeStr;
            webViewConditions = (WebView) findViewById(R.id.wvConditions);
            WebSettings webSettings = webViewConditions.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webViewConditions.loadUrl(urlCmplet);
            webViewConditions.setWebViewClient(new WebViewClient());
////////////////////-------------------------Action Bar------------------------------------//////////////////////////

            Button btnProceed = (Button) findViewById(R.id.btnProceed);
            btnProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent donateIntent = new Intent(getApplicationContext(), classVar);
                    startActivity(donateIntent);
                }
            });
        }
    }

    public void onBackPressed() {
        if (webViewConditions.canGoBack()) {
            webViewConditions.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
