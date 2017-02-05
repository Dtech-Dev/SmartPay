package com.dtech.smartpulsa;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.preference.PrefManager;
import com.race604.drawable.wave.WaveDrawable;

import java.io.File;


public class SplashActivity extends AppCompatActivity {

    PrefManager prefManager;
    //ParticleView particleView;
    ImageView imgsplash;
    WaveDrawable mWaveDrawable;
    SeekBar mLevelSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //prefManager = new PrefManager(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        imgsplash = (ImageView) findViewById(R.id.imgsplash);
        mWaveDrawable = new WaveDrawable(this, R.drawable.android_robot);
        imgsplash.setImageDrawable(mWaveDrawable);
        mWaveDrawable.setLevel(5000);

        mWaveDrawable.setIndeterminate(true);
        //mWaveDrawable.setLevel(30);
        /*particleView = (ParticleView) findViewById(R.id.particle);
        particleView.startAnim();
        particleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {

            }
        });*/

        /*if (prefManager.isFirstTimeLaunch()) {
            launchWelcome();
        } else {
            new Loading().execute();
        }*/
        if (!fileExistance(Config.FIRST_TIME)) {
            launchWelcome();
        } else {
            new Loading().execute();
        }


    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private void launchWelcome() {
        Intent welcome = new Intent(this, WelcomeActivity.class);
        startActivity(welcome);
        finish();
    }

    public class Loading extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Thread.sleep(4000);
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
