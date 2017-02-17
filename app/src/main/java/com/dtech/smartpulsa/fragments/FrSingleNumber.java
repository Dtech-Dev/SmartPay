package com.dtech.smartpulsa.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.PredictNumber;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.TrfConfirmActivity;
import com.dtech.smartpulsa.custom.CustomGridToken;
import com.dtech.smartpulsa.feature.PulsaActivity;
import com.dtech.smartpulsa.feature.Transaksi;
import com.dtech.smartpulsa.preference.PrefManager;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//import static com.dtech.smartpulsa.R.id.bTransac;

/**
 * Created by aris on 30/11/16.
 */

public class FrSingleNumber extends Fragment implements TextWatcher, AdapterView.OnItemClickListener {

    PredictNumber predictNumber = new PredictNumber(getActivity());
    PrefManager prefManager;
    Transaksi transaksiPulsa;

    String trProvider;
    String trNominal;
    String transaksiKode;

    String kodeProvider, provider;
    String formatTrx;
    String nominal;
    String firebaseId;
    String email;

    View view;
    TextView totherNumber;
    EditText edOtherNumber;
    ImageButton imgPin;

    String harga;
    GridView gridView;
     String json_string;
    InputMethodManager imm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fr_single_number, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        firebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");

         imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initUI();
        return view;
    }

    private void initUI() {
        gridView = (GridView) view.findViewById(R.id.gridPulsa);
        gridView.setVisibility(View.INVISIBLE);
        totherNumber = (TextView) view.findViewById(R.id.txtOtherNumber);
        edOtherNumber = (EditText) view.findViewById(R.id.editOtherNumber);
        imgPin = (ImageButton) view.findViewById(R.id.imgPin);
        /*imgPin.setImageDrawable(GoogleMaterial.Icon.gmd_search);*/
        imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edOtherNumber.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Isikan nomor anda terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    /*getActivity().getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.HI
                    );*/
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    queryKodeProvider(kodeProvider);
                }

            }
        });

        edOtherNumber.addTextChangedListener(this);
    }


    private void prosesTransaksi() {

        JSONObject jsonObject;
        ArrayList<String> listharga = new ArrayList<String>();
        ArrayList<String> listkode = new ArrayList<String>();
        //ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        List<String> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json_string);
            JSONArray result = jsonObject.getJSONArray("result");
            for (int i=0; i<result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                nominal = jo.getString(Config.TAG_NOMINAL);
                harga = jo.getString(Config.TAG_HARGA_PROV);
                listkode.add(nominal+".000");
                listharga.add("Harga: "+harga);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Toast.makeText(PulsaActivity.this, "Nomor tidak dikenali", Toast.LENGTH_SHORT).show();

            /*f*/
        }
        //ListAdapter adapter = new SimpleAdapter(PulsaActivity.this, list, android.R.layout.simple_spinner_item, null, null);
        try {
            String[] kodetoken = listkode.toArray(new String[listkode.size()]);
            String[] hargaToken = listharga.toArray(new String[listharga.size()]);

            gridView.setAdapter(new CustomGridToken(hargaToken, kodetoken, getActivity()));
            gridView.setOnItemClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        gridView.setVisibility(View.INVISIBLE);
        gridView.setAdapter(null);
        if (s.length() >= 6) {

            /*String prepredict = s.toString();
            if (prepredict.contains("-")) {
                prepredict.replaceAll("-", "");
                edOtherNumber.setText(prepredict);
            }*/
            //String numberToRead = s.toString().substring(0, 6);
            //String numberToPredict = numberToRead
            predictNumber.readProvider(s.toString());
            //predictNumber.readNumber(numberToRead);
            provider = predictNumber.getTypeNumber();
            kodeProvider = predictNumber.getKodeTransaksi();
            setTrProvider(predictNumber.getKodeTransaksi());

            if (provider.equals("Unknown")) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_provider);
                dialog.setCancelable(false);
                dialog.setTitle("Oopss");
                TextView txtError = (TextView) dialog.findViewById(R.id.textProviderNull);
                txtError.setText("Nomor Tidak Dikenali");

                Button btnError = (Button) dialog.findViewById(R.id.btnProviderNull);
                btnError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        edOtherNumber.setText("");
                    }
                });

                dialog.show();
                gridView.setVisibility(View.INVISIBLE);
                totherNumber.setText("Provider : ");
            } else {
                totherNumber.setText("Provider : "+provider+" ("+trProvider+")");
            }

            if (s.length() >= 10) {
                imgPin.setVisibility(View.VISIBLE);
            } else {
                imgPin.setVisibility(View.INVISIBLE);
            }

        }
    }



    @Override
    public void afterTextChanged(Editable s) {

    }

    private void queryKodeProvider(final String providerCode) {

        gridView.setVisibility(View.VISIBLE);
        class QueryKodeAsync extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> paramsProvider = new HashMap<>();
                paramsProvider.put(Config.TAG_PROVIDER, providerCode);



                RequestHandler reqHandler = new RequestHandler();
                String res = reqHandler.sendPostRequest(Config.URL_QUERY_KODE, paramsProvider);

                return res;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                json_string = s;
                prosesTransaksi();
            }

        }


        QueryKodeAsync queryKode = new QueryKodeAsync();
        queryKode.execute();

    }

    public String getTrProvider() {
        return trProvider;
    }

    public void setTrProvider(String trProvider) {
        this.trProvider = trProvider;
    }

    public String getTrNominal() {
        return trNominal;
    }

    public void setTrNominal(String trNominal) {
        this.trNominal = trNominal;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tvkode = (TextView) view.findViewById(R.id.txtkodeToken);

        String nominalTemp = tvkode.getText().toString();
        String kodeTnsk = nominalTemp.replace(".000", "");
        setTrNominal(kodeTnsk);

        String nomorTuj = edOtherNumber.getText().toString();
        if (nomorTuj.length() < 6 || nomorTuj.matches("")) {
            Toast.makeText(getActivity(), "Cek nomor tujuan anda", Toast.LENGTH_SHORT).show();
            edOtherNumber.requestFocus();
        }
        //PredictNumber ambilNomorTujuan = new PredictNumber(getActivity());
        //String nomorTuj = predictNumber.getNomorTujuan();
        String transaksi = trProvider + trNominal;
        formatTrx = transaksi+"."+nomorTuj+".3003";
        transaksiPulsa = new Transaksi(getActivity());
        transaksiPulsa.setUser(email);
        transaksiPulsa.setNomorTuj(nomorTuj);
        transaksiPulsa.setJenisTransaksi(transaksi);
        transaksiPulsa.setFirebaseId(firebaseId);
        transaksiPulsa.setKode(formatTrx);

        //Toast.makeText(getActivity(), formatTrx,Toast.LENGTH_SHORT).show();
        final AlertDialog alertDialog ;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Transaksi");
        builder.setMessage("Anda akan melakukan transaksi dengan detail :\nNomor Tujuan : "+nomorTuj+"\nProvider : "+provider+"\nNominal : "+nominalTemp);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transaksiPulsa.execute();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                edOtherNumber.setText("");
                gridView.setAdapter(null);
            }
        });



    }

    /*@Override
    public void onResume() {
        super.onResume();
        edOtherNumber.setText("");
        gridView.setVisibility(View.INVISIBLE);
    }*/
}
