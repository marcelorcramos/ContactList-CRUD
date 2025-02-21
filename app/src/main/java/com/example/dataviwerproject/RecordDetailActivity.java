package com.example.dataviwerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecordDetailActivity extends AppCompatActivity {

    private ImageView profileIv;
    private TextView bioTv, nameTv, phoneTv, emailTv, dobTv, addedTimeTv, updatedTimeTv;

    private ActionBar actionBar;
    private MyDbHelper dbHelper;
    private String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Record Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        dbHelper = new MyDbHelper(this);

        profileIv = findViewById(R.id.profileIv);
        bioTv = findViewById(R.id.bioTv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        dobTv = findViewById(R.id.dobTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);

        showRecordDetails();
    }

    private void showRecordDetails() {
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " = ?";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{recordID});

        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
            String bio = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIO));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB));
            String timestampAdded = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIMESTAMP));
            String timestampUpdated = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATE_TIMESTAMP));

            Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
            calendar1.setTimeInMillis(Long.parseLong(timestampAdded));
            String timeAdded = DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1).toString();

            Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
            calendar2.setTimeInMillis(Long.parseLong(timestampUpdated));
            String timeUpdated = DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2).toString();

            nameTv.setText(name);
            bioTv.setText(bio);
            phoneTv.setText(phone);
            emailTv.setText(email);
            dobTv.setText(dob);
            addedTimeTv.setText(timeAdded);
            updatedTimeTv.setText(timeUpdated);

            if(image.equals("null")){
                profileIv.setImageResource(R.drawable.ic_person);
            }
            else{
                profileIv.setImageURI(Uri.parse(image));
            }
        }

        cursor.close();
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
