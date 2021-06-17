package com.GDMS.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.GDMS.models.GetDc.Dch;

import java.util.ArrayList;

import static com.GDMS.Utilities.DCListDataBase.MyTable.TABLE_NAME;

public class DCListDataBase extends SQLiteOpenHelper {
    private static final String LOCAL_DATABASE_NAME = "sql_database.db";
    private static final int LOCAL_DATABASE_VERSION = 1;
    private SQLiteDatabase sqLiteDatabase;
    private static DCListDataBase database_helper;

    public static DCListDataBase getInstance(Context context) {
        if (database_helper == null) {
            database_helper = new DCListDataBase(context);
        }
        return database_helper;
    }

    private DCListDataBase(Context context) {
        super(context, LOCAL_DATABASE_NAME, null, LOCAL_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public static class MyTable {
        static final String TABLE_NAME = "demand_model_items";
        static final String ID = "id";
        static final String DOCNUMBER = "docnumber";
        static final String DOCSERIAL = "docserial";
        static final String VEHICLENUM = "vehiclenum";
        static final String DELIVERYBY = "deliveryby";
        static final String STATUS = "status";
        static final String SAGEUPDATE = "sageupdate";
        static final String REMARKS = "remarks";
        static final String RREASONID = "rreasonid";
        static final String DELIVERYDATETIME = "deliverydatetime";
        static final String DELIVERYLAT = "deliverylat";
        static final String DELIVERYLONG = "deliverylong";
        static final String DELIVERYLOCATION = "deliverylocation";
        static final String SYSDATETIME = "sysdatetime";


        static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + DOCNUMBER + " TEXT , "
                + DOCSERIAL + " TEXT , "
                + VEHICLENUM + " TEXT , "
                + DELIVERYBY + " TEXT , "
                + STATUS + " TEXT , "
                + SAGEUPDATE + " TEXT , "
                + REMARKS + " TEXT , "
                + RREASONID + " TEXT , "
                + DELIVERYDATETIME + " TEXT ,"
                + DELIVERYLAT + " TEXT,"
                + DELIVERYLONG + " TEXT,"
                + DELIVERYLOCATION + " TEXT,"
                + SYSDATETIME + " TEXT"
                + " );";
    }

    public void openDatabase() {
        sqLiteDatabase = database_helper.getWritableDatabase();
    }

    public void closeDB() {
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public int addItemData(Dch dch) {
        ContentValues values = new ContentValues();
        values.put(MyTable.DOCNUMBER, dch.getDocnumber());
        values.put(MyTable.DOCSERIAL, dch.getDocserial());
        values.put(MyTable.VEHICLENUM, dch.getVehiclenum());
        values.put(MyTable.DELIVERYBY, dch.getDeliveryby());
        values.put(MyTable.STATUS, dch.getStatus());
        values.put(MyTable.SAGEUPDATE, dch.getSageupdate());
        values.put(MyTable.REMARKS, dch.getRemarks());
        values.put(MyTable.RREASONID, String.valueOf(dch.getRreasonid()));
        values.put(MyTable.DELIVERYDATETIME, dch.getDeliverydatetime());
        values.put(MyTable.DELIVERYLAT, dch.getDeliverylat());
        values.put(MyTable.DELIVERYLONG, dch.getDeliverylong());
        values.put(MyTable.SYSDATETIME, String.valueOf(dch.getSysdatetime()));

        // Inserting Row
        return (int) sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

  public ArrayList<Dch> getData() {
        ArrayList<Dch> arrayList = new ArrayList<>();
      Dch dchObject;
        String query = "SELECT * FROM " + TABLE_NAME;
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        dchObject = new Dch();
                        dchObject.setDocnumber(cursor.getString(cursor.getColumnIndex(MyTable.DOCNUMBER)));
                        dchObject.setDocserial(cursor.getInt(cursor.getColumnIndex(MyTable.DOCSERIAL)));
                        dchObject.setVehiclenum(cursor.getString(cursor.getColumnIndex(MyTable.VEHICLENUM)));
                        dchObject.setDeliveryby(cursor.getString(cursor.getColumnIndex(MyTable.DELIVERYBY)));
                        dchObject.setStatus(cursor.getInt(cursor.getColumnIndex(MyTable.STATUS)));
                        dchObject.setSageupdate(cursor.getInt(cursor.getColumnIndex(MyTable.SAGEUPDATE)));
                        dchObject.setRemarks(cursor.getString(cursor.getColumnIndex(MyTable.REMARKS)));
                        dchObject.setRreasonid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyTable.RREASONID))));
                        dchObject.setDeliverydatetime(cursor.getString(cursor.getColumnIndex(MyTable.DELIVERYDATETIME)));
                        dchObject.setDeliverylat(cursor.getString(cursor.getColumnIndex(MyTable.DELIVERYLAT)));
                        dchObject.setDeliverylong(cursor.getString(cursor.getColumnIndex(MyTable.DELIVERYLONG)));
                        dchObject.setDeliverylocation(cursor.getString(cursor.getColumnIndex(MyTable.DELIVERYLOCATION)));
                        dchObject.setSysdatetime(cursor.getString(cursor.getColumnIndex(MyTable.SYSDATETIME)));
                        arrayList.add(dchObject);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        return arrayList;
    }
    public boolean updateList(String docNumber, String DOCSERIAL, String lat ,String loung ,String dateTime,String adress,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyTable.DELIVERYLAT, lat);
        contentValues.put(MyTable.DELIVERYLONG, loung);
        contentValues.put(MyTable.DELIVERYDATETIME, dateTime);
        contentValues.put(MyTable.DELIVERYLOCATION,adress);
        contentValues.put(MyTable.STATUS,status);
        db.update(TABLE_NAME, contentValues, MyTable.DOCNUMBER + "=? And " + MyTable.DOCSERIAL + "=?", new String[]{docNumber, DOCSERIAL});
        return true;
    }
    public boolean updateList(String docNumber, String DOCSERIAL, String lat ,String loung ,String dateTime,String adress,String rid,String reason,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyTable.DELIVERYLAT, lat);
        contentValues.put(MyTable.DELIVERYLONG, loung);
        contentValues.put(MyTable.DELIVERYDATETIME, dateTime);
        contentValues.put(MyTable.DELIVERYLOCATION,adress);
        contentValues.put(MyTable.REMARKS,reason);
        contentValues.put(MyTable.RREASONID,rid);
        contentValues.put(MyTable.STATUS,status);
        db.update(TABLE_NAME, contentValues, MyTable.DOCNUMBER + "=? And " + MyTable.DOCSERIAL + "=?", new String[]{docNumber, DOCSERIAL});
        return true;
    }


    public boolean deleteSingleItem(String docnumber, String sercialnumber) {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.delete(TABLE_NAME, MyTable.DOCNUMBER + "=? AND " + MyTable.DOCSERIAL + "=?", new String[]{docnumber, sercialnumber});
            return true;
        }
        return false;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }
    public int deleteData() {
        int result = 0;
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            result = sqLiteDatabase.delete(MyTable.TABLE_NAME, null, null);
        }
        return result;
    }

    /*public ArrayList<Dch> getAllData(String demandNumber) {
        ArrayList<Dch> arrayList = new ArrayList<>();
        Dch demandModelObject;
        String query = "SELECT * FROM " + MyTable.TABLE_NAME + " WHERE DEMANDNUMBER='" + demandNumber + "'";
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        demandModelObject = new Dch();
                        demandModelObject.setDeviceID(cursor.getInt(cursor.getColumnIndex(MyTable.DEVICEID)));
                        demandModelObject.setStatus(cursor.getInt(cursor.getColumnIndex(MyTable.STATUS)));
                        demandModelObject.setUserID(cursor.getInt(cursor.getColumnIndex(MyTable.USERID)));
                        demandModelObject.setOnHandQty(cursor.getInt(cursor.getColumnIndex(MyTable.ONHANDQTY)));
                        demandModelObject.setDemandQty(cursor.getInt(cursor.getColumnIndex(MyTable.DEMANDQTY)));
                        demandModelObject.setDemandNumber(cursor.getString(cursor.getColumnIndex(MyTable.DEMANDNUMBER)));
                        demandModelObject.setDemandDate(cursor.getString(cursor.getColumnIndex(MyTable.DEMANDDATE)));
                        demandModelObject.setDescription(cursor.getString(cursor.getColumnIndex(MyTable.DESCRIPTION)));
                        demandModelObject.setDemandType(cursor.getString(cursor.getColumnIndex(MyTable.DEMANDTYPE)));
                        demandModelObject.setLocationID(cursor.getString(cursor.getColumnIndex(MyTable.LOCATIONID)));
                        demandModelObject.setDepartment(cursor.getString(cursor.getColumnIndex(MyTable.DEPARTMENT)));
                        demandModelObject.setCategory(cursor.getString(cursor.getColumnIndex(MyTable.CATEGORY)));
                        demandModelObject.setItemID(cursor.getString(cursor.getColumnIndex(MyTable.ITEMID)));
                        demandModelObject.setBarcode(cursor.getString(cursor.getColumnIndex(MyTable.BARCODE)));
                        demandModelObject.setUnit(cursor.getString(cursor.getColumnIndex(MyTable.UNIT)));
                        arrayList.add(demandModelObject);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        return arrayList;
    }*/

/*    public int deleteData() {
        int result = 0;
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            result = sqLiteDatabase.delete(MyTable.TABLE_NAME, null, null);
        }
        return result;
    }

    public boolean deleteSingleItem(String itemId, String demandNumber) {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.delete(MyTable.TABLE_NAME, MyTable.ITEMID + "=? AND " + MyTable.DEMANDNUMBER + "=?", new String[]{itemId, demandNumber});
            return true;
        }
        return false;
    }

    public boolean deleteAllItemsInDemand(String demandNumber) {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.delete(MyTable.TABLE_NAME, MyTable.DEMANDNUMBER + "=?", new String[]{demandNumber});
            return true;
        }
        return false;
    }

    public boolean isTableEmpty() {
        SQLiteDatabase database = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(database, MyTable.TABLE_NAME);

        return NoOfRows == 0;
    }



    public Cursor getItem(String demandNumber, String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + MyTable.TABLE_NAME + " WHERE DEMANDNUMBER='" + demandNumber + "'and ITEMID='" + itemId + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean checkIfValueExistsInDemand(String demandNumber, String itemId) {
        String Query = "Select * from " + MyTable.TABLE_NAME + " where "
                + MyTable.DEMANDNUMBER + " = " + "'" + demandNumber + "'" + " and "
                + MyTable.ITEMID + " = " + "'" + itemId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        return true;*/

}
