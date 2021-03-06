package com.mma5.mma.mmadonation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class DonateKhumsActivity extends AppCompatActivity {
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
    Spinner spnrIsSyed;
    Spinner spnrKhumsType;
    EditText txtAmount;
    Double amount;
    Button btnProceed;
    String TAGres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_khums);
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
        spnrIsSyed=(Spinner)findViewById(R.id.spnrIsSyed) ;
        spnrKhumsType=(Spinner)findViewById(R.id.spnrKhumsType) ;
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
//    public void payyousdkfun(String UserId,String Name,String Contact,String Product,Double amount)
//    {
//        String phone = Contact;
//        String productName = Product;
//        String firstName = Name;
//        String txnId = "0nf7" + System.currentTimeMillis();
//        String email="mazharabbas.official@gmail.com";
//        String sUrl = "https://test.payumoney.com/mobileapp/payumoney/success.php";
//        String fUrl = "https://test.payumoney.com/mobileapp/payumoney/failure.php";
//        String udf1 = UserId;
//        String udf2 = "";
//        String udf3 = "";
//        String udf4 = "";
//        String udf5 = "";
//        boolean isDebug = true;
//        String key = "dRQuiA"; //"J28AxBxQ";
//        String merchantId = "4928174";//"5794940" ;
//
//        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();
//        builder.setAmount(amount)
//                .setTnxId(txnId)
//                .setPhone(phone)
//                .setProductName(productName)
//                .setFirstName(firstName)
//                .setEmail(email)
//                .setsUrl(sUrl)
//                .setfUrl(fUrl)
//                .setUdf1(udf1)
//                .setUdf2(udf2)
//                .setUdf3(udf3)
//                .setUdf4(udf4)
//                .setUdf5(udf5)
//                .setIsDebug(isDebug)
//                .setKey(key)
//                .setMerchantId(merchantId);
//
//        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();
//        calculateServerSideHashAndInitiatePayment(paymentParam);
//
//        // url- https://github.com/payu-intrepos/PayUMoney-Android-SDK/blob/master/app/src/main/java/payumoney/payu/com/payumoneysdkapp/MyActivity.java
//    }
//    public static String hashCal(String str) {
//        byte[] hashseq = str.getBytes();
//        StringBuilder hexString = new StringBuilder();
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
//            algorithm.reset();
//            algorithm.update(hashseq);
//            byte messageDigest[] = algorithm.digest();
//            for (byte aMessageDigest : messageDigest) {
//                String hex = Integer.toHexString(0xFF & aMessageDigest);
//                if (hex.length() == 1) {
//                    hexString.append("0");
//                }
//                hexString.append(hex);
//            }
//        } catch (NoSuchAlgorithmException ignored) {
//        }
//        return hexString.toString();
//    }
//
//    private void calculateServerSideHashAndInitiatePayment(final PayUmoneySdkInitilizer.PaymentParam paymentParam) {
//
//        // Replace your server side hash generator API URL
//        String url = "https://test.payumoney.com/payment/op/calculateHashForTest";
//
//        Toast.makeText(this, "Please wait... Generating hash from server ... ", Toast.LENGTH_LONG).show();
//        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    if (jsonObject.has(SdkConstants.STATUS)) {
//                        String status = jsonObject.optString(SdkConstants.STATUS);
//                        if (status != null || status.equals("1")) {
//
//                            String hash = jsonObject.getString(SdkConstants.RESULT);
//                            Log.i("app_activity", "Server calculated Hash :  " + hash);
//
//                            paymentParam.setMerchantHash(hash);
//
//                            PayUmoneySdkInitilizer.startPaymentActivityForResult(DonateKhumsActivity.this, paymentParam);
//                        } else {
//                            Toast.makeText(DonateKhumsActivity.this,
//                                    jsonObject.getString(SdkConstants.RESULT),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                if (error instanceof NoConnectionError) {
//                    Toast.makeText(DonateKhumsActivity.this,
//                            DonateKhumsActivity.this.getString(R.string.connect_to_internet),
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(DonateKhumsActivity.this,
//                            error.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paymentParam.getParams();
//            }
//        };
//        Volley.newRequestQueue(this).add(jsonObjectRequest);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
//
//            /*if(data != null && data.hasExtra("result")){
//              String responsePayUmoney = data.getStringExtra("result");
//                if(SdkHelper.checkForValidString(responsePayUmoney))
//                    showDialogMessage(responsePayUmoney);
//            } else {
//                showDialogMessage("Unable to get Status of Payment");
//            }*/
//
//
//            if (resultCode == RESULT_OK) {
//                Log.i(TAGres, "Success - Payment ID : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
//                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
//                showDialogMessage("Payment Success Id : " + paymentId);
//            } else if (resultCode == RESULT_CANCELED) {
//                Log.i(TAGres, "failure");
//                showDialogMessage("cancelled");
//            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
//                Log.i("app_activity", "failure");
//
//                if (data != null) {
//                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {
//
//                    } else {
//                        showDialogMessage("failure");
//                    }
//                }
//                //Write your code if there's no result
//            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
//                Log.i(TAGres, "User returned without login");
//                showDialogMessage("User returned without login");
//            }
//        }
//    }
//
//    private void showDialogMessage(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Message");
//        builder.setMessage(message);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//
//    }
}
