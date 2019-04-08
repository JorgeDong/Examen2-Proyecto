package com.iteso.ItesoStore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iteso.test.R;
import com.iteso.ItesoStore.beans.City;
import com.iteso.ItesoStore.beans.Store;
import com.iteso.ItesoStore.beans.User;
import com.iteso.ItesoStore.database.DataBaseHandler;
import com.iteso.ItesoStore.database.ItemProductControl;
import com.iteso.ItesoStore.database.StoreControl;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {

    public static final String MY_PREFERENCES = "com.iteso.sesion13.PREFERENCES";

    public StoreControl storeControl = new StoreControl();
    public ItemProductControl itemProductControl = new ItemProductControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task= new TimerTask(){
            @Override
            public void run() {
                DataBaseHandler dh = DataBaseHandler.getInstance(ActivitySplash.this);
                ArrayList<Store> stores = storeControl.getStores(dh);
                if(stores.size() == 0){
                    Store store1 = new Store(1,"BestBuy","123456789",
                                                1,20.4500,-103.1600, new City(1, "Zapopan"));
                    storeControl.addStore(store1, dh);
                    Store store2 = new Store(2,"HomeDepot","123456789",
                                                2,19.2542,-99.0739, new City(2, "Tonala"));
                    storeControl.addStore(store2, dh);
                    Store store3 = new Store(3,"Liverpool","123456789",
                                                3,25.6714,-100.3040, new City(3, "Guadalajara"));

                    storeControl.addStore(store3, dh);
                }
                Intent intent = new Intent(ActivitySplash.this,ActivityMain.class);
                startActivity(intent);
                finish();
                /*User user = loadPreferences();
                if(user.isLogged()){
                    Intent intent = new Intent(ActivitySplash.this,ActivityMain.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);


    }

    public User loadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCES,MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString("NAME","UNKNOWN"));
        user.setPassword(sharedPreferences.getString("PWD","1234"));
        user.setLogged(sharedPreferences.getBoolean("LOGGED",false));
        return user;
    }
}