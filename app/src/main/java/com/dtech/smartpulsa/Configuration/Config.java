package com.dtech.smartpulsa.Configuration;

/**
 * Created by aris on 02/10/16.
 */

public class Config {
    /*Shared Preference*/
    public static final String PREF_NAME = "app-welcome";
    public static final String DISPLAY_NAME = "displayName";
    public static final String DISPLAY_NUMBER = "displayNumber";
    public static final String DISPLAY_EMAIL = "displayEmail";
    public static final String DISPLAY_FIREBASE_ID = "firebaseId";
    /**/
    public static final String URL_KODE="http://samimi.web.id/dev/add-code.php";
    public static final String URL_SELECT_CUSTOMER="http://samimi.web.id/dev/select-customer.php";
    public static final String URL_SELECT_ALL="http://samimi.web.id/dev/select-all.php?email=";
    public static final String URL_ADD_SALDO="http://samimi.web.id/dev/tambah-saldo.php";


    /*url and post variable request -> query kode provider*/
    public static final String URL_QUERY_KODE="http://samimi.web.id/dev/post-request.php";
    public static final String TAG_PROVIDER = "provider";
    public static final String TAG_NOMINAL = "nominal";

    /*post variables for transaksi pulsa
    * $email = $_POST['email'];
    $firebase = $_POST['firebaseId'];
    $transaksi = $_POST['kodeTrans'];
    $nomorTuj = $_POST['nomor'];
    $status = $_POST['status'];
    $formatTrx = $_POST['formatTrx'];
    */
    public static final String TRX_URL = "http://samimi.web.id/dev/insert-log-transaksi.php";
    public static final String TRX_PULSA_EMAIL = "email";
    public static final String TRX_PULSA_FIREBASEID = "firebaseId";
    public static final String TRX_PULSA_KODE = "kodeTrans";
    public static final String TRX_PULSA_NOMORTUJ = "nomor";
    public static final String TRX_PULSA_FORMATTRX = "formatTrx";

    /*post variables cek tagihan
    * $fbase = $_POST['fbaseUid'];
      $nomorTagihan = $_POST['nomorTag'];*/
    public static final String URL_INSERT_TAGIHAN ="http://samimi.web.id/dev/cek-tagihan.php";
    public static final String FBASE_UID = "fbaseUid";
    public static final String NO_TAGIHAN = "nomorTag";

    //
    public static final String KEY_TKODE_NAME = "name";
    public static final String KEY_TKODE_KODE = "kode";

    //json tags for select customer
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_id="id";
    public static final String TAG_NAME="user_name";
    public static final String TAG_EMAIL="user_email";
    public static final String TAG_PHONE="user_phone";
    public static final String TAG_BALANCE="user_balance";
    public static final String TAG_POINT="user_point";
    public static final String TAG_STATUS="user_status";

    /*json tags for input table tambahSaldo.php
    * $date = $_POST['uDate'];
      $time = $_POST['uTime'];
      $last_update = $date." ".$time;
      $nomorRekUser = $_POST['nomorRekUser'];
      $namaRekUser = $_POST['namaRekUser'];
      $email = $_POST['email'];
      $rekening = $_POST['rekening'];
      $jumlahTransfer = $_POST['jmlTransfer'];*/
    public static final String TAG_NOREK_USER = "nomorRekUser";
    public static final String TAG_NAMAREK_USER = "namaRekUser";
    public static final String TAG_REK_TUJ = "rekening";
    public static final String TAG_JML_TRF = "jmlTransfer";
    public static final String TAG_EMAIL_USER = "email";
}

