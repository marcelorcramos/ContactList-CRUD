package com.example.dataviwerproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addRecordBtn;
    private FloatingActionButton addPerfilBtn;
    private RecyclerView recordsRv;
    private MyDbHelper dbHelper;
    private ActionBar actionBar;
    private AdapterRecord adapterRecord;
    private ArrayList<ModelRecord> recordList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("All Records");
        } else {
            Log.e("MainActivity", "ActionBar is null");
        }

        addRecordBtn = findViewById(R.id.addRecordBtn);
        addPerfilBtn = findViewById(R.id.addPerfilBtn);
        recordsRv = findViewById(R.id.recordsRv);

        dbHelper = new MyDbHelper(this);
        loadRecords();

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelRecord newRecord = new ModelRecord(
                        String.valueOf(System.currentTimeMillis()), "", "", "", "", "", "", "", ""
                );
                newRecord.setAdding(true);
                newRecord.setEditing(true);
                adapterRecord.addNewRecord(newRecord);
                recordsRv.scrollToPosition(0);
            }
        });

        addPerfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Perfil.class));
            }
        });
    }

    private void loadRecords() {
        recordList = dbHelper.getAllRecords(Constants.C_ADDED_TIMESTAMP + " DESC");
        adapterRecord = new AdapterRecord(MainActivity.this, recordList);
        recordsRv.setAdapter(adapterRecord);

        if (actionBar != null) {
            actionBar.setSubtitle("Total: " + dbHelper.getRecordsCount());
        }
    }

    private void searchRecords(String query) {
        adapterRecord = new AdapterRecord(MainActivity.this, dbHelper.searchRecords(query));
        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verifique se a lista precisa ser recarregada
        if (recordList == null || recordList.isEmpty()) {
            loadRecords();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.moreBtn) {
            new AlertDialog.Builder(this)
                    .setTitle("Excluir todos os perfis")
                    .setMessage("Tem certeza que deseja excluir todos os perfis?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        dbHelper.deleteAllData();
                        onResume();
                    })
                    .setNegativeButton("Não", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}