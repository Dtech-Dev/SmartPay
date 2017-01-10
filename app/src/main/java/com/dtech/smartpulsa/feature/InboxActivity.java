package com.dtech.smartpulsa.feature;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.data.DataInbox;
import com.dtech.smartpulsa.data.TagihanAdapter;
import com.dtech.smartpulsa.preference.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    RecyclerView recyclerTagihan;

    String email, json, detailToast;

    PrefManager prefManager;
    SharedPreferences sharedPreferences;
    TagihanAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);
        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        email = (sharedPreferences.getString(Config.DISPLAY_EMAIL, ""));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUi();

        getDataTagihan();
    }

    private void getDataTagihan() {
        class DataTagihanAsync extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(MainActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                json = s;
                showTagihan();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> paramsTagihan = new HashMap<>();
                paramsTagihan.put(Config.TAG_EMAIL_USER, email);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_SHOW_TAGIHAN,paramsTagihan);
                return s;
            }
        }

        DataTagihanAsync dataTagihanAsync = new DataTagihanAsync();
        dataTagihanAsync.execute();
    }

    private void showTagihan() {

        JSONObject jsonObject = null;
        List<DataInbox> data = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            //JSONObject c = result.getJSONObject(0);
            for (int i=0; i<result.length();i++) {
                JSONObject jo = result.getJSONObject(i);
                DataInbox dataTagihan = new DataInbox();
                /*String  jenis= jo.getString(Config.TAG_JENIS_TAGIHAN);
                String nomorTagihan = jo.getString(Config.TAG_NOMOR_TAGIHAN);
                String detail = jo.getString(Config.TAG_TAGIHAN);*/
                dataTagihan.ket = jo.getString(Config.TAG_KET_INBOX);
                dataTagihan.jenis = jo.getString(Config.TAG_JENIS_TAGIHAN);
                dataTagihan.ketag = jo.getString(Config.TAG_KET_TAGIHAN);
                dataTagihan.mes = jo.getString(Config.TAG_MES_INBOX);
                dataTagihan.idTagihan = jo.getString(Config.TAG_ID_TAGIH);
                /*dataTagihan.detailTagihan = "Tagihan "+jenis+ " dengan ID Pelanggan "
                +nomorTagihan+", jumlah yang harus dibayar adalah "+detail;*/
                detailToast = dataTagihan.detailTagihan;
                //Toast.makeText(this, dataTagihan.detailTagihan, Toast.LENGTH_LONG).show();

                data.add(dataTagihan);
            }

            //Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        mAdapter = new TagihanAdapter(InboxActivity.this, data);
        recyclerTagihan.setAdapter(mAdapter);
        recyclerTagihan.setLayoutManager(new LinearLayoutManager(InboxActivity.this));

    }

    private void initUi() {
        recyclerTagihan = (RecyclerView) findViewById(R.id.recyclerTagihan);
    }

}
