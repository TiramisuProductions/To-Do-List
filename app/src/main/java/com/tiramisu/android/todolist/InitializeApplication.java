package com.tiramisu.android.todolist;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.orm.SugarApp;

/**
 * Created by Sarvesh Palav on 24-09-2017.
 */

public class InitializeApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
