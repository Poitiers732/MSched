package com.example.wzdrodowski.msched.config;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
