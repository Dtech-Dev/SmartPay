package com.dtech.smartpulsa.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomDetailVgame;
import com.dtech.smartpulsa.custom.CustomGridVoucher;
import com.dtech.smartpulsa.feature.VoucherActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aris on 01/01/17.
 */

public class FrVgame extends Fragment implements View.OnClickListener {

    View view;

    GridView gridVoucher, gridVoucherDetail;
    RelativeLayout layoutVoucher, layoutDetailVoucher;
    ImageView imgJenisVoucher;
    TextView txtjnsvoucher;
    Button mainMenuVoucher;

    public static String[] gridStringsVoucher = {
            "Garena",
            "Gemscool",
            "Lyto",
            "Megaxus",
            "Steam Wallet"

    };

    public static String[] gridImageTag = {
            "ic_launcher",
            "ic_launcher",
            "ic_launcher",
            "ic_launcher",
            "ic_launcher"
    };

    public static String[] grididVoucher = {
            "11",
            "12",
            "13",
            "14",
            "15"
    };

    public static int[] gridImageVouchers = {
            R.drawable.garena,
            R.drawable.gemscool,
            R.drawable.lyto,
            R.drawable.megasus,
            R.mipmap.ic_launcher

    };

    String json_string;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_voucher, container, false);


        initUi();

        gridVoucher.setAdapter(new CustomGridVoucher(getActivity(), gridStringsVoucher, gridImageVouchers, gridImageTag, grididVoucher));

        gridVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        return view;
    }

    private void updateUi(final String jenisVoucher, String tag, String keterangan) {
        layoutVoucher.setVisibility(View.GONE);
        layoutDetailVoucher.setVisibility(View.VISIBLE);

        int resource = getResources().getIdentifier(tag, "mipmap", getActivity().getPackageName());
        imgJenisVoucher.setImageDrawable(getResources().getDrawable(resource));
        txtjnsvoucher.setText(keterangan);

        class FDetailVoucher extends AsyncTask<Void, Void, String> {

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
                showHargaVoucher();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> paramvocher = new HashMap<>();
                paramvocher.put(Config.TAG_POST_KETERANGAN, jenisVoucher);



                RequestHandler reqHandler = new RequestHandler();
                String res = reqHandler.sendPostRequest(Config.URL_VOUCHER_GAME, paramvocher);

                return res;
            }
        }

        FDetailVoucher detailVoucher = new FDetailVoucher();
        detailVoucher.execute();

    }

    private void showHargaVoucher() {
        JSONObject jsonObject = null;
        ArrayList<String> listharga = new ArrayList<String>();
        ArrayList<String> listkode = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(json_string);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kode = jo.getString(Config.TAG_KODE_ITEM);
                String harga = jo.getString(Config.TAG_HARGA_ITEM);



                listkode.add(kode);
                listharga.add(harga);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] kodevgame = listkode.toArray(new String[listkode.size()]);
        String[] hargavgame = listharga.toArray(new String[listharga.size()]);

        gridVoucherDetail.setAdapter(new CustomDetailVgame(getActivity(), kodevgame, hargavgame));
        gridVoucherDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void initUi() {
        gridVoucher = (GridView) view.findViewById(R.id.gridvoucher);
        gridVoucherDetail = (GridView) view.findViewById(R.id.gridvoucherdetail);
        txtjnsvoucher = (TextView) view.findViewById(R.id.txtJnsVoucher);
        imgJenisVoucher = (ImageView) view.findViewById(R.id.imageJnsVoucher);
        layoutVoucher = (RelativeLayout) view.findViewById(R.id.layVoucher);
        layoutDetailVoucher = (RelativeLayout) view.findViewById(R.id.layVoucherDetail);
        mainMenuVoucher = (Button) view.findViewById(R.id.btnMainVoucher);
        mainMenuVoucher.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMainVoucher:
                showMainMenuVoucher();
        }
    }

    private void showMainMenuVoucher() {
        layoutDetailVoucher.setVisibility(View.GONE);
        layoutVoucher.setVisibility(View.VISIBLE);
    }
}
