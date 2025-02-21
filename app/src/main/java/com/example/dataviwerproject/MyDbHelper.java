package com.example.dataviwerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertRecord(String name, String image, String bio, String phone, String email,
                             String dob, String addedTime, String updatedTime) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.C_NAME, name);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_BIO, bio);
        values.put(Constants.C_PHONE, phone);
        values.put(Constants.C_EMAIL, email);
        values.put(Constants.C_DOB, dob);
        values.put(Constants.C_ADDED_TIMESTAMP, addedTime);
        values.put(Constants.C_UPDATE_TIMESTAMP, updatedTime);

        long id = db.insert(Constants.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public ArrayList<ModelRecord> getAllRecords(String orderBy) {

        ArrayList<ModelRecord> recordArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATE_TIMESTAMP))
                );

                recordArrayList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recordArrayList;
    }

    public ArrayList<ModelRecord> searchRecords(String query) {

        ArrayList<ModelRecord> recordArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME +
                " WHERE " + Constants.C_NAME + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATE_TIMESTAMP))
                );

                recordArrayList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recordArrayList;
    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();

        if (deletedRows > 0) {
            Log.d("MyDbHelper", "Perfil removido com sucesso!");
        } else {
            Log.d("MyDbHelper", "Erro ao remover perfil!");
        }
    }


    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }

    public int getRecordsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void deleteData() {
    }

}