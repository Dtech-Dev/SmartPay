package com.dtech.smartpulsa;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.preference.PrefManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity  implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    String userName, userEmail;
    String userNumber;
    TelephonyManager telephonyManager;
    RelativeLayout rel;
    TextView txtakun, txt2, txtNo;
    Button next1, next2;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    PrefManager prefManager;

    CheckBox cbx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHome();
            finish();
        }
        setContentView(R.layout.activity_welcome);

        telephonyManager=(TelephonyManager)this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        userNumber = telephonyManager.getLine1Number();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_next2).setOnClickListener(this);

        /*initialize google sign in*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        /**/
        /*1st ui*/
        rel = (RelativeLayout) findViewById(R.id.activity_welcome);
        txtakun = (TextView) findViewById(R.id.textView);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        next1 = (Button) findViewById(R.id.btn_next);
        /*2nd ui*/
        txt2 = (TextView) findViewById(R.id.textView2);
        txtNo = (TextView) findViewById(R.id.textViewNo);
        next2 = (Button) findViewById(R.id.btn_next2);
        cbx = (CheckBox) findViewById(R.id.cbx);




    }

    private void launchHome() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(WelcomeActivity.this, TempActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.btn_next:
                updateUI();
                break;
            case R.id.btn_next2:
                launchHome();
                break;
        }
    }

    private void updateUI() {
        /*String userNumber = "";*/
        /*GONE-ing 1st ui*/
        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);
        findViewById(R.id.activity_welcome).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        /*VISIBLE-ing 2nd ui*/
        findViewById(R.id.textView2).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewNo).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_next2).setVisibility(View.VISIBLE);
        findViewById(R.id.cbx).setVisibility(View.VISIBLE);

        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbx.isChecked()) {
                    next2.setEnabled(true);
                    next2.setTextColor(Color.WHITE);
                } else {
                    next2.setEnabled(false);
                    next2.setTextColor(Color.GRAY);
                }

            }
        });
        prefManager.setUserNumber(userNumber);
        txtNo.setText(userNumber);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            userName = acct.getDisplayName();
            userEmail = acct.getEmail();
            txtakun.setText(userName+"\n"+userEmail);
            prefManager.setUserDisplay(userName);
            prefManager.setUserEmail(userEmail);

            checkCustomer();


        }

    }

    private void checkCustomer() {
        class CheckCustomer extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WelcomeActivity.this, "Proccessing ..", "Reading Server", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(WelcomeActivity.this, "Welcome", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.TAG_NAME, userName);
                params.put(Config.TAG_EMAIL, userEmail);
                params.put(Config.TAG_PHONE, userNumber);

                RequestHandler requestHandler = new RequestHandler();
                String result = requestHandler.sendPostRequest(Config.URL_SELECT_CUSTOMER, params);

                return result;
            }
        }
        CheckCustomer checkCustomer = new CheckCustomer();
        checkCustomer.execute();
        next1.setTextColor(Color.WHITE);
        next1.setEnabled(true);

    }
}
