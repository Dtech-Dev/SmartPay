package com.dtech.smartpulsa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.feature.PulsaActivity;
import com.dtech.smartpulsa.preference.PrefManager;
import com.google.firebase.iid.FirebaseInstanceId;

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
    Button btnIsiPulsa;
    Dialog dialogPulsa;
    PrefManager prefManager;
    public String textUser, txtEmail, txtFirebaseId;
    //private static final String PREF_NAME = "app-welcome";
    private static final String DISPLAY_NAME = "displayName";
    private static final String DISPLAY_EMAIL = "displayEmail";

    private String JSON_STRING, status, balance, point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);

        String token = FirebaseInstanceId.getInstance().getToken();
        prefManager.setFirebaseId(token);
        Log.d("Firebase id", "Refreshed token: " + token);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Embeks*/

        btnIsiPulsa = (Button) findViewById(R.id.btnIsiPulsa);
        btnIsiPulsa.setOnClickListener(this);
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        /*headerNav = layoutInflater.inflate(R.dialog_pulsa.nav_header_temp,null, true);*/
        headerNav = navigationView.getHeaderView(0);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        navusername = (TextView) headerNav.findViewById(R.id.navusername);
        navemail = (TextView) headerNav.findViewById(R.id.navemail);
        tbalance = (TextView) findViewById(R.id.tbalance);
        textUser = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        txtEmail = (sharedPreferences.getString(Config.DISPLAY_EMAIL, ""));
        txtFirebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));

        tTempor = (TextView) findViewById(R.id.textViewTempor);

        navemail.setText(txtEmail+"\n"+txtFirebaseId);

        getJson();

        splitString();


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
            navusername.setText(textUser+"("+status+" user)");
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_add_saldo) {
            Intent saldo = new Intent(TempActivity.this, AddSaldoActivity.class);
            startActivity(saldo);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIsiPulsa:
                isiPulsa();
                break;

        }
    }

    private void isiPulsa() {
        Intent intentTransaksi = new Intent(this, PulsaActivity.class);
        intentTransaksi.putExtra("transaksi", "isi pulsa");
        startActivity(intentTransaksi);
        /*final Intent intentPulsa = new Intent(getApplicationContext(), PulsaActivity.class);

        dialogPulsa = new Dialog(this);
        dialogPulsa.setContentView(R.layout.dialog_pulsa);
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
    }
}
