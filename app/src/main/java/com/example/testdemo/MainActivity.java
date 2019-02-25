package com.example.testdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity implements CancellationSignal.OnCancelListener
{

    EditText name,phone,age;
    Button bInsert,bUpdate,bDelete,bQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CancellationSignal cancellationSignal = new CancellationSignal();

        cancellationSignal.setOnCancelListener(this);

        name = findViewById(R.id.editText7);
        phone = findViewById(R.id.editText9);
        age = findViewById(R.id.editText10);



        bInsert = findViewById(R.id.button_insert);
        bUpdate = findViewById(R.id.button_update);
        bDelete = findViewById(R.id.button_delete);
        bQuery = findViewById(R.id.button_query);


        bInsert.setOnClickListener(new View.OnClickListener()
                                   {
            @Override
            public void onClick(View v)
            {
                String name1 = name.getText().toString();
                String phone1 = phone.getText().toString();
                long age1 = 0;
                if (!TextUtils.isEmpty(age.getText()))
                {
                  age1 = Long.parseLong(age.getText().toString());
                }

                User user = new User(name1,phone1,age1);
                DataBaseHelperDemo dataBaseHelperDemo = new DataBaseHelperDemo(MainActivity.this);

                dataBaseHelperDemo.insetValue(user);
            }
        }
        );

        bUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DataBaseHelperDemo dataBaseHelperDemo = DataBaseHelperDemo.getInstance(MainActivity.this);



                dataBaseHelperDemo.updateRecord();

            }
        }
        );

        bDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        }
        );


        bQuery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataBaseHelperDemo dataBaseHelperDemo = DataBaseHelperDemo.getInstance(MainActivity.this);

                SQLiteDatabase database = dataBaseHelperDemo.openReadable();

                Cursor cursor = dataBaseHelperDemo.getDetail();



                if(cursor != null )
                {
                    Log.e("detail",cursor.getCount()+"" + "/"+cursor);

                    while (cursor.moveToNext())
                    {
                        Log.e("test","name");
                        int position = cursor.getColumnIndexOrThrow("name");

                        int name = cursor.getColumnIndexOrThrow("phone");

                        int age = cursor.getColumnIndexOrThrow("age");

                        Log.e("resultValue",cursor.getString(position) + "/"+ cursor.getString(name) + "/"+ cursor.getString(age));




                    };

                }

               cursor.close();
               database.close();

            }
        }
        );



    }

    @Override
    public void onCancel() {

        Log.e("Oncancel","onCancel");
    }
}
