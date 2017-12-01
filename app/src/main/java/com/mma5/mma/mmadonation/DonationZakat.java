package com.mma5.mma.mmadonation;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DonationZakat extends AppCompatActivity {
    private ImageView imageViewRound;
    File photo;
    File direct;
    int extraVal;
    int spUserId;
    String spUserContact,spUserName,spName;
    SharedPreferences sharedpreferences;
    TextView txtName;
    TextView txtUserName;
    TextView txtContact;
    EditText txtAmount;
    Double amount;
    Button btnProceed;
    String TAGres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_zakat);
        ////////////////////-------------------------Action Bar Code------------------------------------//////////////////////////
        imageViewRound = (ImageView) findViewById(R.id.userImage);
        direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);
        if (direct.exists()) {
            photo = new File(new File("/sdcard" + CommonStrings.ImageDir + "/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
            }
        }

////////////////////-------------------------End Action Bar Code------------------------------------//////////////////////////
        sharedpreferences = getSharedPreferences(CommonStrings.MyPREFERENCES, MODE_PRIVATE);
        spUserId= Integer.parseInt(sharedpreferences.getString(CommonStrings.UserId,"0" ));
        spUserContact= sharedpreferences.getString(CommonStrings.UserContact,"" );
        spUserName= sharedpreferences.getString(CommonStrings.UserName,"" );
        spName= sharedpreferences.getString(CommonStrings.Name,"" );

        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText(spName);
        txtUserName = (TextView)findViewById(R.id.txtUserName);
        txtUserName.setText(spUserName);
        txtContact = (TextView)findViewById(R.id.txtContact);
        txtContact.setText(spUserContact);
        txtAmount=(EditText)findViewById(R.id.txtAmount) ;

        btnProceed =(Button)findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int errFlag = 0;
                if (txtAmount.getText().toString().trim().equals("")) {
                    txtAmount.setError("Amount is required!");
                    errFlag++;
                }

                else {
                    amount= Double.parseDouble(txtAmount.getText().toString().trim()) ;
                    // payyousdkfun(spUserId+"",spName,spUserContact,spnrIsSyed.getSelectedItem().toString().trim(),amount);
                }
            }
        });
    }
}
