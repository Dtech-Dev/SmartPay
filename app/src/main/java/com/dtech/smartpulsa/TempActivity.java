package com.dtech.smartpulsa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.feature.DompetActivity;
import com.dtech.smartpulsa.feature.InboxActivity;
import com.dtech.smartpulsa.preference.PrefManager;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.pkmmte.view.CircularImageView;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class TempActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    LayoutInflater layoutInflater ;
    View headerNav;
    TextView navemail, navusername, tbalance;
    ImageButton imageButton;
    CircularImageView imageAcc;
    PrefManager prefManager;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    public String textUser, txtEmail, txtFirebaseId;
    DrawerLayout drawer;
    CircleMenu circleMenu;
    TourGuide mTourGuideHandler, mTourGuideHandler2, mTourGuideHandler3;
    private static int SPLASH_TIME_OUT = 1100;

    String imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        prefManager = new PrefManager(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*Embeks*/

        initUi();
        if (prefManager.isTempFirstTimeLaunch()) {
            initTour();
        }


    }

    private void initTour() {
       mTourGuideHandler = TourGuide.init(this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle("Selamat Datang di Pay Point").setDescription("Klik Button Utama tersebut untuk kami perkenalkan beberapa fitur di Pay Point"))
                .setOverlay(new Overlay())
                .playOn(circleMenu);

        mTourGuideHandler2 = TourGuide.init(this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle("Menu Utama").setDescription("Menu kami desain dalam bentuk icon. Click 'X' untuk menutup menu"))
                .setOverlay(new Overlay());
        mTourGuideHandler3 = TourGuide.init(this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(new ToolTip().setTitle("Menu Navigasi").setDescription("Keterangan dari ikon menu sebelumnya bisa anda liat di sini (membuka Menu Navigasi juga bisa anda lakukan dengan menggeser bagian kiri layar anda ke arah kanan)"))
                .setOverlay(new Overlay());

    }

    private void initUi() {

        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        /*headerNav = layoutInflater.inflate(R.dialog_kota.nav_header_temp,null, true);*/
        headerNav = navigationView.getHeaderView(0);
        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        navusername = (TextView) headerNav.findViewById(R.id.navusername);
        navemail = (TextView) headerNav.findViewById(R.id.navemail);
        tbalance = (TextView) findViewById(R.id.tbalance);
        imageAcc = (CircularImageView) headerNav.findViewById(R.id.imageViewAcc);
        //Uri setImgAcc = Uri.parse();
        imguri = (sharedPreferences.getString(Config.DISPLAY_ID, ""));
        Glide.with(getApplicationContext()).load(imguri)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageAcc);

        textUser = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        txtEmail = (sharedPreferences.getString(Config.DISPLAY_EMAIL, ""));
        txtFirebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));

        //tTempor = (TextView) findViewById(R.id.textViewTempor);

        navemail.setText(txtEmail);
        navusername.setText(textUser);

        imageButton = (ImageButton) findViewById(R.id.openNav);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);

                if (prefManager.isTempFirstTimeLaunch()) {
                   mTourGuideHandler3.cleanUp();
                        //mTourGuideHandler3.playOn(imageButton);
                    prefManager.setTempFirstTimeLaunch(false);
                }

            }
        });

        /*circle menu*/
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#5481a2"), R.mipmap.ic_local_parking_white_24dp, R.mipmap.ic_close_white_24dp);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.mipmap.ic_grain_white_24dp)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.ic_mail_outline_white_24dp)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.ic_assistant_white_24dp)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.ic_airport_shuttle_white_24dp)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.ic_monetization_on_white_24dp);

        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {

                                                 @Override
                                                 public void onMenuSelected(int index) {
                                                     switch (index) {
                                                         case 0:
                                                             Toast.makeText(TempActivity.this, "Go to Transaction", Toast.LENGTH_SHORT).show();
                                                             new Handler().postDelayed(new Runnable() {

                                                                 @Override
                                                                 public void run() {
                                                                     // This method will be executed once the timer is over
                                                                     // Start your app main activity
                                                                     Intent transaksi = new Intent(TempActivity.this, TransactActivity.class);
                                                                     startActivity(transaksi);

                                                                 }
                                                             }, SPLASH_TIME_OUT);


                                                             break;
                                                         case 1:
                                                             Toast.makeText(TempActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                                                             new Handler().postDelayed(new Runnable() {

                                                                 @Override
                                                                 public void run() {
                                                                     // This method will be executed once the timer is over
                                                                     // Start your app main activity
                                                                     Intent inbox = new Intent(TempActivity.this, InboxActivity.class);
                                                                     startActivity(inbox);

                                                                 }
                                                             }, SPLASH_TIME_OUT);

                                                             break;
                                                         case 2:
                                                             Toast.makeText(TempActivity.this, "Tambah Saldo", Toast.LENGTH_SHORT).show();
                                                             new Handler().postDelayed(new Runnable() {

                                                                 @Override
                                                                 public void run() {
                                                                     // This method will be executed once the timer is over
                                                                     // Start your app main activity
                                                                     Intent saldo = new Intent(TempActivity.this, AddSaldoActivity.class);
                                                                     startActivity(saldo);

                                                                 }
                                                             }, SPLASH_TIME_OUT);

                                                             break;
                                                         case 3:
                                                             Toast.makeText(TempActivity.this, "Coming Soon :\nPemesanan Tiket", Toast.LENGTH_SHORT).show();

                                                             break;
                                                         case 4:
                                                             Toast.makeText(TempActivity.this, "Dompet Clicked", Toast.LENGTH_SHORT).show();
                                                             new Handler().postDelayed(new Runnable() {

                                                                 @Override
                                                                 public void run() {
                                                                     // This method will be executed once the timer is over
                                                                     // Start your app main activity
                                                                     Intent dompet = new Intent(TempActivity.this, DompetActivity.class);
                                                                     startActivity(dompet);

                                                                 }
                                                             }, SPLASH_TIME_OUT);
                                                             //getJson();
                                                             break;
                                                     }
                                                 }
                                             }

        );

        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                                                     @Override
                                                     public void onMenuOpened() {
                                                         //Toast.makeText(TempActivity.this, "Menu Opend", Toast.LENGTH_SHORT).show();
                                                         if (prefManager.isTempFirstTimeLaunch()) {
                                                             mTourGuideHandler.cleanUp();
                                                             mTourGuideHandler2.playOn(circleMenu);
                                                         }
                                                     }

                                                     @Override
                                                     public void onMenuClosed() {
                                                         //Toast.makeText(TempActivity.this, "Menu Closed", Toast.LENGTH_SHORT).show();
                                                         if (prefManager.isTempFirstTimeLaunch()) {
                                                             mTourGuideHandler2.cleanUp();
                                                             mTourGuideHandler3.playOn(imageButton);
                                                         }
                                                     }
                                                 }
        );
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
        getMenuInflater().inflate(R.menu.temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.transaksinav) {
            // Handle the camera action
            Intent transaksi = new Intent(TempActivity.this, TransactActivity.class);
            startActivity(transaksi);
        } else if (id == R.id.inboxnav) {
            Intent inbox = new Intent(this, InboxActivity.class);
            startActivity(inbox);

        } else if (id == R.id.tambahsaldonav) {
            Intent saldo = new Intent(TempActivity.this, AddSaldoActivity.class);
            startActivity(saldo);

        } else if (id == R.id.tiketnav) {
            Toast.makeText(TempActivity.this, "Coming Soon :\nPemesanan Tiket", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.dompetnav) {
            Intent dompett = new Intent(TempActivity.this, DompetActivity.class);
            startActivity(dompett);
        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
