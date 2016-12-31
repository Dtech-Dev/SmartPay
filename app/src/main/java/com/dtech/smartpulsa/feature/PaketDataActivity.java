package com.dtech.smartpulsa.feature;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridVoucher;

public class PaketDataActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridPaket, gridPaketDetail;
    RelativeLayout layMain, layDetail;
    ImageView imgjnspaket;
    TextView txtjnspaket;
    Button btnmainpaket;

    public static String[] gridStringPaket = {
            "paket data Xl",
            "paket data Indosat",
            "paket data Axis",
            "paket data Smartfren",
            "paket data Telkomsel",
            "paket data Tri"

    };

    public static String[] gridImageTagPaket = {
            "ic_launcher",
            "ic_launcher",
            "ic_launcher",
            "ic_launcher",
            "ic_launcher",
            "ic_launcher"
    };

    public static String[] grididpaket = {
            "16",
            "17",
            "18",
            "19",
            "20",
            "21"
    };

    public static int[] gridImagePaket = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        inutUi();

        gridPaket.setAdapter(new CustomGridVoucher(this, gridStringPaket, gridImagePaket, gridImageTagPaket, grididpaket));
        gridPaket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view.findViewById(R.id.gridvocher_image);
                String tag = (String) imageView.getTag();
                TextView textView = (TextView) view.findViewById(R.id.gridvoucher_text);
                String jenisVoucher = textView.getText().toString();
                TextView textView1 = (TextView) view.findViewById(R.id.txtid);
                String idItem = textView1.getText().toString();

                updateUi(idItem, tag, jenisVoucher);
            }
        });

    }

    private void updateUi(String idItem, String tag, String jenisVoucher) {
        layMain.setVisibility(View.GONE);
        layDetail.setVisibility(View.VISIBLE);

        Toast.makeText(this, idItem + " " + jenisVoucher, Toast.LENGTH_SHORT).show();
    }

    private void inutUi() {
        gridPaket = (GridView) findViewById(R.id.gridpaket);
        gridPaketDetail = (GridView) findViewById(R.id.gridpaketdetail);
        layMain = (RelativeLayout) findViewById(R.id.layMainPaket);
        layDetail = (RelativeLayout) findViewById(R.id.layDetailPaket);
        txtjnspaket = (TextView) findViewById(R.id.txtJnsPaket);
        imgjnspaket = (ImageView) findViewById(R.id.imageJnsPaket);
        btnmainpaket = (Button) findViewById(R.id.btnMainPaket);
        btnmainpaket.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainPaket:
                backToMainPaket();
        }
    }

    private void backToMainPaket() {

    }
}
