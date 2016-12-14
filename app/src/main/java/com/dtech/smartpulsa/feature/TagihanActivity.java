package com.dtech.smartpulsa.feature;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.dtech.smartpulsa.R;
import com.dtech.smartpulsa.custom.CustomGridTagihan;

public class TagihanActivity extends AppCompatActivity {

    GridView gridView;

    public static String[] gridViewStrings = {
            "PLN",
            "PDAM",
            "Telkom",
            "Orange TV",
            "Indovision",
            "Aora TV",
            "FIF",
            "Astra Credit\nCompany",
            "WOM Finane"

    };
    public static int[] gridViewImages = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CustomGridTagihan(this, gridViewStrings, gridViewImages));

    }

}
