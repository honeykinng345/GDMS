package com.GDMS.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.core.app.ActivityCompat;

import com.GDMS.models.GetDc.Dch;
import com.GDMS.models.ReasonList.ReasonListResponse;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Helper {

    private static final Helper ourInstance = new Helper();
    static Dch dch;
public static    SharedPreferences prefs;
    public  static   double Lat = 0.000;
    public  static   double Loung = 0.000;
    public static int is_login=1;

    public  static  String docnumber ;
    public  static String docSerial;
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static String getUserid(Context context){
        prefs = context.getSharedPreferences("login", MODE_PRIVATE);
          String user_id = prefs.getString("user_id","");

          return user_id;
    }
    public static String getCurrentDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        String time = df.format(c);
        return  time;
        // Toast.makeText(context, "date "+SingletonClass.currentDateTime, Toast.LENGTH_SHORT).show();
    }
    public static Dch getDch(){
        if (dch == null){

            dch = new Dch();
        }
        return dch;
    }

    public static Helper getInstance() {
        return ourInstance;
    }



    public  String fetchUserId(Context context,int value) {
        DCListDataBase sqLiteHandler;
        sqLiteHandler = DCListDataBase.getInstance(context);
        sqLiteHandler.openDatabase();
        Cursor res = sqLiteHandler.getAllData();
        String id = null;
        while (res.moveToNext()) {
            id = res.getString(value);
        }
        return id;
    }
}
