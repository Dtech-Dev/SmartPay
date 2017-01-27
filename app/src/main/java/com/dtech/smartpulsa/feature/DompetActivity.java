package com.dtech.smartpulsa.feature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.AddSaldoActivity;
import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.TempActivity;
import com.dtech.smartpulsa.preference.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DompetActivity extends AppCompatActivity {

    TextView tsaldo, tpoin;
    String JSON_STRING;
    String status;
    String balance;
    String point;
    String txtEmail;

    PrefManager prefManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dompet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(DompetActivity.this, AddSaldoActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniUi();
        getJson();
    }

    private void getJson() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DompetActivity.this,"Fetching Data","Wait...",false,false);
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

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void showCustomer(String json) {
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
            tsaldo.setText(balance);
            tpoin.setText(point);

            Toast.makeText(DompetActivity.this, status, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void iniUi() {
        tsaldo = (TextView) findViewById(R.id.tsaldo);
        tpoin = (TextView) findViewById(R.id.tpoin);

        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        txtEmail = (sharedPreferences.getString(Config.DISPLAY_EMAIL, ""));
    }

}
