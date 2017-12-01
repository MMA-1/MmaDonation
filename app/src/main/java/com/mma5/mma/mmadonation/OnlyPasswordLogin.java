package com.mma5.mma.mmadonation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileOutputStream;


public class OnlyPasswordLogin extends AppCompatActivity {
    private ImageView imageViewRound;
    Context context;
    String TAG = "Response";
    EditText txtPassword;
    TextView lblUserName;
    TextView lblUserContact;
    SoapPrimitive resultString;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserId = "UserId";
    public static final String UserName = "UserName";
    public static final String UserContact = "UserContact";
    public static final String UserType = "UserType";
    public static final String SessionStatus = "SessionStatus";
    private ProgressDialog pDialog;
    int spUserId;
    String spUserContact,spUserName;
    SharedPreferences sharedpreferences;
    File photo;
    FileOutputStream fos;
    File direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_password_login);
        context=this.getApplicationContext();
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.demouser);
        imageViewRound.setImageBitmap(icon);
        lblUserName= (TextView) findViewById(R.id.lblUserName);
        lblUserContact= (TextView) findViewById(R.id.lblUserContact);
        final Button btnLogin=(Button)findViewById(R.id.btnLogin);
        txtPassword= (EditText)findViewById(R.id.txtPassword);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        spUserId= Integer.parseInt(sharedpreferences.getString(UserId,"0" ));
        spUserContact= sharedpreferences.getString(UserContact,"Unknown" );
        spUserName= sharedpreferences.getString(UserName,"Unknown" );
        lblUserName.setText(spUserName);
        int contactLength = spUserContact.length();
        String shortContact = spUserContact.substring(0, 2) + "______" + spUserContact.substring(contactLength-2);

        lblUserContact.setText(shortContact);

        if (direct.exists()) {
            photo = new File(new File("/sdcard"+CommonStrings.ImageDir+"/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
            }

        }
        checkLogin();

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
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OnlyPasswordLogin.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

            //////////////////////////////// DELETE PHOTO IF ALREADY EXIST/////////////////////////////
            if (!direct.exists()) {
                File chkdir = new File("/sdcard"+CommonStrings.ImageDir+"/");
                chkdir.mkdirs();
            }

            photo = new File(new File("/sdcard"+CommonStrings.ImageDir+"/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                photo.delete();
            }
        }
        @Override
        protected Void doInBackground(Void... params) {

            getLogin();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

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



                            String imgResize=CommonMethods.resizeBase64Image(c.getString("UserImage"),150,150);
                            byte[] decodedString = Base64.decode(imgResize, Base64.DEFAULT);
                            //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            //imageViewRound.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, imageViewRound.getWidth(), imageViewRound.getHeight(), false));

//////////////////////////////////////////////////////////////////
                            if (!direct.exists()) {
                                File chkdir = new File("/sdcard"+CommonStrings.ImageDir+"/");
                                chkdir.mkdirs();
                            }
                            if (photo.exists()) {
                                photo.delete();
                            }

                            try {
                                fos=new FileOutputStream(photo.getPath());
                                fos.write(decodedString[0]);
                                fos.close();
                            }
                            catch (java.io.IOException e) {
                                Log.e("PictureDemo", "Exception in photoCallback", e);
                            }
                            /////////////////////////////////////////////////////////////
                            Intent userhomeIntent1 = new Intent(OnlyPasswordLogin.this,MainActivity.class);
                            startActivity(userhomeIntent1);
                            finish();
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
            Request.addProperty("UserName", spUserName);
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
        if( Integer.parseInt(sharedpreferences.getString(UserId, "0")) > 0){

            //finish();
            return true;
        }
        else
        {
            CommonMethods.logOutProcess(getApplicationContext());
            Intent i = new Intent(context, ContactNumberEnter.class);
           // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //finish();
            startActivity(i);
            return false;
        }

    }
    public boolean isUserLoggedIn(){
        boolean boolData= sharedpreferences.getBoolean(SessionStatus, false);
        return boolData;
    }
}
