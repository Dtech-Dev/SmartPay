package com.dtech.smartpulsa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AddSaldoActivity extends AppCompatActivity {

    Button bca, mandiri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saldo);
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

        bca = (Button) findViewById(R.id.btnBca);
        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirm = new Intent(AddSaldoActivity.this, TrfConfirmActivity.class);
                confirm.putExtra("bank", "BCA");
                startActivity(confirm);
            }
        });

        mandiri = (Button) findViewById(R.id.btnmandiri);
        mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirm = new Intent(AddSaldoActivity.this, TrfConfirmActivity.class);
                confirm.putExtra("bank", "Mandiri");
                startActivity(confirm);
            }
        });
    }

}
