package com.example.dataviwerproject;

public class Constants {

    public static final String DB_NAME = "MY_RECORDS_DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "MY_RECORDS_TABLE";
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE = "IMAGETEXT";  // Agora o nome da coluna é IMAGETEXT
    public static final String C_BIO = "BIO";
    public static final String C_PHONE = "PHONE";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_DOB = "DOB";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATED_TIME_STAMP";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"  // Corrigido: nome da coluna para IMAGETEXT
            + C_BIO + " TEXT,"
            + C_PHONE + " TEXT,"
            + C_EMAIL + " TEXT,"
            + C_DOB + " TEXT,"
            + C_ADDED_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            + ")";
}
