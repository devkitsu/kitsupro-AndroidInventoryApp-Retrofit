package com.example.toko;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toko.API.insertKatAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_kategori extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/kategori/";
    private ProgressDialog progress;
    @BindView(R.id.et_namakat) EditText et_namakat;
    @OnClick(R.id.buttonSimpanKat)
    void insertkat(){
        String nama_kategori = et_namakat.getText().toString();
        if (TextUtils.isEmpty(nama_kategori)) {
            et_namakat.setError("Nama kategori tidak boleh kosong");
        } else {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertKatAPI api = retrofit.create(insertKatAPI.class);
            Call<Value> call = api.kategori(nama_kategori);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(tambah_kategori.this, message, Toast.LENGTH_SHORT).show();
                        et_namakat.setText("");
                    } else {
                        Toast.makeText(tambah_kategori.this, message, Toast.LENGTH_SHORT).show();
                        et_namakat.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(tambah_kategori.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_kategori);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Kategori");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
