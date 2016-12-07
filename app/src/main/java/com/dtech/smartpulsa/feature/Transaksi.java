package com.dtech.smartpulsa.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.TrfConfirmActivity;

import java.util.HashMap;

/**
 * Created by aris on 27/11/16.
 */

public class Transaksi extends AsyncTask<Void, Void, String> {

    String kode;
    String user;
    String jenisTransaksi;
    String NomorTuj;
    String firebaseId;
    ProgressDialog progress;
    Context context;

    public Transaksi(Context context) {
        //this.kode = kode;
        this.context = context;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public String getNomorTuj() {
        return NomorTuj;
    }

    public void setNomorTuj(String nomorTuj) {
        NomorTuj = nomorTuj;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String, String> paramsTransaksi = new HashMap<>();
        paramsTransaksi.put(Config.TRX_PULSA_EMAIL, user);
        paramsTransaksi.put(Config.TRX_PULSA_FORMATTRX, kode);
        paramsTransaksi.put(Config.TRX_PULSA_FIREBASEID, firebaseId);
        paramsTransaksi.put(Config.TRX_PULSA_KODE, jenisTransaksi);
        paramsTransaksi.put(Config.TRX_PULSA_NOMORTUJ, NomorTuj);

        RequestHandler reqHandler = new RequestHandler();
        String res = reqHandler.sendPostRequest(Config.TRX_URL, paramsTransaksi);

        return res;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Processing...", "Wait....", false, false);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.dismiss();
    }
}
