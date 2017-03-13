package com.dtech.smartpulsa.feature;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridVoucher;
import com.dtech.smartpulsa.data.AdapterDetailHist;
import com.dtech.smartpulsa.data.AdapterPaket;
import com.dtech.smartpulsa.data.DataPaket;
import com.dtech.smartpulsa.data.DataPul;
import com.dtech.smartpulsa.data.DataTa;
import com.dtech.smartpulsa.data.DataTo;
import com.dtech.smartpulsa.data.DataVo;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

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
    RecyclerView recyclerHist;
    AdapterDetailHist madapter;
    RadioButton fall, f3, f7, f30;
    RadioGroup rfilter;
    String jenis;

    List<DataPul> datadetail = new ArrayList<>();

    JSONArray pulsa, token, tagihan, voucher;

    private String json_string;

    List<DataPul> datapulsa;
    private TextView titlehist;
    EditText eded;
    Button bb;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jenis = getIntent().getStringExtra("jenis");
        response = getIntent().getStringExtra("response");
        prepareJson();
        initUi();
        if (jenis.matches("pulsa")) {
            setDetailPulsa();
        } else if (jenis.matches("token")) {
            setDetailToken();
        }
        //Toast.makeText(this, response, Toast.LENGTH_LONG).show();

    }

    private void prepareJson() {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);
            JSONArray detail = data.getJSONArray(Config.ARRAY_HIST_DETAIL);
            JSONObject detaildata = detail.getJSONObject(0);

            /*jsaon array Pulsa*/
            pulsa = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_PULSA);
            token = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_TOKEN);
            tagihan = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_TAGIHAN);
            voucher = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_VOUCHER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDetailToken() {

    }

    private void setDetailPulsa() {
        for (int i = 0; i<pulsa.length(); i++) {
            JSONObject jopulsa = null;
            try {
                jopulsa = pulsa.getJSONObject(i);
                DataPul datapulsa = new DataPul();
                datapulsa.dateP = jopulsa.getString("date");
                datapulsa.kodeP = jopulsa.getString("kode");
                datapulsa.hargaP = jopulsa.getString("harga");
                datapulsa.nomorP = jopulsa.getString("nomor_tujuan");
                datadetail.add(datapulsa);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("array Pulsa", String.valueOf(datadetail));
        }
        madapter = new AdapterDetailHist(DetailHistActivity.this, datadetail);
        recyclerHist.setAdapter(madapter);
        recyclerHist.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initUi() {
        titlehist = (TextView) findViewById(R.id.titlehist);
        eded = (EditText) findViewById(R.id.eded);
        bb = (Button) findViewById(R.id.bb);
        bb.setOnClickListener(this);
        recyclerHist = (RecyclerView) findViewById(R.id.rechisto);

        fall = (RadioButton) findViewById(R.id.fall);
        f3 = (RadioButton) findViewById(R.id.f3);
        f30 = (RadioButton) findViewById(R.id.f30);
        f7 = (RadioButton) findViewById(R.id.f7);
        rfilter = (RadioGroup) findViewById(R.id.rfilter);
        rfilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.fall:
                        if (jenis.matches("pulsa")) {
                            setDetailPulsa();
                        } else if (jenis.matches("token")) {
                            setDetailToken();
                        } else if (jenis.matches("tagihan")) {
                            setDetailTagihan();
                        } else if (jenis.matches("voucher")) {
                            setDetailVoucher();
                        }
                        break;
                    case R.id.f3:
                        if (jenis.matches("pulsa")) {
                            setDetailPulsaThree();
                        } else if (jenis.matches("token")) {
                            setDetailTokenThree();
                        } else if (jenis.matches("tagihan")) {
                            setDetailTagihanThree();
                        } else if (jenis.matches("voucher")) {
                            setDetailVoucherThree();
                        }
                        break;
                    case R.id.f7:
                        if (jenis.matches("pulsa")) {
                            setDetailPulsaSeven();
                        } else if (jenis.matches("token")) {
                            setDetailTokenSeven();
                        } else if (jenis.matches("tagihan")) {
                            setDetailTagihanSeven();
                        } else if (jenis.matches("voucher")) {
                            setDetailVoucherSeven();
                        }
                        break;
                    case R.id.f30:
                        if (jenis.matches("pulsa")) {
                            setDetailPulsaThitry();
                        } else if (jenis.matches("token")) {
                            setDetailTokenThirty();
                        } else if (jenis.matches("tagihan")) {
                            setDetailTagihanThirty();
                        } else if (jenis.matches("voucher")) {
                            setDetailVoucherThirty();
                        }
                        break;
                }
            }
        });
    }

    private void setDetailTagihanThirty() {

    }

    private void setDetailVoucherThirty() {

    }

    private void setDetailTagihanSeven() {

    }

    private void setDetailVoucherSeven() {

    }

    private void setDetailTagihanThree() {

    }

    private void setDetailVoucherThree() {

    }

    private void setDetailTagihan() {
    }

    private void setDetailVoucher() {

    }

    private void setDetailPulsaThitry() {

    }

    private void setDetailTokenThirty() {

    }

    private void setDetailTokenSeven() {

    }

    private void setDetailPulsaSeven() {

    }

    private void setDetailTokenThree() {

    }

    private void setDetailPulsaThree() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bb:
                String no = eded.getText().toString();
                backToMainPaket(no);

        }
    }

    private void backToMainPaket(String no) {

        try {
            // phone must begin with '+'
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(no, "");
            int countryCode = numberProto.getCountryCode();
            long nationalNumber = numberProto.getNationalNumber();
            Log.i("code", "code " + countryCode);
            Log.i("code", "national number " + nationalNumber);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        /*layMain.setVisibility(View.VISIBLE);
        layDetail.setVisibility(View.GONE);*/
    }
}
