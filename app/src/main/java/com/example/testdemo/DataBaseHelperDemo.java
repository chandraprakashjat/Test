package com.example.testdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class DataBaseHelperDemo extends SQLiteOpenHelper
{

    public static String TABLE_NAME = "Record";
    public static  int DB_VERSION =1;
    public static  String DATABASENAME = "STUDENT_RECORD";
    public   SQLiteDatabase database;
   public static DataBaseHelperDemo instance;

   public static DataBaseHelperDemo getInstance(Context context)
   {
       if(instance == null)
       {
         instance = new DataBaseHelperDemo(context);

       }
       return instance;
   }





    String table_create = "CREATE TABLE " + TABLE_NAME + "("
            + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "name" + " TEXT,"
            + "phone" + " TEXT,"+"age"+ " TEXT"+")";



    public DataBaseHelperDemo(Context context)
    {
        super(context, DATABASENAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        Log.e("DBOncreate",db+"");
        db.execSQL(table_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        //drop table if exist

        Log.e("onUpgrade",oldVersion+" and new version is "+newVersion);

        ArrayList<User> arrayList = null;
        //Take backup
       if(oldVersion == 1)
       {
           database = db;
           arrayList = getPreviousRecord();
       }

       db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
       onCreate(db);

        Log.e("Size",arrayList.size() + "");
         //save previous value


        if (oldVersion == 1)
        {

            for( User user: arrayList)
            {

                insetValueForVersion2( user);
            }
        }





    }

    private ArrayList<User> getPreviousRecord()
    {


        ArrayList<User> arrayList = new ArrayList<>();



        Cursor c = getDetail();

        if (c!=null && c.getCount()>0)
        {

            while (c.moveToNext())
            {

              String name = c.getString(c.getColumnIndex("name"));
              String phone = c.getString(c.getColumnIndex("phone"));
              String age = c.getString(c.getColumnIndex("age"));

               if (TextUtils.isEmpty(age))
               {
                age = "0";
               }

               arrayList.add(new User(name,phone,Long.parseLong(age)));

            }
        }

       return  arrayList;

    }


    public void insetValue( User user)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("phone",user.getPhoneNumber());
        values.put("age",user.getAge());

        long check = db.insert(TABLE_NAME,"name" ,values);

       Log.e("Test",check + "");
    }

    public void insetValueForVersion2( User user)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("phone",user.getPhoneNumber());
        values.put("age",user.getAge());
        values.put("detail",user.getDetail());
        long check = db.insert(TABLE_NAME,"name" ,values);

        Log.e("Test",check + "");
    }


    public Cursor getDetail() throws CancellationException , OperationCanceledException
    {
       Cursor cursor = null;





     // String [] args = new String[]{"%" + name +"%" };

     //String sql = "SELECT * FROM "+TABLE_NAME + " WHERE  age =?";

       // String sql = "SELECT * FROM "+TABLE_NAME ;

      //cursor = database.rawQuery(sql,args);


        cursor =database.query(TABLE_NAME,null,null,null,null,null,null,null);




      return  cursor;

    }

    public synchronized SQLiteDatabase openReadable()
    {
       return database = instance.getReadableDatabase();
    }

    public synchronized SQLiteDatabase openWritable()
    {
        return database = instance.getWritableDatabase();
    }

    public void onClose()
    {
      database.close();
    }

    public void updateRecord()
    {
      openWritable();

      ContentValues cv = new ContentValues();
      cv.put("name","ptest");

     int value = database.update(TABLE_NAME,cv,"age !=?",new String[]{"25"});

     Log.e("updateValues",value+" are");

     onClose();

    }
}
