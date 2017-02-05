package com.dtech.smartpulsa.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by aris on 01/10/16.
 */

public class PrefManager {

    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    String userDisplayName;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "app-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String DISPLAY_NAME = "displayName";
    private static final String DISPLAY_NUMBER = "displayNumber";
    private static final String DISPLAY_EMAIL = "displayEmail";
    private static final String DISPLAY_POIN = "poin";
    private static final String DISPLAY_FIREBASE_ID = "firebaseId";
    private static final String DISPLAY_ID = "id";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

         /*String userDisplayName = pref.getString(DISPLAY_NAME,"");
        this.userDisplayName=userDisplayName;*/
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserDisplay(String displayName) {
        editor.putString(DISPLAY_NAME, displayName);
        editor.commit();
        //userDisplayName = displayName;
    }

    public void setUserNumber(String displayNumber) {
        editor.putString(DISPLAY_NUMBER, displayNumber);
        editor.commit();
    }

    public void setUserEmail(String displayEmail) {
        editor.putString(DISPLAY_EMAIL, displayEmail);
        editor.commit();
    }

    public void setFirebaseId(String firebaseId) {
        editor.putString(DISPLAY_FIREBASE_ID, firebaseId);
        editor.commit();
    }

    public void setPoin(String poin) {
        editor.putString(DISPLAY_POIN, poin);
        editor.commit();
    }

    public void setUri(String uri) {
        editor.putString(DISPLAY_ID, uri);
        editor.commit();
    }

    public String getUserDisplay() {
        userDisplayName = PreferenceManager.getDefaultSharedPreferences(_context).getString(DISPLAY_NAME, "");
        return userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
}
