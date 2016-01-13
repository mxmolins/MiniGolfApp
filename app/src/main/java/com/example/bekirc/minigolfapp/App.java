package com.example.bekirc.minigolfapp;

import android.app.Application;

import timber.log.Timber;

/**
 * @author bekirc on 02.12.15.
 */
public class App extends Application {
    private static App instance;

    public App() {
        super();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
