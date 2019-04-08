package com.iteso.itesoclient;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityStore extends AppCompatActivity {
    private Button save;

    private EditText name,phone, longitud, latitud,thumbnail;
    private Spinner city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        name = findViewById(R.id.activity_store_name);
        phone = findViewById(R.id.activity_store_phone);
        longitud = findViewById(R.id.activity_store_lon);
        latitud = findViewById(R.id.activity_store_lat);
      //  thumbnail = findViewById(R.id.activity_store_thum);
        city = findViewById(R.id.activity_store_cities);
        save = findViewById(R.id.activity_store_save);


        final String[] cities = getResources().getStringArray(R.array.cities);
        city.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, cities));


        // En este metodo poner el que llama a la base de datos
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertStore();
                    // Toast de insertar exitosamentes
                Context context = getApplicationContext();
                CharSequence text = "Tienda Insertada Exitosamente";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Regresar a actividad principal
               // Context context1 = getApplicationContext();

/*
                CharSequence text1 = name.getText().toString()
                 + phone.getText().toString()
                 +latitud.getText().toString() +longitud.getText().toString()
                + city.getSelectedItem().toString();


                //CharSequence text1 = city.getSelectedItem().toString();

                Toast toast1 = Toast.makeText(context1, text1, duration);
                toast1.show();
                */

                Intent intent = new Intent(ActivityStore.this, ActivityMain.class);
                startActivityForResult(intent, 1);
                setResult(Activity.RESULT_OK,intent);
                finish();


            }
        });

    }


    private void insertStore() {
        Uri uri = Uri.parse("content://com.iteso.test.PROVIDER");

        // Arreglo de Strings  String[] projection = {"Products"};
        String[] projection = new String[7];
        projection[0] = "addStore";
        projection[1] = name.getText().toString();
        projection[2] = phone.getText().toString();
        projection[3] = "4";
        projection[4] = latitud.getText().toString();
        projection[5] = longitud.getText().toString();
        projection[6] = city.getSelectedItem().toString();

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null,null);

        try{
            cursor.close();
        }catch (Exception e){

        }
    }




}
