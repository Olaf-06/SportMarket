package com.example.sportmarket;

import android.app.Application;

import androidx.room.Room;

import com.example.sportmarket.room.Database;
import com.onesignal.OneSignal;

public class App extends Application {

    private static final String ONESIGNAL_APP_ID = "b9bec8e1-e881-44f4-8ed1-6a51862cbfd0";

    public static App instance;

    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, Database.class, "database")
                .allowMainThreadQueries()
                .build();

        oneSignalStart();
    }

    public void oneSignalStart() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    public static App getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }
}
