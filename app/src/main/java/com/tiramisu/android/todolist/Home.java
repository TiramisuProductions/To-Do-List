package com.tiramisu.android.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tiramisu.android.todolist.Fragments.CalenderFragment;
import com.tiramisu.android.todolist.Fragments.CategoriesFragment;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.WorldEvent;
import com.tiramisu.android.todolist.service.FirebaseBackgroundService;

import org.greenrobot.eventbus.EventBus;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String RECEIVE_JSON = "com.your.package.RECEIVE_JSON";
    MyReceiver myReceiver;

    ServicetoFragment cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        cm = (ServicetoFragment)this;
        StaticVar.context = this;
        startService(new Intent(this,FirebaseBackgroundService.class));
        setContentView(R.layout.activity_navi_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);

      /*  bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_JSON);
        bManager.registerReceiver(bReceiver, intentFilter);
        String jsonString = "ajaj";
        Intent RTReturn = new Intent(Home.RECEIVE_JSON);
        RTReturn.putExtra("json", jsonString);*/
        //LocalBroadcastManager.getInstance(this).sendBroadcast(RTReturn);


        Intent i = getIntent();
        ////  Log.d("useruid",i.getStringExtra("uid"));
        StaticVar.context = Home.this;



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();


    }


    @org.greenrobot.eventbus.Subscribe
    public void onEvent(final WorldEvent event){
        // your implementation

                Log.d("japan","isgreat");

    //    CategoriesFragment categoriesFragment = new CategoriesFragment();
  //      FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.layout_content,categoriesFragment,categoriesFragment.getTag()).commit();


        };

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FirebaseBackgroundService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service



        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            int datapassed = arg1.getIntExtra("DATAPASSED", 0);

            Toast.makeText(Home.this,
                    "Triggered by Service!\n"
                            + "Data passed: " + String.valueOf(datapassed),
                    Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(new WorldEvent("Hello EventBus!"));



        }

    }


    public void init()
    {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_content,categoriesFragment,categoriesFragment.getTag()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi_drawer, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.categories) {
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layout_content,categoriesFragment,categoriesFragment.getTag()).commit();
            // Handle the camera action
        } else if (id == R.id.calender) {

            CalenderFragment calenderFragment =new CalenderFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layout_content,calenderFragment,calenderFragment.getTag()).commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
