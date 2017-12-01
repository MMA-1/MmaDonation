package com.mma5.mma.mmadonation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView txtUserName= (TextView)findViewById(R.id.txtFatherName);
        final TextView txtName= (TextView)findViewById(R.id.txtName);
        final TextView txtAge= (TextView)findViewById(R.id.txtUserContact);
    }
}
