package com.example.toko;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toko.API.insertPelangganAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_pelanggan extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/pelanggan/";
    private ProgressDialog progress;
    @BindView(R.id.et_namapelanggan) EditText et_namapelanggan;
    @BindView(R.id.et_alamatpelanggan) EditText et_alamatpelanggan;
    @BindView(R.id.et_tlppelanggan) EditText et_tlppelanggan;
    @OnClick(R.id.buttonSimpanPelanggan)
    void insertPelanggan(){
        String nama_pelanggan = et_namapelanggan.getText().toString();
        String tlp_pelanggan = et_tlppelanggan.getText().toString();
        String alamat_pelanggan = et_alamatpelanggan.getText().toString();
        if (TextUtils.isEmpty(nama_pelanggan)) {
            et_namapelanggan.setError("Nama pelanggan tidak boleh kosong");
        } else if(TextUtils.isEmpty(tlp_pelanggan)) {
            et_tlppelanggan.setError("Telepon pelanggan tidak boleh kosong");
        } else if(TextUtils.isEmpty(alamat_pelanggan)) {
            et_alamatpelanggan.setError("Alamat pelanggan tidak boleh kosong");
        } else {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertPelangganAPI api = retrofit.create(insertPelangganAPI.class);
            Call<Value> call = api.insertpelanggan(nama_pelanggan, tlp_pelanggan, alamat_pelanggan);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(tambah_pelanggan.this, message, Toast.LENGTH_SHORT).show();
                        et_namapelanggan.setText("");
                        et_alamatpelanggan.setText("");
                        et_tlppelanggan.setText("");
                    } else {
                        Toast.makeText(tambah_pelanggan.this, message, Toast.LENGTH_SHORT).show();
                        et_namapelanggan.setText("");
                        et_alamatpelanggan.setText("");
                        et_tlppelanggan.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(tambah_pelanggan.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_pelanggan);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Pelanggan");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
