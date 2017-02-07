package com.dtech.smartpulsa.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.ItemClickListener;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridTagihan;
import com.dtech.smartpulsa.custom.CustomGridVoucher;
import com.dtech.smartpulsa.data.AdapterKota;
import com.dtech.smartpulsa.data.KotaAdapter;
import com.dtech.smartpulsa.firedatabase.DatabaseCek;
import com.dtech.smartpulsa.preference.PrefManager;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.USER_SERVICE;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by aris on 10/12/16.
 */

public class FrTagihan extends Fragment implements View.OnClickListener, ItemClickListener {

    EditText edNmrTagihan;
    public TextView txtTagihan, txtjnsTagihan;
    Button btnCek, btnPay, btnMain;
    ProgressBar prgBar;
    public RelativeLayout laymainTagihan, laydetailTagihan;
    GridView gridView;

    View view;
    PrefManager prefManager;
    Dialog kotaDialog;

    String userId;
    String email;
    String name;
    String trx;
    String tagihanTampil;

    public String getJnsTagihan() {
        return jnsTagihan;
    }

    public void setJnsTagihan(String jnsTagihan) {
        this.jnsTagihan = jnsTagihan;
    }

    String jnsTagihan;

    AdapterKota mAdapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    FirebaseAuth firebaseAuth;
    FirebaseUser userF;

    ProgressDialog loading;

    public static String[] gridViewStrings = {
            "PLN",
            "PDAM",
            "Telkom",
            "Orange TV",
            "Indovision",
            "Aora TV",
            "FIF",
            "Astra Credit\nCompany",
            "WOM Finance"

    };

    public static String[] gridid = {
            "PLN",
            "PDAM",
            "Telkom",
            "orange",
            "Indovision",
            "Aora",
            "FIF",
            "acc",
            "wom"

    };

    public static int[] gridViewImages = {
            R.drawable.pln,
            R.drawable.pdam,
            R.drawable.telkom,
            R.drawable.orangetv,
            R.drawable.indovision,
            R.drawable.aora,
            R.drawable.fif,
            R.drawable.acc,
            R.drawable.wom
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_tagihan, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        name = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");
        userId = (sharedPreferences).getString(Config.DISPLAY_FIREBASE_ID, "");

        initUI();
        gridView.setAdapter(new CustomGridVoucher(getActivity(), gridViewStrings, gridViewImages, gridViewStrings, gridid));


        /*firebaseAuth = FirebaseAuth.getInstance();
        userF = firebaseAuth.getCurrentUser();*/
        //userId = userF.getUid();
        /*mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("Cek Tagihan");*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.txtid);
                String pilihan = textView.getText().toString();

                if (pilihan.contains("PDAM")) {
                    //jnsTagihan = pilihan + " kota";
                    pilihKota();
                } else {
                    setJnsTagihan(pilihan);
                    updateUi(pilihan);
                }


            }
        });

        return view;
    }

    /*public interface RecyclerViewClickListener{
        public void recyclerViewListClicked(View v, int position);
    }*/

    /*@Override
    public void recyclerViewListClicked(View v, int position){
        String kodeKota = KotaAdapter.kodeKota[position];
    }*/

    @Override
    public void onClick(View view, String data) {
        //String kodeKota = KotaAdapter.kodeKota[position];
        setJnsTagihan(data);
        updateUi(data);
        kotaDialog.dismiss();
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }


    private void pilihKota() {
        kotaDialog = new Dialog(getActivity());
        kotaDialog.setContentView(R.layout.dialog_kota);
        RecyclerView recyclerView = (RecyclerView) kotaDialog.findViewById(R.id.recKota);
        mAdapter = new AdapterKota(getActivity(), KotaAdapter.kodeKota, KotaAdapter.kota);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setOnClickListener(this);
        //recyclerView.setAdapter();
        kotaDialog.show();

        //recyclerView.addOnItemTouchListener(this);
    }



    public void updateUi(String jnsTagihan) {
        laymainTagihan.setVisibility(View.GONE);
        laydetailTagihan.setVisibility(View.VISIBLE);

        txtjnsTagihan.setText("Masukkan Id pelanggan "+jnsTagihan+" anda");
    }

    private void initUI() {
        laymainTagihan = (RelativeLayout) view.findViewById(R.id.layMainTagihan);
        laydetailTagihan = (RelativeLayout) view.findViewById(R.id.layDetailTagihan);
        gridView = (GridView) view.findViewById(R.id.gridtagihan);
        edNmrTagihan = (EditText) view.findViewById(R.id.edNmrTagihan);
        txtTagihan = (TextView) view.findViewById(R.id.txtTagihan);
        txtjnsTagihan = (TextView) view.findViewById(R.id.txtjenisTagihan);
        btnCek = (Button) view.findViewById(R.id.btnCek);
        btnPay = (Button) view.findViewById(R.id.btnPay);
        btnMain = (Button) view.findViewById(R.id.btnMainTagihan);
        prgBar = (ProgressBar) view.findViewById(R.id.prgBar);
        prgBar.setVisibility(View.INVISIBLE);
        /*if (txtTagihan.getText() == "") {
                    prgBar.setVisibility(View.VISIBLE);
                } else {
                    prgBar.setVisibility(View.INVISIBLE);
                }*/

        btnPay.setOnClickListener(this);
        btnCek.setOnClickListener(this);
        btnMain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCek:
                //prgBar.setVisibility(View.VISIBLE);
                trx = edNmrTagihan.getText().toString();
                //if (TextUtils.isEmpty(userId)) {
                    cekTagihan();
                //} else {
                //    updateUser();
                //}
                break;
            case R.id.btnPay:
                bayarTagihan();
                break;
            case R.id.btnMainTagihan:
                backMenuTagihan();
                break;
        }
    }

    private void backMenuTagihan() {
        laydetailTagihan.setVisibility(View.GONE);
        laymainTagihan.setVisibility(View.VISIBLE);
    }

    private void bayarTagihan() {

    }

    private void cekTagihan() {

        postCekTagihan();
        txtTagihan.setText("Transaksi sedang di proses\nAnda akan mendapat pemberitahuan jumlah tagihan tersebut");


    }

    private void postCekTagihan() {

        final String formatTrx = jnsTagihan+" "+trx+" cek 3003";
        class InsCekTagihan extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> paramsCekTagihan = new HashMap<>();
                //paramsCekTagihan.put(Config.FBASE_UID, userId);
                paramsCekTagihan.put(Config.NO_TAGIHAN, trx);
                paramsCekTagihan.put(Config.FBASE_UID, userId);
                paramsCekTagihan.put(Config.JENIS, jnsTagihan);
                paramsCekTagihan.put(Config.TAG_EMAIL_USER, email);
                paramsCekTagihan.put(Config.FORMAT, formatTrx);

                RequestHandler reqHandler = new RequestHandler();
                String res = reqHandler.sendPostRequest(Config.URL_INSERT_TAGIHAN, paramsCekTagihan);



                return res;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getActivity(), "Processing...", "Wait....", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(getActivity(), "We'll Processing Your Request", Toast.LENGTH_SHORT).show();
            }
        }

        /*InsCekTagihan cekTagihan = new InsCekTagihan();
        cekTagihan.execute();*/
        Log.d("formatTrx : ", formatTrx);
    }

    public void tagihanData() {
        class TampilTagihan extends AsyncTask<Void, Void, String> {
            String tagihanFix;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                txtTagihan.setText(tagihanFix);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_TAGIHAN, trx);
                showTagihan(s);

                return s;
            }
        }
        TampilTagihan tampilTagihan = new TampilTagihan();
        tampilTagihan.execute();
    }

    public void showTagihan(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            tagihanTampil = c.getString(Config.JML_TAGIHAN);


            //txtTagihan.setText(tagihan);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
