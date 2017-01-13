package com.dtech.smartpulsa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.feature.InboxActivity;
import com.dtech.smartpulsa.feature.PaketDataActivity;
import com.dtech.smartpulsa.feature.PulsaActivity;
import com.dtech.smartpulsa.feature.QuickPayActivity;
import com.dtech.smartpulsa.feature.TagihanActivity;
import com.dtech.smartpulsa.feature.VoucherActivity;
import com.dtech.smartpulsa.preference.PrefManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class TempActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    LayoutInflater layoutInflater ;
    View headerNav;
    TextView navemail, navusername, tbalance, tTempor;
    Button btnIsiPulsa, btnCekTagihan, btnToken, btnVoucher, btnPaketData;
    Dialog dialogPulsa;
    ImageButton imageButton;
    PrefManager prefManager;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    public String textUser, txtEmail, txtFirebaseId;
    DrawerLayout drawer;
    CircleMenu circleMenu;
    private static int SPLASH_TIME_OUT = 1100;
    //private static final String PREF_NAME = "app-welcome";
    //private static final String DISPLAY_NAME = "displayName";
    //private static final String DISPLAY_EMAIL = "displayEmail";

    private String JSON_STRING, status, balance, point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        prefManager = new PrefManager(this);
        /*if (!prefManager.isFirstTimeLaunch()) {
            launchLogin();
            //finish();
        }
*/

        /*String token = FirebaseInstanceId.getInstance().getToken();
        prefManager.setFirebaseId(token);*/
        //Log.d("Firebase id", "Refreshed token: " + token);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent quickPay = new Intent(TempActivity.this, QuickPayActivity.class);
                startActivity(quickPay);

                drawer.openDrawer(GravityCompat.START);

            }
        });*/




        /*Embeks*/



        initUi();
        //getJson();

        //splitString();


    }

    private void launchLogin() {

    }

    private void initUi() {
        /*btnIsiPulsa = (Button) findViewById(R.id.btnIsiPulsa);
        btnCekTagihan = (Button) findViewById(R.id.btnCekTagihan);
        btnToken = (Button) findViewById(R.id.btnToken);
        btnVoucher = (Button) findViewById(R.id.btnVoucher);
        btnPaketData = (Button) findViewById(R.id.btnpaketdata);
        btnPaketData.setOnClickListener(this);
        btnVoucher.setOnClickListener(this);
        btnToken.setOnClickListener(this);
        btnCekTagihan.setOnClickListener(this);
        btnIsiPulsa.setOnClickListener(this);*/
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        /*headerNav = layoutInflater.inflate(R.dialog_kota.nav_header_temp,null, true);*/
        headerNav = navigationView.getHeaderView(0);
        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        navusername = (TextView) headerNav.findViewById(R.id.navusername);
        navemail = (TextView) headerNav.findViewById(R.id.navemail);
        tbalance = (TextView) findViewById(R.id.tbalance);
        textUser = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        txtEmail = (sharedPreferences.getString(Config.DISPLAY_EMAIL, ""));
        txtFirebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));

        //tTempor = (TextView) findViewById(R.id.textViewTempor);

        navemail.setText(txtEmail+"\n"+txtFirebaseId);

        imageButton = (ImageButton) findViewById(R.id.openNav);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
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
                                                             Toast.makeText(TempActivity.this, "Transaction Clicked", Toast.LENGTH_SHORT).show();
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
                                                             Toast.makeText(TempActivity.this, "Inbox Clicked", Toast.LENGTH_SHORT).show();
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
                                                             Toast.makeText(TempActivity.this, "Tambah DanaClciked", Toast.LENGTH_SHORT).show();
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
                                                             getJson();
                                                             break;
                                                     }
                                                 }
                                             }

        );

        /*circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

                                                     @Override
                                                     public void onMenuOpened() {
                                                         Toast.makeText(TempActivity.this, "Menu Opend", Toast.LENGTH_SHORT).show();
                                                     }

                                                     @Override
                                                     public void onMenuClosed() {
                                                         Toast.makeText(TempActivity.this, "Menu Closed", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
        );*/
    }

    private void splitString() {
        String kalimat = "Trx: 385716875, Pengisian SUKSES. Semua TRX Lancar: te100.085731706444.3003, Harga: 10550, sisa saldo: .3 133189 SN:00704700000195581837";
        String kata;
        String[] arrayKalimat = kalimat.split("\\s");
        String search = ".3003";
        int indexKode = Arrays.asList(arrayKalimat).indexOf(search);
        int start = kalimat.indexOf(".3003")-18;
        int end = kalimat.indexOf(".3003")+5;
        String kode = kalimat.substring(start, end);
        String[] arrayKodeA = kode.split("\\s");
        //int indexKode = Arrays.binarySearch(arrayKalimat, ".3003");
        int countKlimat = arrayKalimat.length;
        //String tempor = arrayKalimat[indexKode];
        for (int i = 0; i < countKlimat; i++) {
            kata = arrayKalimat[i];
             //int searcha = arrayKalimat;
            int indexKodea = Arrays.binarySearch(arrayKalimat, kode);
            Log.d("Tag Array Kalimat", "index ke " + i + " = " + kata + "\n");
            //tempor = "index ke " + i + " = " + kata + "\n";

        }
        //tTempor.setText("array ke "+"="+ kata+"\n");
        //tTempor.setText(tempor);
    }

    public void getJson() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TempActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showCustomer(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_SELECT_ALL, txtEmail);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void showCustomer(String json) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            status = c.getString(Config.TAG_STATUS);
            balance = c.getString(Config.TAG_BALANCE);
            point = c.getString(Config.TAG_POINT);

            if (point == "null") {
                point = "0";
            }

            //String saldo=NumberFormat.getNumberInstance(Locale.US).format(balance);
            //navusername.setText(textUser+"("+status+" user)");
            tbalance.setText(balance+"\n"+point);

            Toast.makeText(TempActivity.this, status, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            getJson();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btnIsiPulsa:
                isiPulsa();
                break;
            case R.id.btnCekTagihan:
                frCekTagihan();
                //openTagihan();
                break;
            case R.id.btnToken:
                isiToken();
                break;
            case R.id.btnVoucher:
                voucherGame();
                break;
            case R.id.btnpaketdata:
                paketData();
        }*/
    }

    /*private void paketData() {
        Intent paketDataOpen = new Intent(this, PaketDataActivity.class);
        startActivity(paketDataOpen);
    }

    private void voucherGame() {
        Intent voucherGameOpen = new Intent(this, VoucherActivity.class);
        startActivity(voucherGameOpen);
    }

    private void isiToken() {
        Intent tokenOpen = new Intent(this, TokenActivity.class);
        startActivity(tokenOpen);
    }

    private void openTagihan() {
        Intent tagihanOpen = new Intent(this, TagihanActivity.class);
        startActivity(tagihanOpen);
    }

    private void frCekTagihan() {
        Intent intentCek = new Intent(this, PulsaActivity.class);
        intentCek.putExtra("transaksi", "cek");
        startActivity(intentCek);
    }

    private void isiPulsa() {
        Intent intentTransaksi = new Intent(this, PulsaActivity.class);
        intentTransaksi.putExtra("transaksi", "isi pulsa");
        startActivity(intentTransaksi);*/
        /*final Intent intentPulsa = new Intent(getApplicationContext(), PulsaActivity.class);

        dialogPulsa = new Dialog(this);
        dialogPulsa.setContentView(R.layout.dialog_kota);
        dialogPulsa.setTitle("Choose One");

        Button selfNumber = (Button) dialogPulsa.findViewById(R.id.selfNumber);
        selfNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentSelf = new Intent(TempActivity.this, PulsaActivity.class);
                intentPulsa.putExtra("self", "selfNumber");
                startActivity(intentPulsa);
                dialogPulsa.dismiss();
            }
        });

        Button otherNumber = (Button) dialogPulsa.findViewById(R.id.otherNumber);
        otherNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentPulsa.putExtra("self", "otherNumber");
                startActivity(intentPulsa);
                dialogPulsa.dismiss();
            }
        });
        dialogPulsa.show();*/
    //}
}
