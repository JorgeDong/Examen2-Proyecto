package com.iteso.ItesoStore.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iteso.ItesoStore.beans.ItemProduct;

import java.util.ArrayList;

public class ItemProductControl {

    public CategoryControl categoryControl = new CategoryControl();
    public StoreControl storeControl = new StoreControl();


    public void addItemProduct(ItemProduct itemProduct, DataBaseHandler dh){

         int lastIdStoreProducts = 0;
         int lastIdProducts = 0;
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();


        String getCountProduct = "SELECT COUNT(" + DataBaseHandler.KEY_PRODUCT_ID
                + ") FROM " + DataBaseHandler.TABLE_PRODUCT;
        Cursor cursor_nip = db.rawQuery(getCountProduct, null);
        cursor_nip.moveToNext();
        lastIdProducts = cursor_nip.getInt(0) + 1;

        itemProduct.setCode(lastIdProducts);
        values.put(DataBaseHandler.KEY_PRODUCT_ID, lastIdProducts);
        values.put(DataBaseHandler.KEY_PRODUCT_TITLE, itemProduct.getTitle());
        values.put(DataBaseHandler.KEY_PRODUCT_IMAGE, itemProduct.getImage());
        values.put(DataBaseHandler.KEY_PRODUCT_CATEGORY, itemProduct.getCategory().getId());
        db.insert(DataBaseHandler.TABLE_PRODUCT,null,values);

        values.clear();

        String getCountStoreProduct = "SELECT COUNT(" + DataBaseHandler.STOREPRODUCT_ID
                + ") FROM " + DataBaseHandler.TABLE_STOREPRODUCT;

        Cursor cursor_nisp = db.rawQuery(getCountStoreProduct, null);
        cursor_nisp.moveToNext();
        lastIdStoreProducts = cursor_nisp.getInt(0) + 1;

        values.put(DataBaseHandler.STOREPRODUCT_ID, lastIdStoreProducts);
        values.put(DataBaseHandler.STOREPRODUCT_IDPRODUCT, itemProduct.getCode());
        values.put(DataBaseHandler.STOREPRODUCT_IDSTORE, itemProduct.getStore().getId());
        db.insert(DataBaseHandler.TABLE_STOREPRODUCT,null,values);
        try{
            db.close();
        }catch (Exception e){

        }
    }

    public ArrayList<ItemProduct> getItemProductsByCategory(int idCategory, DataBaseHandler dh){
        ArrayList<ItemProduct> itemProducts = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();
        String select = "SELECT p." + DataBaseHandler.KEY_PRODUCT_ID + ","
                + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                + DataBaseHandler.KEY_PRODUCT_IMAGE + ","
                + DataBaseHandler.STOREPRODUCT_IDSTORE + ","
                + DataBaseHandler.KEY_PRODUCT_CATEGORY
                + " FROM " + DataBaseHandler.TABLE_PRODUCT + " p JOIN "
                + DataBaseHandler.TABLE_STOREPRODUCT + " sp ON p."
                + DataBaseHandler.KEY_PRODUCT_ID + " = sp." + DataBaseHandler.STOREPRODUCT_IDPRODUCT
                + " WHERE " + DataBaseHandler.KEY_PRODUCT_CATEGORY + " = "+ idCategory;
        Cursor cursor = db.rawQuery(select, null);
        while(cursor.moveToNext()){
            ItemProduct itemProduct = new ItemProduct();
            itemProduct.setCode(cursor.getInt(0));
            itemProduct.setTitle(cursor.getString(1));
            itemProduct.setImage(cursor.getInt(2));
            itemProduct.setStore(storeControl.getStoreById(cursor.getInt(3), dh));
            itemProduct.setCategory(categoryControl.getCategoryById(cursor.getInt(4), dh));
            itemProducts.add(itemProduct);
        }
        try{
            cursor.close();
            db.close();
        }catch (Exception e){

        }
        return itemProducts;
    }

    public Cursor getItemProducts(DataBaseHandler dh){
        SQLiteDatabase db = dh.getReadableDatabase();

        String select = "SELECT p."
                + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                + DataBaseHandler.KEY_STORE_NAME + ","
                + DataBaseHandler.KEY_PRODUCT_CATEGORY
                + " FROM " + DataBaseHandler.TABLE_PRODUCT +
                " p JOIN " + DataBaseHandler.TABLE_STOREPRODUCT +
                " sp ON p." + DataBaseHandler.KEY_PRODUCT_ID + " = sp." + DataBaseHandler.STOREPRODUCT_IDPRODUCT +
                " JOIN " + DataBaseHandler.TABLE_STORE + " st ON sp." + DataBaseHandler.STOREPRODUCT_ID + " = st." +
                DataBaseHandler.KEY_STORE_ID;


        return db.rawQuery(select, null);
    }
}
