package com.mma5.mma.mmadonation;

import android.app.ProgressDialog;
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

public class ContactNumberEnter extends AppCompatActivity {
    String TAG = "Response";
    SoapPrimitive resultString;
    EditText txtUserName;
    TextView txtRegister;
    private ProgressDialog pDialog;
    Button submitUserName;
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
        setContentView(R.layout.activity_contact_number_enter);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        txtUserName= (EditText)findViewById(R.id.txtUserName);
        submitUserName= (Button) findViewById(R.id.submitUserName);
        txtRegister= (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(ContactNumberEnter.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        submitUserName.setOnClickListener(new View.OnClickListener() {
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
            pDialog = new ProgressDialog(ContactNumberEnter.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
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
                            editor.putString(Name, c.getString("Name"));
                            editor.putString(UserContact, c.getString("UserContact"));
                            editor.putString(UserType, c.getString("UserType"));
                            editor.putBoolean(SessionStatus, false);
                            editor.commit();
                            Intent userhomeIntent1 = new Intent(ContactNumberEnter.this,OnlyPasswordLogin.class);
                            startActivity(userhomeIntent1);
                            //finish();

                        } else {
                            Toast.makeText(getApplicationContext(), ResultMessage, Toast.LENGTH_LONG).show();
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
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_SelectUser;
        String METHOD_NAME = CommonStrings.METHOD_NAME_SelectUser;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("UserId", 0);
            Request.addProperty("UserName", txtUserName.getText().toString());
            Request.addProperty("ParamName", "SINGLEUSER");
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
}
