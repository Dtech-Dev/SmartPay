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
    ProgressDialog progress;
    Context context;

    public Transaksi(Context context) {
        //this.kode = kode;
        this.context = context;
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
        paramsTransaksi.put(Config.KEY_TKODE_NAME, user);
        paramsTransaksi.put(Config.KEY_TKODE_KODE, kode);


        RequestHandler reqHandler = new RequestHandler();
        String res = reqHandler.sendPostRequest(Config.URL_KODE, paramsTransaksi);

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
