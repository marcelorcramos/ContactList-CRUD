package com.example.dataviwerproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.ViewHolder> {

    private Context context;
    private ArrayList<ModelRecord> recordList;
    private MyDbHelper dbHelper;

    public AdapterRecord(Context context, ArrayList<ModelRecord> recordList) {
        this.context = context;
        this.recordList = recordList;
        this.dbHelper = new MyDbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_record_update, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRecord record = recordList.get(position);

        if (record.isEditing()) {
            holder.nameEt.setVisibility(View.VISIBLE);
            holder.phoneEt.setVisibility(View.VISIBLE);
            holder.emailEt.setVisibility(View.VISIBLE);
            holder.dobEt.setVisibility(View.VISIBLE);
            holder.bioEt.setVisibility(View.VISIBLE);

            holder.nameTv.setVisibility(View.GONE);
            holder.phoneTv.setVisibility(View.GONE);
            holder.emailTv.setVisibility(View.GONE);
            holder.dobTv.setVisibility(View.GONE);
            holder.bioTv.setVisibility(View.GONE);

            holder.nameEt.setText(record.getName());
            holder.phoneEt.setText(record.getPhone());
            holder.emailEt.setText(record.getEmail());
            holder.dobEt.setText(record.getDob());
            holder.bioEt.setText(record.getBio());
        } else {
            holder.nameEt.setVisibility(View.GONE);
            holder.phoneEt.setVisibility(View.GONE);
            holder.emailEt.setVisibility(View.GONE);
            holder.dobEt.setVisibility(View.GONE);
            holder.bioEt.setVisibility(View.GONE);

            holder.nameTv.setVisibility(View.VISIBLE);
            holder.phoneTv.setVisibility(View.VISIBLE);
            holder.emailTv.setVisibility(View.VISIBLE);
            holder.dobTv.setVisibility(View.VISIBLE);
            holder.bioTv.setVisibility(View.VISIBLE);

            holder.nameTv.setText(record.getName());
            holder.phoneTv.setText(record.getPhone());
            holder.emailTv.setText(record.getEmail());
            holder.dobTv.setText(record.getDob());
            holder.bioTv.setText(record.getBio());
        }

        // Botão para remover o perfil
        holder.moreBtn.setOnClickListener(view -> {
            dbHelper.deleteData(record.getId());
            recordList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Perfil removido", Toast.LENGTH_SHORT).show();
        });

        holder.ligarBtn.setOnClickListener(view -> {
            String phoneNumber = record.getPhone(); // Pegue o número salvo
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                Toast.makeText(context, "Número de telefone não disponível", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));

            // Verifica a permissão antes de fazer a chamada
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    context.startActivity(callIntent);
                } catch (SecurityException e) {
                    Toast.makeText(context, "Erro ao tentar realizar a chamada", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Permissão de chamada não concedida", Toast.LENGTH_SHORT).show();
            }
        });




        // Botão para editar
        holder.editBtn.setOnClickListener(view -> {
            record.setEditing(!record.isEditing());
            notifyItemChanged(position);
        });

        // Botão para salvar as alterações
        holder.saveBtn.setOnClickListener(view -> {
            record.setName(holder.nameEt.getText().toString());
            record.setPhone(holder.phoneEt.getText().toString());
            record.setEmail(holder.emailEt.getText().toString());
            record.setDob(holder.dobEt.getText().toString());
            record.setBio(holder.bioEt.getText().toString());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.C_NAME, record.getName());
            values.put(Constants.C_PHONE, record.getPhone());
            values.put(Constants.C_EMAIL, record.getEmail());
            values.put(Constants.C_DOB, record.getDob());
            values.put(Constants.C_BIO, record.getBio());

            db.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[]{record.getId()});
            db.close();

            record.setEditing(false);
            notifyItemChanged(position);
            Toast.makeText(context, "Contato atualizado", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void addNewRecord(ModelRecord newRecord) {
        recordList.add(0, newRecord);
        notifyItemInserted(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, phoneTv, emailTv, dobTv, bioTv;
        EditText nameEt, phoneEt, emailEt, dobEt, bioEt;
        ImageView profileIv;
        FloatingActionButton saveBtn, editBtn, moreBtn, ligarBtn; // Alterado para FloatingActionButton

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            dobTv = itemView.findViewById(R.id.dobTv);
            bioTv = itemView.findViewById(R.id.bioTv);

            nameEt = itemView.findViewById(R.id.nameEt);
            phoneEt = itemView.findViewById(R.id.phoneEt);
            emailEt = itemView.findViewById(R.id.emailEt);
            dobEt = itemView.findViewById(R.id.dobEt);
            bioEt = itemView.findViewById(R.id.bioEt);

            profileIv = itemView.findViewById(R.id.profileIv);
            saveBtn = itemView.findViewById(R.id.saveBtn);
            editBtn = itemView.findViewById(R.id.editBtn);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            ligarBtn = itemView.findViewById(R.id.ligarBtn);
        }
    }
}

