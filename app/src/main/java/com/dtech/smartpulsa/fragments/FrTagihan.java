package com.dtech.smartpulsa.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.firedatabase.DatabaseCek;
import com.dtech.smartpulsa.preference.PrefManager;

import static android.content.Context.MODE_PRIVATE;

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

public class FrTagihan extends Fragment implements View.OnClickListener {

    EditText edNmrTagihan;
    TextView txtTagihan;
    Button btnCek, btnPay;
    ProgressBar prgBar;

    View view;
    PrefManager prefManager;

    String userId;
    String email;
    String name;
    String trx;
    String tagihanTampil;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    FirebaseAuth firebaseAuth;
    FirebaseUser userF;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_tagihan, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        name = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");

        initUI();

        firebaseAuth = FirebaseAuth.getInstance();
        userF = firebaseAuth.getCurrentUser();
        userId = userF.getUid();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("Cek Tagihan");

        /*mFirebaseInstance.getReference("trx").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String transaksi = dataSnapshot.getValue(String.class);
                txtTagihan.setText("anda akan melakukan cek tagihan dengan nomor tagihan: \n"+transaksi);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return view;
    }

    private void initUI() {
        edNmrTagihan = (EditText) view.findViewById(R.id.edNmrTagihan);
        txtTagihan = (TextView) view.findViewById(R.id.txtTagihan);
        btnCek = (Button) view.findViewById(R.id.btnCek);
        btnPay = (Button) view.findViewById(R.id.btnPay);
        prgBar = (ProgressBar) view.findViewById(R.id.prgBar);
        prgBar.setVisibility(View.INVISIBLE);

        btnPay.setOnClickListener(this);
        btnCek.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCek:
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
        }
    }

    private void updateUser() {
        /*if (!TextUtils.isEmpty(trx)) {
            mFirebaseDatabase.child(userId).child("trx").setValue(trx);
        }*/
    }

    private void bayarTagihan() {

    }

    private void cekTagihan() {


        prgBar.setVisibility(View.VISIBLE);
        String tagihan = "";
       /* if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }*/


        DatabaseCek databaseCek = new DatabaseCek(name, email, trx, tagihan);
        mFirebaseDatabase.child(userF.getUid()).setValue(databaseCek);

        //postCekTagihan();

        addDbaseChangeListener();

    }



    private void addDbaseChangeListener() {
        mFirebaseDatabase.child(userF.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseCek databaseCek = dataSnapshot.getValue(DatabaseCek.class);
                if (databaseCek == null) {
                    Log.e("DatabaseCek", "User data is null");
                    return;
                }
                Log.e("Database Cek", "User data is changed: " + databaseCek.tagihan);
                txtTagihan.setText(databaseCek.tagihan);
                if (txtTagihan.getText() == "") {
                    prgBar.setVisibility(View.VISIBLE);
                } else {
                    prgBar.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Cek", "Failed Update User Data");
            }
        });

        //tagihanData();
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

                /*if (tagihanTampil == "" || tagihanTampil == null) {

                    showTagihan(s);
                } else {
                    tagihanFix = tagihanTampil;
                }*/

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