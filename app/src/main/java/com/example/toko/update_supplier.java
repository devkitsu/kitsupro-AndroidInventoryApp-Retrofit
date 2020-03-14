package com.example.toko;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toko.API.insertSupplierAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class update_supplier extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/supplier/";
    private ProgressDialog progress;
    @BindView(R.id.et_idsup)
    EditText et_idsup;
    @BindView(R.id.et_namasupplier) EditText et_namasupplier;
    @BindView(R.id.et_tlpsupplier) EditText et_tlpsupplier;
    @BindView(R.id.et_alamatsupplier) EditText et_alamatsupplier;
    @OnClick(R.id.buttonEditSup) void ubah(){
        //mengambil data dari edittext
        String id_supplier = et_idsup.getText().toString();
        String nama_supplier = et_namasupplier.getText().toString();
        String telepon_supplier = et_tlpsupplier.getText().toString();
        String alamat_supplier = et_alamatsupplier.getText().toString();
        if (TextUtils.isEmpty(nama_supplier)) {
            et_namasupplier.setError("Nama pelanggan tidak boleh kosong");
        } else if(TextUtils.isEmpty(telepon_supplier)) {
            et_tlpsupplier.setError("Telepon pelanggan tidak boleh kosong");
        } else if(TextUtils.isEmpty(alamat_supplier)) {
            et_alamatsupplier.setError("Alamat pelanggan tidak boleh kosong");
        } else {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertSupplierAPI api = retrofit.create(insertSupplierAPI.class);
            Call<Value> call = api.ubah(id_supplier, nama_supplier, telepon_supplier, alamat_supplier);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        notif();
                    } else {
                        Toast.makeText(update_supplier.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(update_supplier.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.buttonDelSup) void hapus(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Apakah anda yakin ingin hapus?");
        alertDialogBuilder.setIcon(R.drawable.warn).setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
        String id_supplier = et_idsup.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertSupplierAPI api = retrofit.create(insertSupplierAPI.class);
        Call<Value> call = api.hapus(id_supplier);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(update_supplier.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(update_supplier.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(update_supplier.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
})
        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
        // jika tombol ini diklik, akan menutup dialog
        // dan tidak terjadi apa2
        dialog.cancel();
        }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        Intent intent = getIntent();
        String id = intent.getStringExtra("id_supplier");
        String nama = intent.getStringExtra("nama_supplier");
        String tlp = intent.getStringExtra("telepon_supplier");
        String alamat = intent.getStringExtra("alamat_supplier");

        et_idsup.setText(id);
        et_namasupplier.setText(nama);
        et_tlpsupplier.setText(tlp);
        et_alamatsupplier.setText(alamat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void notif(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Data telah diubah");
        alertDialogBuilder.setIcon(R.drawable.confirm).setCancelable(true);
        alertDialogBuilder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
