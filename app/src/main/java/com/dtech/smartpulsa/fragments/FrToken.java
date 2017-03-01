package com.dtech.smartpulsa.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridToken;
import com.dtech.smartpulsa.feature.Transaksi;
import com.dtech.smartpulsa.preference.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by aris on 01/01/17.
 */

public class FrToken extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {

    View view;
    Transaksi transaksi;
    PrefManager prefManager;
    String json_string, firebaseId, email;
    ImageButton imgDone;
    GridView gridToken;
    EditText edIdPelanggan;
    InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_token, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        firebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initUI();
        getHargaToken();
        return view;
    }

    private void getHargaToken() {
        class HargaToken extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_HARGA_TOKEN);
                return s;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                json_string = s;
                showHarga();
            }
        }

        HargaToken hargaToken= new HargaToken();
        hargaToken.execute();
    }

    private void showHarga() {
        JSONObject jsonObject = null;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> listkode = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(json_string);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String keterangan = jo.getString(Config.TAG_KETERANGAN_TOKEN);
                String harga = jo.getString(Config.TAG_HARGA_TOKEN);
                String kode = jo.getString(Config.TAG_KODE_TOKEN);
                String textBtnToken = keterangan + "\n harga: " + harga;


                listkode.add(kode);
                list.add(textBtnToken);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] kodetoken = listkode.toArray(new String[listkode.size()]);
        String[] hargaToken = list.toArray(new String[list.size()]);

        gridToken.setAdapter(new CustomGridToken(hargaToken, kodetoken, getActivity()));
        gridToken.setOnItemClickListener(this);
    }

    private void initUI() {
        gridToken = (GridView) view.findViewById(R.id.gridToken);
        edIdPelanggan = (EditText) view.findViewById(R.id.idPelangganToken);
        edIdPelanggan.addTextChangedListener(this);
        imgDone = (ImageButton) view.findViewById(R.id.imgPintoken);
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edIdPelanggan.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Isikan nomor anda terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    gridToken.setVisibility(View.VISIBLE);
                }
            }
        });
        //gridToken.setEnabled(false);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tvkode = (TextView) view.findViewById(R.id.txtkodeToken);
        /*Button btnProses = (Button)view.findViewById()*/
        String tkodetoken = tvkode.getText().toString();
        String formatTrx;
        String kodetoken = tkodetoken.substring(3);
        String idpel = edIdPelanggan.getText().toString().trim();
        /*if (!gridToken.isEnabled()) {

        }*/
        if (idpel.matches("")) {
            Toast.makeText(getActivity(), "Silahkan isi nomor pelangga anda", Toast.LENGTH_SHORT).show();
            edIdPelanggan.requestFocus();
        } else {
            formatTrx = "pln " + idpel + " " + kodetoken + " 3003";

            //Toast.makeText(getActivity(), formatTrx, Toast.LENGTH_SHORT).show();
        //}
            transaksi = new Transaksi(getActivity());
            transaksi.setUser(email);
            transaksi.setNomorTuj(idpel);
            transaksi.setJenisTransaksi(tkodetoken);
            transaksi.setFirebaseId(firebaseId);
            transaksi.setKode(formatTrx);
            transaksi.execute();

            imgDone.setVisibility(View.INVISIBLE);
            gridToken.setVisibility(View.INVISIBLE);
            edIdPelanggan.setText("");
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //gridToken.setEnabled(false);
        if (charSequence.length() >= 5) {
            imgDone.setVisibility(View.VISIBLE);
        } else {
            imgDone.setVisibility(View.INVISIBLE);
            gridToken.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //gridToken.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
