package com.tiramisu.android.todolist.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.Home;
import com.tiramisu.android.todolist.Model.Category;
import com.tiramisu.android.todolist.Model.CategoryLocal;
import com.tiramisu.android.todolist.Model.KEYS;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.WorldEvent;
import com.tiramisu.android.todolist.ServicetoFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by sarveshpalav on 18/08/17.
 */

public class FirebaseBackgroundService extends Service {

    DatabaseReference lastupdatedref, todoref,categoryref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ArrayList<String> categoryNamelist = new ArrayList<String>();
    private ArrayList<String> categoryIdlist = new ArrayList<String>();
    private ArrayList<String> taskFolderIdlist = new ArrayList<String>();
    int no_of_categories = 0;
    int CategoryCounter = 0;
    int taskcounter = 0;
    public final static String MY_ACTION = "MY_ACTION";
    int counter =0;
    String  lastupdated="none";




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        FirebaseApp.initializeApp(FirebaseBackgroundService.this);
        todoref = FirebaseDatabase.getInstance().getReference("Todo");
        categoryref = todoref.child("4PmAwSIoFHfK7WFx1YVghucay4A3/Categories");
        lastupdatedref = todoref.child("4PmAwSIoFHfK7WFx1YVghucay4A3/Last_Updated");
        MyThread myThread = new MyThread();
        myThread.start();






       /* lastupdatedref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    Log.d("breath",dataSnapshot.getValue().toString());
                    lastupdated = dataSnapshot.getValue().toString().trim();
                    updatelocaldb();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/








        return super.onStartCommand(intent, flags, startId);
    }

    public class MyThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub

            pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            editor=pref.edit();



            Log.d("Preflol",pref.getString(KEYS.LASTUPDATED,"hj"));








          lastupdatedref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Tupperware",dataSnapshot.getValue().toString());
                    long i = Long.parseLong(dataSnapshot.getValue().toString().trim());
                    Log.d("wolla",""+dataSnapshot.getChildrenCount());




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            categoryref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Category.deleteAll(Category.class);
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {

CategoryCounter++;
                        Log.d("Counter",""+CategoryCounter);
                        Log.d("kick",snapshot.getKey());
                        Log.d("Children",""+dataSnapshot.getChildrenCount());

                        Log.d("bhosdk",snapshot.child("Category Name").getValue().toString());
                        Category category = new Category(snapshot.getKey(),Long.valueOf(snapshot.child("Category Name").getValue().toString()));
                        category.save();
                        if(CategoryCounter==dataSnapshot.getChildrenCount())
                        {
                            Log.d("Nvidia","Nvidia");
                            CategoryCounter=0;
                            Intent intent = new Intent();
                            intent.setAction(MY_ACTION);

                            intent.putExtra("DATAPASSED", "lol");

                            sendBroadcast(intent);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            stopSelf();
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
















}




