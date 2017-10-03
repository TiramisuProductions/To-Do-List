package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;



import com.tiramisu.android.todolist.Fragments.CalendarFragment;
import com.tiramisu.android.todolist.Fragments.CategoriesFragment;
import com.tiramisu.android.todolist.Fragments.GoalsFragment;
import com.tiramisu.android.todolist.Model.StaticVar;


import butterknife.ButterKnife;

import static com.tiramisu.android.todolist.TAGS.MyPREFERENCES;
import static com.tiramisu.android.todolist.TAGS.SHAREDPREFUID;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     ImageView overflowmenu;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_home);
        overflowmenu = (ImageView)findViewById(R.id.overflowmenu);


        if(getIntent()!=null) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(SHAREDPREFUID, getIntent().getStringExtra("uid"));
            editor.commit();

        }

        StaticVar.UID = sharedpreferences.getString(SHAREDPREFUID,null);



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       // drawer.setDrawerListener(toggle);
      //  toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        overflowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
drawer.openDrawer(Gravity.LEFT);
            }
        });

        init();


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

            CalendarFragment calenderFragment =new CalendarFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layout_content,calenderFragment,calenderFragment.getTag()).commit();

        }

         else if (id == R.id.goals) {

            GoalsFragment goalsFragment=new GoalsFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layout_content,goalsFragment,goalsFragment.getTag()).commit();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
