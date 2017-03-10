package com.dtech.smartpulsa.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridVoucher;
import com.dtech.smartpulsa.data.AdapterPaket;
import com.dtech.smartpulsa.data.DataPaket;
import com.dtech.smartpulsa.data.DataPul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailHistActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridPaket;
    RelativeLayout layMain, layDetail;
    ImageView imgjnspaket;
    TextView txtjnspaket;
    Button btnmainpaket;
    RecyclerView recyclerPaket;
    AdapterPaket madapter;


    private String json_string;

    List<DataPul> datapulsa;
    private TextView titlehist;
    EditText eded;
    Button bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUi();

        //Bundle bundle = getIntent().getBundleExtra("extra");
        String title = getIntent().getStringExtra("title");

        if (title.matches("pulsa")) {
            titlehist.setText(title);
            /*datapulsa = (List<DataPul>) bundle.getSerializable("ob");
            StringBuilder builder = new StringBuilder();
            for (int a =0 ;a < datapulsa.size(); a++) {
                builder.append(datapulsa.get(a) + "\n");
            }
            Toast.makeText(DetailHistActivity.this, builder+"\n"+datapulsa.size(), Toast.LENGTH_LONG).show();*/
        }


    }



    private void initUi() {
        titlehist = (TextView) findViewById(R.id.titlehist);
        eded = (EditText) findViewById(R.id.eded);
        bb = (Button) findViewById(R.id.bb);
        bb.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bb:
                backToMainPaket();
        }
    }

    private void backToMainPaket() {
        String no = eded.getText().toString();
        

        /*layMain.setVisibility(View.VISIBLE);
        layDetail.setVisibility(View.GONE);*/
    }
}
