package com.mma5.mma.mmadonation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static in.amrtechnologies.kjnaqvi.kjnaqvi.LoginActivity.MyPREFERENCES;
import static in.amrtechnologies.kjnaqvi.kjnaqvi.LoginActivity.SessionStatus;
import static in.amrtechnologies.kjnaqvi.kjnaqvi.LoginActivity.UserContact;
import static in.amrtechnologies.kjnaqvi.kjnaqvi.LoginActivity.UserId;
import static in.amrtechnologies.kjnaqvi.kjnaqvi.LoginActivity.UserName;

public class ChangeProfileImage extends AppCompatActivity {
    String TAG = "MMA Response";
    SoapPrimitive resultString;
    private ImageView imageViewRound;
    int userId;
    String userContact,userName;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_image);
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.demouser);
        imageViewRound.setImageBitmap(icon);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        userId= Integer.parseInt(sharedpreferences.getString(UserId,"0" ));
        userContact= sharedpreferences.getString(UserContact,"Unknown" );
        userName= sharedpreferences.getString(UserName,"Unknown" );
       // checkLogin();
        final Button btnImageFromGallary = (Button) findViewById(R.id.btnImageFromGallary);
        btnImageFromGallary.setOnClickListener(new View.OnClickListener() {
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

            setProfileImage();
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
                            String imgResize=CommonMethods.resizeBase64Image(c.getString("UserImage"),150,150);
                            byte[] decodedString = Base64.decode(imgResize, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageViewRound.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, imageViewRound.getWidth(), imageViewRound.getHeight(), false));
//                            editor.putString(UserId, c.getString("UserId"));
//                            editor.putString(UserName, c.getString("UserName"));
//                            editor.putString(UserContact, c.getString("UserContact"));
//                            editor.putString(UserType, c.getString("UserType"));
//                            editor.putBoolean(SessionStatus, true);
                            //editor.commit();
                            //Intent userhomeIntent1 = new Intent(ChangeProfileImage.this,UserHome.class);
                            //startActivity(userhomeIntent1);

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
    public void setProfileImage() {
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_GetUserDataWithImage;
        String METHOD_NAME = CommonStrings.METHOD_NAME_GetUserDataWithImage;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("UserId", userId);
            Request.addProperty("UserContact", userContact);
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

            //finish();
            return true;
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), OnlyPasswordLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
            finish();
        }
        return false;
    }
    public boolean isUserLoggedIn(){
        boolean boolData= sharedpreferences.getBoolean(SessionStatus, false);
        return boolData;
    }
}
