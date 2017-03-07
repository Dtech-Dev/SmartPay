package com.dtech.smartpulsa;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.data.DataPaket;
import com.dtech.smartpulsa.preference.PrefManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Volley";
    Button bhistTr, bhistTagih;
    WebView webHistTr, webhistTagih;

    PrefManager prefManager;
    SharedPreferences sharedPreferences;
    String idUsr;

    PieChart mChart;
    //private int[] yData ;
    private String[] xData = { "Pulsa", "Token", "Tagihan", "Voucher"};

    /*json variables*/
    String[] dateP, kodeP, hargaP, nomorP, dateTo, kodeTo, hargaTo, nomorTo, dateTa, jnsTa, jmlTa, nomorTa,
            dateVo, kodeVo, hargaVo, nomorVo;
    String lastdatesaldo, lastsaldo, currentsaldo, totaltrx, totalpulsa, totaltoken, totaltagihan, totalvoucher,
            jmltrx, jmlpulsa, jmltoken, jmltagihan, jmlvoucher;
    TextView tlastdatesaldo, tcurrentsaldo, ttotaltransaksi, totalspend, ttotaltrxpulsa, ttotaltrxtoken,
            ttotaltrxtagihan, ttotaltrxvoucher, tdetailpulsa, tdetailtoken, tdetailtagihan, tdetailvoucher;
    RelativeLayout reldetailpulsa, reldetailtoken, reldetailtagihan, reldetailvoucher;
    //String[] kodeP;

    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUi();
        getDataHist();
    }

    private void getDataHist() {
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url = Config.URL_HIST_TRX_NEW + idUsr;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this,error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        List<DataPaket> kodepulsa = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);
            lastdatesaldo = data.getString(Config.HIST_DATA_LASTDATESALDO);
            currentsaldo = data.getString(Config.HIST_DATA_CURRENTSALDO);
            totaltrx = data.getString(Config.HIST_DATA_TOTALTRX);
            totalpulsa = data.getString(Config.HIST_DATA_TOTALPULSA);
            totaltoken = data.getString(Config.HIST_DATA_TOTALTOKEN);
            totaltagihan = data.getString(Config.HIST_DATA_TOTALTAGIHAN);
            totalvoucher = data.getString(Config.HIST_DATA_TOTALVOUCHER);

            tlastdatesaldo.setText(lastdatesaldo);
            tcurrentsaldo.setText(currentsaldo);
            //ttotaltransaksi.setText(totaltrx);
            totalspend.setText(totaltrx);
            tdetailpulsa.setText(totalpulsa);
            tdetailtagihan.setText(totaltagihan);
            tdetailtoken.setText(totaltoken);
            tdetailvoucher.setText(totalvoucher);
            /*================*/
            JSONArray detail = data.getJSONArray(Config.ARRAY_HIST_DETAIL);
            JSONObject detaildata = detail.getJSONObject(0);

            /*jsaon array Pulsa*/
            JSONArray pulsa = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_PULSA);
            JSONArray token = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_TOKEN);
            JSONArray tagihan = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_TAGIHAN);
            JSONArray voucher = detaildata.getJSONArray(Config.ARRAY_HIST_DETAIL_VOUCHER);
            jmlpulsa = (pulsa.length()==0)?"0":Integer.toString(pulsa.length());
            ttotaltrxpulsa.setText("("+jmlpulsa+" transaksi)");
            jmltoken = (token.length()==0)?"0": Integer.toString(token.length());
            ttotaltrxtoken.setText("("+jmltoken+" transaksi)");
            jmltagihan = (tagihan.length()==0)?"0":Integer.toString(tagihan.length());
            ttotaltrxtagihan.setText("("+jmltagihan+" transksi)");
            jmlvoucher = (voucher.length()==0)?"0": Integer.toString(voucher.length());
            ttotaltrxvoucher.setText("("+jmlvoucher+" transaksi)");

            float[] yData = {Float.parseFloat(jmlpulsa), Float.parseFloat(jmltoken), Float.parseFloat(jmltagihan), Float.parseFloat(jmlvoucher)};
            ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

            List<PieEntry> entries = new ArrayList<>();

            for (int i=0;i<yData.length;i++) {
                entries.add(new PieEntry(yData[i], xData[i]));
            }


            PieDataSet set = new PieDataSet(entries, "");
            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.MATERIAL_COLORS)
                colors.add(c);

            /*for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);*/


            //colors.add(ColorTemplate.getHoloBlue());
            set.setColors(colors);
            PieData datax = new PieData(set);
            mChart.setData(datax);
            mChart.invalidate();
            //jmlpulsa.contains("0") ? "no history" : jmlpulsa;
            /*if (jmlpulsa.contains("0")) {
                ttotaltrxpulsa.setText("(tidak ada histori transaki");
            } else {
                ttotaltrxpulsa.setText("("+jmlpulsa+")");
            }*/
            jmltrx = Integer.toString(pulsa.length() + token.length() + tagihan.length() + voucher.length());
            ttotaltransaksi.setText(jmltrx);

            dateP = new String[pulsa.length()];
            kodeP = new String[pulsa.length()];
            hargaP = new String[pulsa.length()];
            nomorP = new String[pulsa.length()];
            dateTo = new String[token.length()];
            kodeTo = new String[token.length()];
            hargaTo = new String[token.length()];
            nomorTo = new String[token.length()];
            dateTa = new String[tagihan.length()];
            jnsTa = new String[tagihan.length()];
            jmlTa = new String[tagihan.length()];
            nomorTa = new String[tagihan.length()];
            for (int i = 0; i<pulsa.length(); i++) {
                JSONObject datapulsa = pulsa.getJSONObject(i);
                //DataPaket dataPaket = new DataPaket();
                //dataPaket.kode = datapulsa.getString("kode");
                //JSONObject jo = datapulsa.getJSONObject();
                dateP[i] = datapulsa.getString("date");
                kodeP[i] = datapulsa.getString("kode");
                hargaP[i] = datapulsa.getString("harga");
                nomorP[i] = datapulsa.getString("nomor_tujuan");
                //kodepulsa.add(dataPaket);
            }
            /*jsaon array token*/



            for (int j = 0; j < token.length(); j++) {
                JSONObject datatoken = token.getJSONObject(j);
                //DataPaket dataPaket = new DataPaket();
                //dataPaket.kode = datapulsa.getString("kode");
                //JSONObject jo = datapulsa.getJSONObject();
                dateTo[j] = datatoken.getString("date");
                kodeTo[j] = datatoken.getString("kode");
                hargaTo[j] = datatoken.getString("harga");
                nomorTo[j] = datatoken.getString("nomor_tujuan");
                //kodepulsa.add(dataPaket);
            }
            /*jsaon array tagihan*/



            for (int k = 0; k < tagihan.length(); k++) {
                JSONObject datatagih = tagihan.getJSONObject(k);
                //DataPaket dataPaket = new DataPaket();
                //dataPaket.kode = datapulsa.getString("kode");
                //JSONObject jo = datapulsa.getJSONObject();
                /*dateTa[k] = datatagih.getString("date");
                kodeTa[k] = datatagih.getString("kode");
                hargaTa[k] = datatagih.getString("harga");
                nomorTo[k] = datatagih.getString("nomor_tujuan");*/
                //kodepulsa.add(dataPaket);
            }
            /*jsaon array tagihan*/


            //Log.d(TAG, "showJSON: "+kodepulsa);
            /*StringBuilder builder = new StringBuilder();
            for (int a =0 ;a < kodeTo.length; a++) {
                builder.append(kodeTo[a] + "\n");
            }
            Toast.makeText(HistoryActivity.this, builder+"\n"+kodeTo.length, Toast.LENGTH_LONG).show();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initUi() {

        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        idUsr = (sharedPreferences.getString(Config.DISPLAY_IDUSR, ""));
        bhistTr = (Button) findViewById(R.id.bhistTr);
        bhistTagih = (Button) findViewById(R.id.bhistTagih);
        //webHistTr = (WebView) findViewById(R.id.webHistTr);
        //webhistTagih = (WebView) findViewById(R.id.webHistTagih);
        //webhistTagih.loadUrl("http://samimi.web.id/dev/hist-tagih.php?id="+idUsr);
        //webHistTr.loadUrl("http://samimi.web.id/dev/hist-tr.php?id="+idUsr);

        bhistTr.setOnClickListener(this);
        bhistTagih.setOnClickListener(this);
        tlastdatesaldo = (TextView) findViewById(R.id.lastdatesaldo);
        tcurrentsaldo = (TextView) findViewById(R.id.currentsaldo);
        ttotaltransaksi = (TextView) findViewById(R.id.totalTransaksi);
        totalspend = (TextView) findViewById(R.id.totalSpend);
        tlastdatesaldo = (TextView) findViewById(R.id.lastdatesaldo);
        ttotaltrxpulsa = (TextView) findViewById(R.id.tTotaltrxPulsa);
        ttotaltrxtagihan = (TextView) findViewById(R.id.tTotaltrxTagihan);
        ttotaltrxtoken = (TextView) findViewById(R.id.tTotaltrxToken);
        ttotaltrxvoucher = (TextView) findViewById(R.id.tTotaltrxVoucher);
        tdetailpulsa = (TextView) findViewById(R.id.tdetailpulsa);
        tdetailtoken = (TextView) findViewById(R.id.tdetailtoken);
        tdetailtagihan = (TextView) findViewById(R.id.tdetailtagihan);
        tdetailvoucher = (TextView) findViewById(R.id.tdetailvoucher);

        reldetailpulsa = (RelativeLayout) findViewById(R.id.reldetailPulsa);
        reldetailtoken = (RelativeLayout) findViewById(R.id.reldetailtoken);
        reldetailtagihan = (RelativeLayout) findViewById(R.id.reldetailtagihan);
        reldetailvoucher = (RelativeLayout) findViewById(R.id.reldetailvoucher);
        mChart = (PieChart) findViewById(R.id.piechart);
        //mChart.setDescription(new Description().setText("Analisa Transaksi"));

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        //mChart.setUsePercentValues(true);
        //mChart.setHoleColorTransparent(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bhistTr:
                showHistTr();
                break;
            case R.id.bhistTagih:
                showHistTagih();
                break;
        }

    }

    private void showHistTagih() {
        webHistTr.setVisibility(View.GONE);
        webhistTagih.setVisibility(View.VISIBLE);

    }

    private void showHistTr() {
        webHistTr.setVisibility(View.VISIBLE);
        webhistTagih.setVisibility(View.GONE);

    }
}
