package com.dtech.smartpulsa.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.R;

public class PulsaActivity extends AppCompatActivity {

    Intent intent=getIntent();
    Bundle extras;
    String selfIntent;

    TextView tuserNumber;
    //otherIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras=getIntent().getExtras();
        if (extras!=null) {
            selfIntent = extras.getString("self");
            //Toast.makeText(this, selfIntent, Toast.LENGTH_LONG).show();
            if (selfIntent.equals("selfNumber") ) {
                //tuserNumber.setText("Self");
                UISelfNumber();
            } else {
                //tuserNumber.setText("other");
                UIOtherNumber();
            }
        }



    }

    private void UIOtherNumber() {
        findViewById(R.id.otherLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.selfLayout).setVisibility(View.GONE);

    }

    private void UISelfNumber() {
        findViewById(R.id.selfLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.otherLayout).setVisibility(View.GONE);
    }

    private void initUI() {
        tuserNumber = (TextView) findViewById(R.id.tuserNumber);
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();
        this.closeContextMenu();*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
