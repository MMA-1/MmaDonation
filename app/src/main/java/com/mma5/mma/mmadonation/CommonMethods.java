package com.mma5.mma.mmadonation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by MMA on 4/17/2017.
 */

public class CommonMethods {


    public static String resizeBase64Image(String base64image, int IMG_WIDTH, int IMG_HEIGHT) {
        byte[] encodeByte = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, IMG_WIDTH, IMG_HEIGHT, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static boolean logOutProcess(Context context) {

        try {
             SharedPreferences sharedpreferences;
            sharedpreferences =  context.getSharedPreferences(CommonStrings.MyPREFERENCES, 0);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(CommonStrings.SessionStatus, false);
            editor.remove(CommonStrings.UserId);
            editor.remove(CommonStrings.UserName);
            editor.remove(CommonStrings.Name);
            editor.remove(CommonStrings.UserContact);
            editor.remove(CommonStrings.UserType);
            editor.clear();
            editor.commit();
            return true;
        } catch (Exception ex) {
            Toast.makeText(context, "Logout Error"+ex, Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


