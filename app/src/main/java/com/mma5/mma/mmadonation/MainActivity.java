package com.mma5.mma.mmadonation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder alertDialog;
    DrawerLayout drawer;
    private ImageView imageViewRound;
    File photo;
    File direct;
    int extraVal;
    Class<?> classVar;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserId = "UserId";
    public static final String UserName = "UserName";
    public static final String Name = "Name";
    public static final String UserContact = "UserContact";
    public static final String UserType = "UserType";
    public static final String SessionStatus = "SessionStatus";
    int spUserId;
    String spUserContact,spUserName,spName;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageViewRound=(ImageView)findViewById(R.id.userImage);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // imageView=(ImageView)findViewById(R.id.imageView);
       // imageView.setImageResource(R.drawable.demouser);
        //Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.demouser);
        //imageViewRound.setImageBitmap(icon);
       // getSupportActionBar().setIcon(R.drawable.toolbaricon);
        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        View hView =  rightNavigationView.getHeaderView(0);
        ImageView imageView = (ImageView)hView.findViewById(R.id.imageView);
        TextView txtName = (TextView)hView.findViewById(R.id.txtName);
        TextView txtContact = (TextView)hView.findViewById(R.id.txtContact);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        spUserId= Integer.parseInt(sharedpreferences.getString(UserId,"0" ));
        spUserContact= sharedpreferences.getString(UserContact,"Unknown" );
        spUserName= sharedpreferences.getString(UserName,"Unknown" );
        spName= sharedpreferences.getString(Name,"Unknown" );
        txtName.setText(spName);
        txtContact.setText(spUserContact);
        /////////////////////////////////set user image ///////////////////////////////////////
        direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);
        if (direct.exists()) {
            photo = new File(new File("/sdcard"+CommonStrings.ImageDir+"/"), CommonStrings.ImageFile);
            if (photo.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                imageViewRound.setImageBitmap(myBitmap);
                imageView.setImageBitmap(myBitmap);
            }
        }
        /////////////////////////////////end set user image ///////////////////////////////////////

        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_settings) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Settings", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                   if(CommonMethods.logOutProcess(getApplicationContext())==true)
                   {
                       Intent logoutIntent = new Intent(getApplicationContext(),ContactNumberEnter.class);
                       startActivity(logoutIntent);
                       finish();
                   }
                   else {
                       Toast.makeText(MainActivity.this, "Logout Error", Toast.LENGTH_SHORT).show();
                   }
                } else if (id == R.id.nav_help) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Help", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "Right Drawer - About", Toast.LENGTH_SHORT).show();
                }

                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,View v, int position, long id){

                switch (position)
                {
                    case 0:
                    {
                        classVar=ListNews.class;
                        extraVal=1; //LIFE_HISTORY_OF_JANAB
                        break;
                    }
                    case 1:{
                        classVar=ListNews.class;
                        extraVal=2; //KHUTBA_E_JUMA
                        break;
                    }
                    case 2:{
                        classVar=ListNews.class;
                        extraVal=3; //NEWS
                        break;
                    }
                    case 3:{
                        classVar=ConditionActivity.class;
                        extraVal=4; //DONATION;
                        break;
                    }
                    case 4:{
                        classVar=ConditionActivity.class;
                        extraVal=5; //KHUMS
                        break;
                    }
                    case 5:{
                        classVar=ConditionActivity.class;
                        extraVal=6; //SADAQA
                        break;
                    }
                    case 6:{
                        classVar=ConditionActivity.class;
                        extraVal=7; //ZAKAT_E_FITRA
                        break;
                    }
                    case 7:{
                        classVar=ConditionActivity.class;
                        extraVal=8; //ZAKAT;
                        break;
                    }
                    default:{
                        classVar=ListNews.class;
                        extraVal=2; //KHUTBS_E_JUMA
                        break;
                    }
                }
                if(CommonMethods.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), classVar);
                    i.putExtra("extraVal", extraVal);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Network Not Available.", Toast.LENGTH_LONG).show();
                }
            }
        });

        ////////////////////////////////------ Alert Dialog-----------/////////////////////////////
        alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.circle);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                System.exit(1);
                // Write your code here to invoke YES event
               // Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });






        ////////////////////////////////------End Alert Dialog-------///////////////////////////////
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
           // super.onBackPressed();
            // Showing Alert Message
            alertDialog.show();
           // System.exit(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
