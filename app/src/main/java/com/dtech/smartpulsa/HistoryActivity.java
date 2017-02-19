package com.dtech.smartpulsa;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.dtech.smartpulsa.configuration.Config;
import com.dtech.smartpulsa.preference.PrefManager;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button bhistTr, bhistTagih;
    WebView webHistTr, webhistTagih;

    PrefManager prefManager;
    SharedPreferences sharedPreferences;
    String idUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUi();
    }

    private void initUi() {

        sharedPreferences = getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE);
        idUsr = (sharedPreferences.getString(Config.DISPLAY_IDUSR, ""));
        bhistTr = (Button) findViewById(R.id.bhistTr);
        bhistTagih = (Button) findViewById(R.id.bhistTagih);
        webHistTr = (WebView) findViewById(R.id.webHistTr);
        webhistTagih = (WebView) findViewById(R.id.webHistTagih);
        webhistTagih.loadUrl("http://samimi.web.id/dev/hist-tagih.php?id="+idUsr);
        webHistTr.loadUrl("http://samimi.web.id/dev/hist-tr.php?id="+idUsr);

        bhistTr.setOnClickListener(this);
        bhistTagih.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bhistTr:
                showHistTr();
                break;
            case R.id.bhistTagih:
                showHistTagih();
                break;
        }

    }

    private void showHistTagih() {
        webHistTr.setVisibility(View.GONE);
        webhistTagih.setVisibility(View.VISIBLE);

    }

    private void showHistTr() {
        webHistTr.setVisibility(View.VISIBLE);
        webhistTagih.setVisibility(View.GONE);

    }
}
