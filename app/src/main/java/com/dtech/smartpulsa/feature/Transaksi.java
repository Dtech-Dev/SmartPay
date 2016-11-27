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
    ProgressDialog progress;
    Context context;

    public Transaksi(Context context) {
        //this.kode = kode;
        this.context = context;
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
        paramsTransaksi.put(Config.KEY_TKODE_NAME, "aris");
        paramsTransaksi.put(Config.KEY_TKODE_KODE, kode);
        /*paramsAddSaldo.put(Config.TAG_NOREK_USER, noRekUser);
        paramsAddSaldo.put(Config.TAG_NAMAREK_USER, namaRekUser);
        paramsAddSaldo.put(Config.TAG_REK_TUJ, rekTujuan);
        paramsAddSaldo.put(Config.TAG_JML_TRF, jmlTrf);*/

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
