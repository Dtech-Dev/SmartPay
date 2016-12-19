package com.dtech.smartpulsa.fragments;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.PredictNumber;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.feature.PulsaActivity;
import com.dtech.smartpulsa.feature.Transaksi;
import com.dtech.smartpulsa.preference.PrefManager;

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

public class FrSingleNumber extends Fragment implements View.OnClickListener, TextWatcher {

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
    Spinner spinnerKode;
    Button bTransac;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fr_single_number, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        firebaseId = (sharedPreferences.getString(Config.DISPLAY_FIREBASE_ID, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");

        initUI();
        return view;
    }

    private void initUI() {
        totherNumber = (TextView) view.findViewById(R.id.txtOtherNumber);
        edOtherNumber = (EditText) view.findViewById(R.id.editOtherNumber);
        bTransac = (Button) view.findViewById(R.id.bTransac);
        bTransac.setOnClickListener(this);
        spinnerKode = (Spinner) view.findViewById(R.id.spinnerKode);
        spinnerKode.setPrompt("Nominal");
        edOtherNumber.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTransac:
                prosesTransaksi();
                break;

        }
    }

    private void prosesTransaksi() {
        String nomorTuj = edOtherNumber.getText().toString();
        //PredictNumber ambilNomorTujuan = new PredictNumber(getActivity());
        //String nomorTuj = predictNumber.getNomorTujuan();
        String transaksi = trProvider + trNominal;
        formatTrx = transaksi+"."+nomorTuj+".3003";
        Toast.makeText(getActivity(), formatTrx,Toast.LENGTH_SHORT).show();
        /*transaksiPulsa = new Transaksi(this.getActivity());
        transaksiPulsa.setUser(email);
        transaksiPulsa.setNomorTuj(nomorTuj);
        transaksiPulsa.setJenisTransaksi(transaksi);
        transaksiPulsa.setFirebaseId(firebaseId);
        transaksiPulsa.setKode(formatTrx);
        transaksiPulsa.execute();*/
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
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
            totherNumber.setText("Provider : "+provider+" ("+trProvider+")");
            queryKodeProvider(kodeProvider);
        }
    }



    @Override
    public void afterTextChanged(Editable s) {

    }

    private void queryKodeProvider(final String providerCode) {
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
                JSONObject jsonObject;
                //ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                List<String> list = new ArrayList<>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");
                    for (int i=0; i<result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        nominal = jo.getString(Config.TAG_NOMINAL);
                        list.add(nominal+".000");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(PulsaActivity.this, "Nomor tidak dikenali", Toast.LENGTH_SHORT).show();

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
                }
                //ListAdapter adapter = new SimpleAdapter(PulsaActivity.this, list, android.R.layout.simple_spinner_item, null, null);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKode.setAdapter(adapter);
            }
        }

        QueryKodeAsync queryKode = new QueryKodeAsync();
        queryKode.execute();

        spinnerKode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String nominalTemp = spinnerKode.getSelectedItem().toString();
                String kodeTnsk = nominalTemp.replace(".000", "");
                setTrNominal(kodeTnsk);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}
