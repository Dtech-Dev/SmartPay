package com.dtech.smartpulsa.fragments;

import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.firedatabase.DatabaseCek;
import com.dtech.smartpulsa.preference.PrefManager;

import static android.content.Context.MODE_PRIVATE;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aris on 10/12/16.
 */

public class FrTagihan extends Fragment implements View.OnClickListener {

    EditText edNmrTagihan;
    TextView txtTagihan;
    Button btnCek, btnPay;

    View view;
    PrefManager prefManager;

    String userId;
    String email;
    String name;
    String trx;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_tagihan, container, false);
        prefManager = new PrefManager(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        name = (sharedPreferences.getString(Config.DISPLAY_NAME, ""));
        email = (sharedPreferences).getString(Config.DISPLAY_EMAIL, "");

        initUI();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("Cek Tagihan");
        return view;
    }

    private void initUI() {
        edNmrTagihan = (EditText) view.findViewById(R.id.edNmrTagihan);
        txtTagihan = (TextView) view.findViewById(R.id.txtTagihan);
        btnCek = (Button) view.findViewById(R.id.btnCek);
        btnPay = (Button) view.findViewById(R.id.btnPay);

        btnPay.setOnClickListener(this);
        btnCek.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCek:
                trx = edNmrTagihan.getText().toString();
                if (TextUtils.isEmpty(userId)) {
                    cekTagihan();
                } else {
                    updateUser();
                }
                break;
            case R.id.btnPay:
                bayarTagihan();
                break;
        }
    }

    private void updateUser() {
        if (!TextUtils.isEmpty(trx)) {
            mFirebaseDatabase.child(userId).child("trx").setValue(trx);
        }
    }

    private void bayarTagihan() {

    }

    private void cekTagihan() {


        String tagihan = "";
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        DatabaseCek databaseCek = new DatabaseCek(name, email, trx, tagihan);
        mFirebaseDatabase.child(userId).setValue(databaseCek);
        //addDbaseChangeListener();

    }

    private void addDbaseChangeListener() {
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseCek databaseCek = dataSnapshot.getValue(DatabaseCek.class);
                if (databaseCek == null) {
                    Log.e("DatabaseCek", "User data is null");
                    return;
                }
                Log.e("Database Cek", "User data is changed");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Cek", "Failed Update User Data");
            }
        });
    }
}
