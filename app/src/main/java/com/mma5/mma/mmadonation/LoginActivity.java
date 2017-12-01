package com.mma5.mma.mmadonation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends AppCompatActivity {
    String TAG = "Response";
    SoapPrimitive resultString;
    EditText txtUserContact;
    EditText txtPassword;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserId = "UserId";
    public static final String UserName = "UserName";
    public static final String Name = "Name";
    public static final String UserContact = "UserContact";
    public static final String UserType = "UserType";
    public static final String SessionStatus = "SessionStatus";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserContact= (EditText)findViewById(R.id.txtUserContact);
        txtPassword= (EditText)findViewById(R.id.txtPassword);
        final Button btnLogin=(Button)findViewById(R.id.btnLogin);
        final TextView tvRegisterHere= (TextView)findViewById(R.id.tvRegisterHere);
        final TextView tvGoToHome = (TextView) findViewById(R.id.tvGotoHome);
        final TextView tvPassLogin = (TextView) findViewById(R.id.tvPassLogin);
        final TextView tvMobileLogin = (TextView) findViewById(R.id.tvMobileLogin);
        final TextView tvChangeImage = (TextView) findViewById(R.id.tvChangeImage);
        final TextView mainnavigation = (TextView) findViewById(R.id.mainnavigation);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);

        checkLogin();

        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        tvGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userhomeIntent = new Intent(LoginActivity.this,UserHome.class);
                startActivity(userhomeIntent);
            }
        });
        tvPassLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent passloginIntent = new Intent(LoginActivity.this,OnlyPasswordLogin.class);
                startActivity(passloginIntent);
            }
        });
        tvMobileLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mobileloginIntent = new Intent(LoginActivity.this,ContactNumberEnter.class);
                startActivity(mobileloginIntent);
            }
        });
        tvChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mobileloginIntent = new Intent(LoginActivity.this,ChangeProfileImage.class);
                startActivity(mobileloginIntent);
            }
        });
        mainnavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mobileloginIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mobileloginIntent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();

            }
        });
    }
    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }
        @Override
        protected Void doInBackground(Void... params) {

            getLogin();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SharedPreferences.Editor editor = sharedpreferences.edit();

            if(resultString!=null) {
                String jsonStrLogin = resultString.toString();
                if (jsonStrLogin != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStrLogin);
                        JSONArray Status = jsonObj.getJSONArray("Status");
                        JSONObject Status0 = Status.getJSONObject(0);
                        String ResultStatus = Status0.getString("ResultStatus");
                        String ResultMessage = Status0.getString("ResultMessage");
                        JSONArray Data = jsonObj.getJSONArray("Data");
                        if (Integer.parseInt(ResultStatus) == 1) {
                            Toast.makeText(getApplicationContext(), "Success : " + ResultMessage, Toast.LENGTH_LONG).show();
                            JSONObject c = Data.getJSONObject(0);
                            editor.putString(UserId, c.getString("UserId"));
                            editor.putString(UserName, c.getString("UserName"));
                            editor.putString(UserContact, c.getString("UserContact"));
                            editor.putString(UserType, c.getString("UserType"));
                            editor.putBoolean(SessionStatus, true);
                            editor.commit();
                            Intent userhomeIntent1 = new Intent(LoginActivity.this,UserHome.class);
                            startActivity(userhomeIntent1);

                        } else {
                            Toast.makeText(getApplicationContext(), "Error : " + ResultMessage, Toast.LENGTH_LONG).show();
                        }
                    } catch (final Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Log.e("JSONError", "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get json from server.", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

        }

    }

    public void getLogin() {
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_SelectUserLogin;
        String METHOD_NAME = CommonStrings.METHOD_NAME_SelectUserLogin;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("UserId", 0);
            Request.addProperty("UserContact", txtUserContact.getText().toString());
            Request.addProperty("UserPassword", txtPassword.getText().toString());
            Request.addProperty("ParamName", "ANDROIDLOGIN");
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
    public boolean checkLogin(){
        if(this.isUserLoggedIn()){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
            //finish();
            return true;
        }
        return false;
    }
    public boolean isUserLoggedIn(){
       boolean boolData= sharedpreferences.getBoolean(SessionStatus, false);
        return boolData;
    }
}
