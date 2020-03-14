package com.example.toko;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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

public class tambah_supplier extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/supplier/";
    private ProgressDialog progress;
    @BindView(R.id.et_namasupplier) EditText et_namasupplier;
    @BindView(R.id.et_alamatsupplier) EditText et_alamatsupplier;
    @BindView(R.id.et_teleponsupplier) EditText et_teleponsupplier;
    @OnClick(R.id.buttonSimpanSupplier)
    void insertSupplier(){
        String nama_supplier = et_namasupplier.getText().toString();
        String telepon_supplier = et_teleponsupplier.getText().toString();
        String alamat_supplier = et_alamatsupplier.getText().toString();
        if (TextUtils.isEmpty(nama_supplier)) {
            et_namasupplier.setError("Nama pelanggan tidak boleh kosong");
        } else if(TextUtils.isEmpty(telepon_supplier)) {
            et_teleponsupplier.setError("Telepon pelanggan tidak boleh kosong");
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
            Call<Value> call = api.insert_supplier(nama_supplier, telepon_supplier, alamat_supplier);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(tambah_supplier.this, message, Toast.LENGTH_SHORT).show();
                        et_namasupplier.setText("");
                        et_alamatsupplier.setText("");
                        et_teleponsupplier.setText("");
                    } else {
                        Toast.makeText(tambah_supplier.this, message, Toast.LENGTH_SHORT).show();
                        et_namasupplier.setText("");
                        et_alamatsupplier.setText("");
                        et_teleponsupplier.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(tambah_supplier.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_supplier);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Supplier");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
