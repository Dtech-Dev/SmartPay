package com.dtech.smartpulsa.Configuration;

/**
 * Created by aris on 02/10/16.
 */

public class Config {
    public static final String URL_KODE="http://samimi.web.id/dev/add-code.php";
    public static final String URL_SELECT_CUSTOMER="http://samimi.web.id/dev/select-customer.php";
    public static final String URL_SELECT_ALL="http://samimi.web.id/dev/select-all.php?email=";
    public static final String URL_ADD_SALDO="http://samimi.web.id/dev/tambah-saldo.php";

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
/*
"id"=>$row['id_customer'],
        "user_name"=>$row['user_name'],
        "user_email"=>$row['user_email'],
        "user_phone"=>$row['user_phone']*/
