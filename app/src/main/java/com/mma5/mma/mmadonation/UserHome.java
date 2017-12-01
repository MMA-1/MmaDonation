package com.mma5.mma.mmadonation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.FileOutputStream;


public class UserHome extends AppCompatActivity {
    int extraVal;
    Class<?> classVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

       ///////////////////////-------------------------------------------
        //userimage.setImageResource(R.drawable.homeicon);
        String str ="/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExIVFRUWGBcXFxcXFxcXFxUXGBcYGBgXFxUYHSggGBolGxUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGhAQGi0lHyUuKy0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAOwA1gMBIgACEQEDEQH/xAAcAAEAAQUBAQAAAAAAAAAAAAAAAQIDBQYHBAj/xABEEAABAwICBgcDCgUBCQAAAAABAAIDBBEFIQYSMUFRYQcTInGBkaEysfAUI0JSYnKSosHhQ4Ky0fHCFSQzU2Nzk6Oz/8QAGgEBAQADAQEAAAAAAAAAAAAAAAECAwQFBv/EACgRAQEAAgICAgIBAwUAAAAAAAABAhEDIQQxEkETUSIFYdEUcYGhsf/aAAwDAQACEQMRAD8A7iiIgIiICIiAiKhxQVXQlW158SxGKCMyTSNYwbS4+gG88gqPUXK1NO1gu9zWj7RA964hpl0vzPcYqMdTGCQZXAl7hn7Lfo8eK5fimJyTnXkc57vryOLnHz2eCivqyfSqhYbOraZpGec0f917sMxWCoaXQTRytGRMb2vA79Ur4zN1VT1Do3azHuY7ZdpLTbhcFDp9r3UAr5Bw/TGuhyjrKho4GRzh5OvZbro30yVsJIqAyobu1vm3jnrNFiORCGn0Ui0rRLpJo6xucjYZBtZI4Dhm15sD7Qy27VujXA7ENJRERBERAREQEREBERAREQEREBERBS5UqXK3NK1jXPcQGtBc4nYABck+Cowul+k8NBCZJCC91xGze91vRvEr560k0rqKuQvmfe17A+zHfc1uwHZ++1XukHSV1ZVPk2sB1Y75hrBs8Te5AWnyzXytkivQ97Dm4F3nb/CvtZrjst/mIv8AhaD7/RYtxJyWQoH7WlodewF+z5ZKxjVmoi1RmM+Jz8gMgvA5e+sj7Vr245ki68LmW5qVUNChVMKkjeoqkZrqPRd0jvo3CCse91O42a83cYHc9+pbcuadSdoCyEdgy5F78+HL3KyJt9fUlUyRjXscHMcAWuabhwOwgq8vnTov02fRTsp5ZC6kkNrHPqXHY4cG3tcc19FNdcXUEoiICIiAiIgIiICIiAiIgKCpUFBQVpXS3iRioHNBsZTq+A7R9wHit1XFunfED1scV/ZZcDm4nP0HkrVjknG/H4+OStOZfPYFVa47j/hX6amc94aBnuUqzurEe0EC1uOZP917Iw8O1htGzs7OY3BbTh+jUeRf2neg8FsGH4GzO4B4LT+eb6b541125nYg3IL3bbFuQvvI9V556dzhrOOfC1vILs9LhLG3GqO1ty2pPgkLna2oNbKxts7gr+b+x/p5+3En0EjdrSO9XoaYh1iMjt8V2CfBmXFmiw2X954lYXEsBY7WysTY/Hksfzz7L49+q59DIQwNy2m3MEXt35LzvkyPDbq8Dy5LKYtg8kOdrt48FiLG63zKWdOe43G6q5ENY2vuBHcNoX030U4u+pw6F8h1nt1oyeOobAnnay+YXsscuAPjvHmu+dAkjjSTg7BNl36va7tyqOpoiKAiIgIiICIiAiIgIiICIiChxXCOnymd8rjf9ExNHiHOvf0Xd3LlfT9RXpIpQM2vLSbbA4XFz3j1SrHEmZAc8/VbHo9C0EuGfNamwmwvzC2/AYdVgvvWrmy/jpv8fHeW200wWUpisJDUNGRcO5Zaknad4XJJXdlYyQOSrGamKO4yVTIltm2lRKFjKyyyNWQ0Xc4DvKwlTWs+sPMLDKVljYx2I2IIIyO1c5xCPUJb9q66BVSh2wrUcdg7Xf8ABWzgurpr8jHc3GEcw5kdy+jehSj1MOD7ZyyPf3gWaP6V8+6w2AXvlbffcvq7RegMFJTxHayJjT36ouPNdbhZVERQEREBERAREQEREBERAREQQQtL6WXg0Dod8zmgZXyaQ4+4DxW6ErS+kN2cNx2QHnxNgsc7rFs4sZc5K+chSWm6s7nW/dbcYXEBrcuY3KjSfDQyaFwFi7I94ssjAw2Ntq5uTLcldnFh8bY8DsOYP+JMW9xF/VWGQ0+tZs73+Oa9sNARI5zgHXBFnbADvHAq5gWDNhfrXD8nANIyGtz3rLC9e2OeF36Z3BahrQA1zrAWsSs0Z8rrAQwaruz+w5LNTRnqtq1ZW7bZJpreOzMkuHFzvG1lrbIqX/nOB4BwW007AHXLQ4jc69liGYExs3WDtC7iGHYCfetuN1O61ZY23qPN8g3xykj42hWMUZ804naAsrS4SWuc5uQO4ez4BWcVp7scOIWHy7bPj/HTU8AoXySh7cmsIcSdxBuPcvqXRXFflNLHKRZxBDhu1mmxtyyv4rgehtCeoLiMg4nvIO3wsu6aEMApWkb3PP5iP0W/HO3Oxy58cx45ftn0RFtc4iIgIiICIiAiIgIiICIiClwWt6dUwdTF1s2EZ8AcvfZbMvFjVN1lPKy19ZjgO+2XrZTLuaZYXWUrh2kD9fqnap7Ljnuzbl6q5hzN6vYky0eqM96s4W/cuG3cenJplepHC6qjpuVl7KYA2Cv1LQGnipN6S1iOtAdYBZJzzqLDwuY21yS47clmHVEXVkk9ySLkwrSNex3rIspxttdY2Rzb5X1r5AhZ+nsRdJsqxKwW2LWcVYtkrStWxSW5Ks7T6e3AexCG29rWseZ3eq69ohHq0cPME+biVyOhgdqtZnfLV73bPUrtWF03VQxx/Ua1vkAF0cPdtc3kdSR6kRFvcgiIgIiICIiAiIgIiICIiAiIg1et0Mje9zg8ta+92gA2vt1TuXKjEY5XMO1ri3yNl31cf6Q8PMFYX/Rl7Y79jh+vitHLhNbjq4OS26teennVdRWHZe6xsM1wF58RhcRdriDyXNI67Xu1u1m5ekxMvz4rX6OBxPtXJ4rInDpr2uPNbJiy+O+18vsTmLr101TltWsVMBBIDjfkslQRlozJJ5rG4/bG7l0yNbPkvBgNJ11ZCwjWDnjWB2FozN/AKuskyWydFWGl80lQR2WDUaeLnbfIe9ZceO61cuWo36DAYGSCRsbQ4bOA7hsCyaIuuST04LbfYiIqgiIgIiICIiAiIgIiICIiAiIgLWukHDWzUUhI7UQMjDvBG3wIutlWmdImPdW1tHHYzVDX3+xE1pLnW4m2qO/kpZuaZY9VyShqxdZmN1wsA6l1hcZFX8Pry06r1x2b9PQ3r2zEdHndpIKv/IpT9LJU09UARmvbNiYta4SWrctemJdS6p23Kuk6oVE9U3bdYWsxAvOqzPnuHf8A2UktvZllHoq6kucGMzcSABzOS7zgGGtp6eOJotqtF+bj7R81wOgYWOYQe1rNOtzuM13rBcXZOHgZPjIa9vAlocD3EH3rp4p7rk599MkiItrnEREBERAREQEREBERAREQERQ5wAuTYDaTsCCUWl6Q9J+H0t2iXr5BfsQ2fmNxffVHndc4xzpoq5Ltpoo6cfWPzr/C4DR5FF07ZjeLxUsL55narGDPiTua0byTlZcL0dx11djDqiQe0x4Y3aGsFgG+RPiStLxbH6qpN6ioklO2zndkdzB2R4BZLo8m1a+H7Rc3zYf1stnHO0y6jdsYwYwSZD5t2bDu+6eYWIraMHcusGmZI0seLtPpzHArUdINH3QgkdqPc7eOTv7rm5+C435Y+nVweRM58cvbTGUrtgcR3q78gmP0gvXHHdXtezSBe60fkrf+PFg56N2xzyeQyV6ipQBsXpkj3lbDo5o66exPZjG13Hk3mrPlndRL8cJuvNo5ghnkDnC0UZBceJGYaP1Xli0wNBjUsj79TKGMkA3NDRqvA3lufgSul/JmRxiNg1WNHx3rgOn1QH18xGwEN/C0A+t13zimGGnn5cv5M9vqamqGyMa9jg5jgHNcDcOBzBBV1fKGC6YVtJYQVMjWjYwnWZ4MfcDwC6BgfThK2zauma8b3wnVd+B2R8wsNK7ei1bR/pCw+rsI6hrXn+HL82/uAdk7+UlbSogiIgIiICIiAi13FtOMPp2l0lXEbEt1WO6x9xtGoy5G1aTjHTbA24pqZ8h+tKRG3vAGs49xsi6dYWHxvSijpB/vFRHGfqk3ee6Nt3HyXz5j3STiFVcGcxMP0Ifmx+Ids/iWoucSSTtOZO8nmVdGo7VpF02MF20UBccx1k3Zb3iNpufEtXLtINKqusN6id7xuYDqxjujbl4m5WFRZTE2K40KlqquskW3bVkMCqOqnhk2asjD4awv6XXh1VVZMeqV9NwHJe5liLHMFYjAKkTU0Eo+nGx3iWi/rdZOM2K6a5o1jGtEQHa8GVzmzdf7J/RYCfCZW5OjeD90+9eTTzTWoFS+GB7omR2bcCznO2uIJzAzAFttr71prayWR3blkeT9Z7ne8rzObHj317e34vDy5Yy5Wa/7dRwTREyOD5uywbG/Sd38At1DA1oa0AACwA2Bcq0O0o+Sytikd8zIQDc+w45B3IbL+e5dSncurxph8f4uHzuPkwz1levp466WwK+asTm6yV8n1nOd5klfQuPy6lPNIfoRyO8mlfORW7k9ObjWyqrqCEC520cFsOj+m1fR2EFS/UH8N/zkfcGu9kfdIWBYharrZt2zR3puidZtbA6M75Iu2zvMZ7Te4ay6bg2O01U3Xp545Rv1XAlvJzdrTyIC+RbKqnnfG4PY9zHjY5ji1w7nNzCxuKvslF856PdL1fT2bMW1TOEnZk8JWj+oFdKwTpgw6awlc+mcdvWNuy//AHGXFuZssTToSLEUGk9FM4thq4JC0XIbKwkDjkdilE0+SQUUgKQtmjaFNkCFXQBTdUlSFRBVQugU2QA5TdRZSEHdOiWt6zD2tvcxPfGeWeu30eFukUOtkuT9CNb26iC+1rJGjm0lrj5Oj8l1qN5GfD1C3e40Xqtb0+0UbWM12honYOy4Za4Geo79DuXGKNpa/VIsQbEcLbQV9JuANiuF6aPidWTy0+wOs76peBZ5HEawPquTyMZrf29X+m8mVtw+p3/saGaPfL6p2uD1Eeb92te+qwHnbyBXY5ItSzNwGXIcPDIeS1zoqw9n+z2PHtPfI5xzGYeWgeAaFtr4W7LbVu4MZjjHJ5vNeTlv6nTSuk2r6vDpQNshZGP5nAu/K1y4Q5dW6a6qzaeDiXyH+UBjf63eS5Q4rPNo450oQbVLgi1NirVUqgKbqoKFJQBRUFqpLVdQK6FMZyRSEU0bQpVO9SFYJARyBCgFUAqWlS5BUUCpYfRVgXQLqLIiDbOi+t6vEof+oHxk/eaSPzMb5rv8RXy5h9WYZY5he8T2SZb9Rwdb0svqGAg2cNhAI7jmFuwvTTyTtiNL8XNJTSOabPd2Y+TjfPwFz4Likg1YTxOa3zpRr9edkIOUbbkfafmfyhvmVoWLuswNC8/nz+Wev097wOKYcPy+73/h0zohx9rovkbrB0YL492uxxu4fea4nwcOBW/zi6+asGxJ8Esc0Z7Ubg4c7bWnkQSDyJX0bS1bZY2SsN2SNa9p+y4Aj3rp4MtvM87h+GXynq/+uGdLdd1mIOZfKKONncTeQ/8A0b5LSCsnpHXdfVVE17h8ry37usQz8oasWs8r20SaiVClQsVFARxRFSFU0KAFKqJUOKm6occ7JQRCUWFqqVJO9Ql8lRVdHFRdFRF1UFSpaVBJVQKhAskSilCgL6I6PMR67DqZ17lrOrdxvETHn36oPivncFdf6Dq+8VRTk+w9srfuyN1SB3GO/wDOs8Kw5J0wuk9T1lZUG/8AFc3/AMdmf6Vr2MNXufLryyO+tI934nE/qvNiTMl5mV3bX0+GOsJj/aMHFtXXdF8aLMDlkvd1O2dg+97UY/8AYwLkrW5rMtxcNwuemvnJVQkj7Gpr3/FA0eK38GWsnB52G+L/AJjVbWFhuVJVTlSul5RZSTZSoKCmyqAUgJdNAVTdCVCCsq3faUJUE5eqmxIKKLIsWQFUAqVLdqsQCKHGxsgKCUChSAgqClUgqoFZREqUOy6IJC3HooxDqcRjBPZlY+M8NnWN/NGB4rTVepql0b2yMNnMIc08CDkrKWbjYcIfdoJ2m3rmr9e3JY+jfZgtsyWUc27R8fH7rzX089MG9trrG1Htdw96zNQzNYSR1yTz9At3BN5OHz8vjx6/agoiBdbxglAEbxUFyCSqULlBcloglQouqwMrqChyqeFDD2lJKigUKVCgi6rjVCrjKs9iiQ5oqJCqwglSoBUgIAVaoBVQKsRcQqBsKrsstCmyBEQZjDX6zGjhkfD9rLPMGXx8f571rOCv7RHcfFbQB7v0uuDkmsq+h8XP58WNYvFOy1zuAP7etlrV1smkfsDmQD4XP6Ba2Vv4J1a87+oZbzk/UQE25KSMvP0soj/Vb3npKtlXHNyVLmCyWC2hVRaoLViqlFSSqgMr/GxBMJzUPURFVSp9INNkUDMoiv/Z";
        //new SavePhotoTask().execute(data);

            //data:image/gif;base64,
            //this image is a single pixel (black)
            //String str = "R0lGODlhAQABAIAAAAAAAAAAACH5BAAAAAAALAAAAAABAAEAAAICTAEAOw==";
            byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
            new SavePhotoTask().execute(decodedString);


        ////////////////////////-----------------------------

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,View v, int position, long id){

                switch (position)
                {
                    case 0:
                    {
                        classVar=ConditionActivity.class;
                        extraVal=1; //KHUMS
                    }
                    case 1:{
                        classVar=ConditionActivity.class;
                        extraVal=2; //ZAKAT;
                    }
                    case 2:{
                        classVar=ConditionActivity.class;
                        extraVal=3; //ZAKAT_E_FITRA
                    }
                    case 3:{
                        classVar=ConditionActivity.class;
                        extraVal=4; //DONATION;
                    }
                    case 4:{
                        classVar=ConditionActivity.class;
                        extraVal=5; //LIFE_HISTORY_OF_JANAB
                    }
                    case 5:{
                        classVar=ConditionActivity.class;
                        extraVal=6; //KHUTBS_E_JUMA
                    }
                    case 6:{
                        classVar=ConditionActivity.class;
                        extraVal=7; //NEWS
                    }

                    default:{
                        classVar=ConditionActivity.class;
                        extraVal=6; //KHUTBS_E_JUMA
                    }
                }
                Intent i = new Intent(getApplicationContext(), classVar);
                i.putExtra("extraVal", position);
                startActivity(i);
            }
        });
    }


    private class SavePhotoTask extends AsyncTask<byte[], String, String> {
        FileOutputStream fos;
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        ImageView userimage= (ImageView)myToolbar.findViewById(R.id.userImage);
       // File photo =new File(Environment.getExternalStorageDirectory()+CommonStrings.ImageDir, CommonStrings.ImageFile);
       File photo;
        /////////////////////////////////////////////////////////////////////////
        File direct = new File(Environment.getExternalStorageDirectory() + CommonStrings.ImageDir);


        /////////////////////////////////////////////////////////////////////////
        @Override
        protected void onPreExecute() {
            //Log.i(TAG, "onPreExecute");
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
        protected String doInBackground(byte[]... jpeg) {


            if (photo.exists()) {
                photo.delete();
            }

            try {
                fos=new FileOutputStream(photo.getPath());
                fos.write(jpeg[0]);
                fos.close();
            }
            catch (java.io.IOException e) {
                Log.e("PictureDemo", "Exception in photoCallback", e);
            }

            return(null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                if (photo.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
                   // userimage.setImageBitmap(myBitmap);

                }


        }
    }
}

