package com.tiramisu.android.todolist.Introslider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.tiramisu.android.todolist.Home;

import com.tiramisu.android.todolist.NewUserForm;

import com.tiramisu.android.todolist.R;

import static android.R.attr.permission;

public class Welcome extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;

    private SharedPreferences permissionStatus;
    private  Intent i ;

    static final Integer LOCATION = 100;
    static final Integer CALL = 200;
    static final Integer WRITE_EXST = 300;
    static final Integer READ_EXST = 400;
    static final Integer CAMERA = 500;
    static final Integer ACCOUNTS = 600;
    static final Integer GPS_SETTINGS = 700;

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;

    String locationPermi = Manifest.permission.ACCESS_FINE_LOCATION;
    String callPermi = Manifest.permission.CALL_PHONE;
    String writeStoragePermi = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String readStoragePermi = Manifest.permission.READ_EXTERNAL_STORAGE;
    String cameraPermi = Manifest.permission.CAMERA;
    String accountsPermi = Manifest.permission.GET_ACCOUNTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = getIntent();
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        // Checking for first time launch - before calling setContentView()


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {

        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(getApplicationContext(), Home.class));

        //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Intent j = new Intent(Welcome.this, NewUserForm.class);
        j.putExtra("name",i.getStringExtra("name"));
        j.putExtra("uid",i.getStringExtra("uid"));
        j.putExtra("email",i.getStringExtra("email"));
        startActivity(j);

        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }

            if(position == 3){
                askForPermission();
            }
        }



        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    private void askForPermission(){

        if (ContextCompat.checkSelfPermission(Welcome.this, locationPermi) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(Welcome.this, callPermi) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(Welcome.this, writeStoragePermi) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(Welcome.this, readStoragePermi) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(Welcome.this, cameraPermi) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(Welcome.this, accountsPermi) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, locationPermi)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, callPermi)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, writeStoragePermi)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, readStoragePermi)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, cameraPermi)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(Welcome.this, accountsPermi)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.



            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Welcome.this,
                        new String[]{locationPermi,callPermi,writeStoragePermi,readStoragePermi,cameraPermi,accountsPermi},
                        PERMISSION_CALLBACK_CONSTANT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {

            /*case LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case CALL:{}
            */

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
