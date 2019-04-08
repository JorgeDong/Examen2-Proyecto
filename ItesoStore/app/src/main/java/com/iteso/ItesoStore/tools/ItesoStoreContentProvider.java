package com.iteso.ItesoStore.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.iteso.ItesoStore.beans.Store;
import com.iteso.ItesoStore.beans.City;
import com.iteso.ItesoStore.database.DataBaseHandler;
import com.iteso.ItesoStore.database.ItemProductControl;
import com.iteso.ItesoStore.database.StoreControl;

import java.util.Arrays;

public class ItesoStoreContentProvider extends ContentProvider {
    DataBaseHandler dataBaseHandler;

    public ItesoStoreContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: 9Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        dataBaseHandler = DataBaseHandler.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(projection.length > 0 && projection[0].equals("getItemsProductsByCategory")){
            ItemProductControl itemProductControl = new ItemProductControl();
            return itemProductControl.getItemProducts(dataBaseHandler);
        }

        if(projection.length > 0 && projection[0].equals("addStore")){

            City city;

            Log.e("DB","Entro al insertgar");
            Log.e("DB",Arrays.toString(projection));
            StoreControl storeControl = new StoreControl();

            int cant = storeControl.getLastStoreId(dataBaseHandler);
            cant ++;
            Log.e("DB",Integer.toString(cant));
            if(projection[6].equals("Guadalajara")) {
                city = new City(1, "Guadalajara");
                Store store = new Store(cant, projection[1], projection[2], 4, Double.parseDouble(projection[4]),
                        Double.parseDouble(projection[5]), city);

                Log.e("DB",city.toString());
                storeControl.addStore(store, dataBaseHandler);
            }else if(projection[6].equals("Zapopan")){
                city = new City(2, "Zapopan");
                Store store = new Store(cant, projection[1], projection[2], 4, Double.parseDouble(projection[4]),
                        Double.parseDouble(projection[5]), city);

                Log.e("DB",city.toString());
                storeControl.addStore(store, dataBaseHandler);
            }else{
                city = new City(3, "Tonala");
                Store store = new Store(cant, projection[1], projection[2], 4, Double.parseDouble(projection[4]),
                        Double.parseDouble(projection[5]), city);

                Log.e("DB",city.toString());
                storeControl.addStore(store, dataBaseHandler);
            }
            return null;
            }
           // CityControl cityControl = new CityControl();
           // City city = cityControl.getCityByName(projection[6],dataBaseHandler);

            // Crear una nueva tienda


            //

/*
            Store store = new Store( storeControl.getLastStoreId(dataBaseHandler), projection[1], projection[2] ,
                                    Integer.parseInt(projection[3]), Double.parseDouble(projection[4]),
                                    Double.parseDouble(projection[5]), city);

*/

            // Crear una tienda
            // atributos del addstore
       //     storeControl.addStore(store, dataBaseHandler);
            return  null;
        }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
