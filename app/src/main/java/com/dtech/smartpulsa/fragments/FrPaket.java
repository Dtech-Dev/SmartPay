package com.dtech.smartpulsa.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridVoucher;
import com.dtech.smartpulsa.data.AdapterPaket;
import com.dtech.smartpulsa.data.DataPaket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aris on 01/01/17.
 */

public class FrPaket extends Fragment implements View.OnClickListener {

    View view;

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
            "xl",
            "indosat",
            "axis",
            "smartfren",
            "telkomsel",
            "tri"
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
            R.drawable.xl,
            R.drawable.indosat,
            R.drawable.axis,
            R.drawable.smartfren,
            R.drawable.telkomsel,
            R.drawable.tri

    };
    private String json_string;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_paket_data, container, false);

        initUi();

        gridPaket.setAdapter(new CustomGridVoucher(getActivity(), gridStringPaket, gridImagePaket, gridImageTagPaket, grididpaket));
        gridPaket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view.findViewById(R.id.gridvocher_image);
                String tag = (String) imageView.getTag();
                TextView textView = (TextView) view.findViewById(R.id.gridvoucher_text);
                String jenisPaket = textView.getText().toString();
                TextView textView1 = (TextView) view.findViewById(R.id.txtid);
                String idItem = textView1.getText().toString();
                if (jenisPaket.matches("paket data Telkomsel")) {
                    final Dialog dialogStatus = new Dialog(getActivity());
                    dialogStatus.setTitle("Paket Data");
                    dialogStatus.setContentView(R.layout.custom_dialog_keterangan);
                    TextView tv = (TextView) dialogStatus.findViewById(R.id.msgDialogKet);
                    tv.setText("Untuk sementara Paket Data Telkomsel Tidak Tersedia");
                    Button btnadd = (Button) dialogStatus.findViewById(R.id.addBtn);
                    btnadd.setText("Close");
                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //context.startActivity(new Intent(context, AddSaldoActivity.class));
                            dialogStatus.dismiss();
                        }
                    });
                    dialogStatus.show();
                } else {
                    updateUi(idItem, tag, jenisPaket);
                }
            }
        });
        return view;
    }

    private void updateUi(final String idItem, String tag, String jenisPaket) {
        layMain.setVisibility(View.GONE);
        layDetail.setVisibility(View.VISIBLE);

        //Toast.makeText(this, idItem + " " + jenisVoucher, Toast.LENGTH_SHORT).show();
        int resource = getResources().getIdentifier(tag, "drawable", getActivity().getPackageName());
        imgjnspaket.setImageDrawable(getResources().getDrawable(resource));
        txtjnspaket.setText(jenisPaket);

        class FDetailPaket extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Loading...", "Please wait", false, false);
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

        FDetailPaket detailPaket = new FDetailPaket();
        detailPaket.execute();
    }

    private void showHargaPaket() {

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

        madapter = new AdapterPaket(getActivity(), data);
        recyclerPaket.setAdapter(madapter);
        recyclerPaket.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPaket.setVisibility(View.VISIBLE);
    }

    private void initUi() {
        gridPaket = (GridView) view.findViewById(R.id.gridpaket);
        //gridPaketDetail = (GridView) findViewById(R.id.gridpaketdetail);
        recyclerPaket = (RecyclerView) view.findViewById(R.id.recyclerpaket);
        layMain = (RelativeLayout) view.findViewById(R.id.layMainPaket);
        layDetail = (RelativeLayout) view.findViewById(R.id.layDetailPaket);
        txtjnspaket = (TextView) view.findViewById(R.id.txtJnsPaket);
        imgjnspaket = (ImageView) view.findViewById(R.id.imageJnsPaket);
        btnmainpaket = (Button) view.findViewById(R.id.btnMainPaket);
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

        layMain.setVisibility(View.VISIBLE);
        layDetail.setVisibility(View.GONE);
        recyclerPaket.setVisibility(View.INVISIBLE);
    }
}
