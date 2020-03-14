package com.example.toko;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toko.API.insertSatuanBarangAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_satuan_barang extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/satuan_barang/";
    private ProgressDialog progress;
    @BindView(R.id.et_namasatuan) EditText et_namasatuan;
    @OnClick(R.id.buttonSatuanBrg) void insertsatuan(){

        String nama_satuan = et_namasatuan.getText().toString();
        if (TextUtils.isEmpty(nama_satuan)) {
            et_namasatuan.setError("Nama barang tidak boleh kosong");
        } else {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertSatuanBarangAPI api = retrofit.create(insertSatuanBarangAPI.class);
            Call<Value> call = api.add(nama_satuan);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(tambah_satuan_barang.this, message, Toast.LENGTH_SHORT).show();
                        et_namasatuan.setText("");
                    } else {
                        Toast.makeText(tambah_satuan_barang.this, message, Toast.LENGTH_SHORT).show();
                        et_namasatuan.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(tambah_satuan_barang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_satuan_barang);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Satuan Barang");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
