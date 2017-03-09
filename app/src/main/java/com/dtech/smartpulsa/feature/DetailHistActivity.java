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
    private String json_string;

    List<DataPul> datapulsa;
    private TextView titlehist;

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

    /*private void updateUi(final String idItem, String tag, String jenisPaket) {
        layMain.setVisibility(View.GONE);
        layDetail.setVisibility(View.VISIBLE);

        //Toast.makeText(this, idItem + " " + jenisVoucher, Toast.LENGTH_SHORT).show();
        int resource = getResources().getIdentifier(tag, "mipmap", getPackageName());
        imgjnspaket.setImageDrawable(getResources().getDrawable(resource));
        txtjnspaket.setText(jenisPaket);

        class DetailPaket extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailHistActivity.this, "Loading...", "Please wait", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                json_string = s;
                showHargaPaket();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> paramvocher = new HashMap<>();
                paramvocher.put(Config.TAG_POST_KETERANGAN, idItem);



                RequestHandler reqHandler = new RequestHandler();
                String res = reqHandler.sendPostRequest(Config.URL_VOUCHER_GAME, paramvocher);

                return res;
            }
        }

        DetailPaket detailPaket = new DetailPaket();
        detailPaket.execute();
    }*/

    /*private void showHargaPaket() {

        JSONObject jsonObject = null;
        List<DataPaket> data = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json_string);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                DataPaket dataPaket = new DataPaket();
                dataPaket.kode = jo.getString(Config.TAG_KODE_ITEM);
                dataPaket.harga = jo.getString(Config.TAG_HARGA_ITEM);
                dataPaket.keterangan = jo.getString(Config.TAG_KETERANGAN_ITEM);
                data.add(dataPaket);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        madapter = new AdapterPaket(DetailHistActivity.this, data);
        recyclerPaket.setAdapter(madapter);
        recyclerPaket.setLayoutManager(new LinearLayoutManager(DetailHistActivity.this));
    }*/

    private void initUi() {
        titlehist = (TextView) findViewById(R.id.titlehist);
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.btnMainPaket:
                backToMainPaket();
        }*/
    }

    private void backToMainPaket() {

        /*layMain.setVisibility(View.VISIBLE);
        layDetail.setVisibility(View.GONE);*/
    }
}
