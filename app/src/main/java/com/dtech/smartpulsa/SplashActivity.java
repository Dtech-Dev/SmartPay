package com.dtech.smartpulsa;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dtech.smartpulsa.preference.PrefManager;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {

    PrefManager prefManager;
    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);

        setContentView(R.layout.activity_splash);

        if (prefManager.isFirstTimeLaunch()) {
            launchWelcome();
        }

        particleView = (ParticleView) findViewById(R.id.particle);
        particleView.startAnim();
        particleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                new Loading().execute();
            }
        });


    }

    private void launchWelcome() {
        Intent welcome = new Intent(this, WelcomeActivity.class);
        startActivity(welcome);
    }

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Thread.sleep(2000);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // When progress finished, open ActivityHome
            Intent homeIntent = new Intent(getApplicationContext(), TempActivity.class);
            startActivity(homeIntent);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}
