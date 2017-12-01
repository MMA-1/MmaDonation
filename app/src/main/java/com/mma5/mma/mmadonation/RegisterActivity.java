package com.mma5.mma.mmadonation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayAdapter<String> adapterReligion;
    ArrayAdapter<String> adapterOccupation;
    //ArrayAdapter<String> adapterUserType;
    List<String> religionName = new ArrayList<String>();
    List<String> religionId = new ArrayList<String>();
    List<String> occupationName = new ArrayList<String>();
    List<String> occupationId = new ArrayList<String>();
    /*List<String> usertypeName = new ArrayList<String>();
    List<String> usertypeId = new ArrayList<String>();*/
    private ProgressDialog pDialog;
    EditText txtName;
    EditText txtUserName;
    EditText txtFatherName;
    EditText txtMotherName;
    EditText txtUserContact;
    EditText txtUserEmail;
    Spinner spnrReligion;
    Spinner spnrGender;
    Spinner spnrOccupation;
    EditText txtAddress;
    EditText txtPincode;
    Spinner spnrType;
    EditText txtPassword;
    EditText txtConfirmPassword;
    EditText errGender;
    EditText errReligion;
    EditText errOccupation;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtName = (EditText) findViewById(R.id.txtName);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtFatherName = (EditText) findViewById(R.id.txtFatherName);
        txtMotherName = (EditText) findViewById(R.id.txtMotherName);
        txtUserContact = (EditText) findViewById(R.id.txtUserContact);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        spnrReligion = (Spinner) findViewById(R.id.spnrReligion);
        spnrGender = (Spinner) findViewById(R.id.spnrGender);
        spnrOccupation = (Spinner) findViewById(R.id.spnrOccupation);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtPincode = (EditText) findViewById(R.id.txtPincode);
        //spnrType= (Spinner)findViewById(R.id.spnrType);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        errGender = (EditText) findViewById(R.id.errorGender);
        errReligion = (EditText) findViewById(R.id.errReligion);
        errOccupation = (EditText) findViewById(R.id.errOccupation);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        try {
            new LongOperation().execute("");
        } catch (Exception ex) {
            Log.i("Async", "Async Task Started");
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int errFlag = 0;
                if (txtUserName.getText().toString().trim().equals("")) {
                    txtUserName.setError("Unique UserId is required!");
                    errFlag++;
                }
                if (txtName.getText().toString().trim().equals("")) {
                    txtName.setError("Name is required!");
                    errFlag++;
                }
                if (txtFatherName.getText().toString().trim().equals("")) {
                    txtFatherName.setError("Father name is required!");
                    errFlag++;
                }
                if (txtMotherName.getText().toString().trim().equals("")) {
                    txtMotherName.setError("Mother name is required!");
                    errFlag++;
                }
                if (txtUserContact.getText().toString().trim().equals("")) {
                    txtUserContact.setError("Contact is required!");
                    errFlag++;
                }
                if (spnrReligion.getSelectedItem().toString().trim().equals("Select Religion")) {
                    errReligion.setError("Select Religion");
                    errFlag++;
                }
                if (spnrGender.getSelectedItem().toString().trim().equals("Select Gender")) {
                    errGender.setError("Select Gender");
                    errFlag++;
                }
                if (spnrOccupation.getSelectedItem().toString().trim().equals("Select Occupation")) {
                    errOccupation.setError("Select Occupation");
                    errFlag++;
                }
                if (txtAddress.getText().toString().trim().equals("")) {
                    txtAddress.setError("Address is required!");
                    errFlag++;
                }
                if (txtPincode.getText().toString().trim().equals("")) {
                    txtPincode.setError("Pincode is required!");
                    errFlag++;
                }
                if (txtPassword.getText().toString().trim().equals("")) {
                    txtPassword.setError("Password is required!");
                    errFlag++;
                }
                if (!txtConfirmPassword.getText().toString().trim().equals(txtPassword.getText().toString().trim())) {
                    txtConfirmPassword.setError("Invalid Confirmation!");
                    errFlag++;
                }
                if (errFlag < 1) {
                    new RegistraionOperation().execute("");
                }

            }
        });
        txtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(txtUserName.getText().length() > 2) //checkDuplicateId()
                {
                    new CheckDuplicateOperation().execute(txtUserName.getText().toString());

                }
                else
                {
                   // Toast.makeText(getApplicationContext(),"UserId is not available.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            if (getReligion()) {
                if (getOccupation()) {
                   /* if (getUserType())
                    {

                    }*/
                }
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            try {
                adapterReligion = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, religionName);
                spnrReligion.setAdapter(adapterReligion);
                adapterOccupation = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, occupationName);
                spnrOccupation.setAdapter(adapterOccupation);
                /*adapterUserType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, usertypeName);
                spnrType.setAdapter(adapterUserType);*/
            } catch (Exception ex) {
                Log.e("ReligionException", ex.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public boolean getReligion() {
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_SelectReligion;
        String METHOD_NAME = CommonStrings.METHOD_NAME_SelectReligion;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;
        SoapPrimitive soapResRelgn;
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("UserId", 1);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            soapResRelgn = (SoapPrimitive) soapEnvelope.getResponse();

            String jsonStrReligion = soapResRelgn.toString();
            if (jsonStrReligion != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrReligion);
                    JSONArray Status = jsonObj.getJSONArray("Status");
                    JSONObject Status0 = Status.getJSONObject(0);
                    String ResultStatus = Status0.getString("ResultStatus");
                    String ResultMessage = Status0.getString("ResultMessage");
                    JSONArray Data = jsonObj.getJSONArray("Data");
                    if (Integer.parseInt(ResultStatus) == 1) {
                        religionId.add("0");
                        religionName.add("Select Religion");

                        for (int i = 0; i < Data.length(); i++) {
                            JSONObject c = Data.getJSONObject(i);
                            religionId.add(c.getString("ReligionId"));
                            religionName.add(c.getString("ReligionName"));
                        }

                    }
                } catch (final Exception e) {
                    Log.e("JSONError", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("JSONError", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return true;
        } catch (Exception ex) {
            Log.e("TAG", "Error: " + ex.getMessage());
            return false;
        }
    }

    public boolean getOccupation() {
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_SelectOccupation;
        String METHOD_NAME = CommonStrings.METHOD_NAME_SelectOccupation;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;
        SoapPrimitive soapResOcpn;
        try {
            SoapObject RequestOccupation = new SoapObject(NAMESPACE, METHOD_NAME);
            RequestOccupation.addProperty("UserId", 1);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(RequestOccupation);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            soapResOcpn = (SoapPrimitive) soapEnvelope.getResponse();

            String jsonStrReligion = soapResOcpn.toString();
            if (jsonStrReligion != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrReligion);
                    JSONArray Status = jsonObj.getJSONArray("Status");
                    JSONObject Status0 = Status.getJSONObject(0);
                    String ResultStatus = Status0.getString("ResultStatus");
                    String ResultMessage = Status0.getString("ResultMessage");
                    JSONArray Data = jsonObj.getJSONArray("Data");
                    if (Integer.parseInt(ResultStatus) == 1) {
                        occupationId.add("0");
                        occupationName.add("Select Occupation");
                        for (int i = 0; i < Data.length(); i++) {
                            JSONObject c = Data.getJSONObject(i);
                            occupationId.add(c.getString("OccupationId"));
                            occupationName.add(c.getString("OccupationName"));
                        }

                    }
                } catch (final Exception e) {
                    Log.e("JSONError", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("JSONError", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return true;
        } catch (Exception ex) {
            Log.e("TAG", "Error: " + ex.getMessage());
            return false;
        }
    }

    ///////////////////////////////////////////////----Registration Process----//////////////////////////////////////////////
    private class RegistraionOperation extends AsyncTask<String, Void, String> {
        String RegResultStatus;
        String RegResultMessage;
        final String UserName = txtUserName.getText().toString();
        final String Name = txtName.getText().toString();
        final String FatherName = txtFatherName.getText().toString();
        final String MotherName = txtMotherName.getText().toString();
        final String UserContact = txtUserContact.getText().toString();
        final String UserEmail = txtUserEmail.getText().toString();
        final String Religion = spnrReligion.getSelectedItem().toString();
        final String Gender = spnrGender.getSelectedItem().toString();
        final String Occupation = spnrOccupation.getSelectedItem().toString();
        final String Address = txtAddress.getText().toString();
        final String Pincode = txtPincode.getText().toString();
        //        final String Type=spnrType.getSelectedItem().toString();
        final String Password = txtPassword.getText().toString();
        final String ConfirmPassword = txtConfirmPassword.getText().toString();

        @Override
        protected String doInBackground(String... params) {


            String SOAP_ACTION = CommonStrings.SOAP_ACTION_InsUpdDelUser;
            String METHOD_NAME = CommonStrings.METHOD_NAME_InsUpdDelUser;
            String NAMESPACE = CommonStrings.NAMESPACE;
            String URL = CommonStrings.URL;
            SoapPrimitive soapResInsUpdDelUser;
            try {
                SoapObject RequestOccupation = new SoapObject(NAMESPACE, METHOD_NAME);
                ////---- Add request data--------////
                ; //spnrOccupation.getSelectedItemPosition()


                RequestOccupation.addProperty("UserId", 1);
                RequestOccupation.addProperty("WhatToDo", 2);
                RequestOccupation.addProperty("UserName", UserName);
                RequestOccupation.addProperty("Name", Name);
                RequestOccupation.addProperty("UserEmail", UserEmail);
                RequestOccupation.addProperty("FatherName", FatherName);
                RequestOccupation.addProperty("MotherName", MotherName);
                RequestOccupation.addProperty("AddressPermanent", Address);
                RequestOccupation.addProperty("PinCode", Pincode);
                RequestOccupation.addProperty("OccupationId", occupationId.get(occupationName.indexOf(Occupation)).toString());
                RequestOccupation.addProperty("AccessStatus", "1");
                RequestOccupation.addProperty("ReligionId", religionId.get(religionName.indexOf(Religion)).toString());
                RequestOccupation.addProperty("Gender", Gender);
                RequestOccupation.addProperty("UserPassword", Password);
                RequestOccupation.addProperty("UserContact", UserContact);
                RequestOccupation.addProperty("UserType", "3");
                ////----End request data--------////
                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(RequestOccupation);
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, soapEnvelope);
                soapResInsUpdDelUser = (SoapPrimitive) soapEnvelope.getResponse();

                String jsonStrInsUpdDel = soapResInsUpdDelUser.toString();
                if (jsonStrInsUpdDel != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStrInsUpdDel);
                        JSONArray Status = jsonObj.getJSONArray("Status");
                        JSONObject Status0 = Status.getJSONObject(0);
                        RegResultStatus = Status0.getString("ResultStatus");
                        RegResultMessage = Status0.getString("ResultMessage");

                    } catch (final Exception e) {
                        Log.e("AsyncTaskError", "----------: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                } else {
                    Log.e("JSONError", "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            } catch (Exception ex) {
                Log.e("TAG", "ErrorInRegistration: " + ex.getMessage());

            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            try {
                Toast.makeText(getApplicationContext(), RegResultMessage + " Correct", Toast.LENGTH_LONG).show();
                Intent intentRegister = new Intent(RegisterActivity.this, LoginActivity.class);
                intentRegister.putExtra("msg", "You have successfully registered. Please login to use app.");
                intentRegister.putExtra("val", 0);

                startActivity(intentRegister);
            } catch (Exception ex) {
                Log.e("ReligionException", ex.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class CheckDuplicateOperation extends AsyncTask<String, Void, String> {
        boolean resultBool;
        @Override
        protected String doInBackground(String... params) {

            try {
              resultBool=  checkDuplicateId(0,1,"USERNAME",params[0]);

            } catch (Exception ex) {
                Log.e("TAG", "ErrorInRegistration: " + ex.getMessage());

            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(resultBool)
            {
                Toast.makeText(getApplicationContext(),
                        "UserId is available.",
                        Toast.LENGTH_LONG)
                        .show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "UserId is not available.",
                        Toast.LENGTH_LONG)
                        .show();
                txtUserName.setText(null);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public boolean checkDuplicateId(int userId,int whatToDo, String paramName,String userName ) {
        boolean returnVal;
        String SOAP_ACTION = CommonStrings.SOAP_ACTION_UniqueFieldsManage;
        String METHOD_NAME = CommonStrings.METHOD_NAME_UniqueFieldsManage;
        String NAMESPACE = CommonStrings.NAMESPACE;
        String URL = CommonStrings.URL;
        SoapPrimitive soapResRelgn;
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("UserId", userId);
            Request.addProperty("WhatToDo", whatToDo);
            Request.addProperty("ParamName", paramName);
            Request.addProperty("UserName", userName);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            soapResRelgn = (SoapPrimitive) soapEnvelope.getResponse();

            String jsonStrReligion = soapResRelgn.toString();
            if (jsonStrReligion != null) {
                    JSONObject jsonObj = new JSONObject(jsonStrReligion);
                    JSONArray Status = jsonObj.getJSONArray("Status");
                    JSONObject Status0 = Status.getJSONObject(0);
                    String ResultStatus = Status0.getString("ResultStatus");
                    String ResultMessage = Status0.getString("ResultMessage");
                    JSONArray Data = jsonObj.getJSONArray("Data");
                    if (Integer.parseInt(ResultStatus) == 1) {
                        returnVal=false;
                    }
                    else
                    {
                        returnVal=true;
                    }
            } else {

                returnVal=false;
            }
        } catch (Exception ex) {
            returnVal=false;
        }
        return returnVal;
    }

}
