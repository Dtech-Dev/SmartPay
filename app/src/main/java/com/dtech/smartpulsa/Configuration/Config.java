package com.dtech.smartpulsa.Configuration;

/**
 * Created by aris on 02/10/16.
 */

public class Config {
    public static final String URL_KODE="http://samimi.web.id/dev/add-kode.php";
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
}
/*
"id"=>$row['id_customer'],
        "user_name"=>$row['user_name'],
        "user_email"=>$row['user_email'],
        "user_phone"=>$row['user_phone']*/
