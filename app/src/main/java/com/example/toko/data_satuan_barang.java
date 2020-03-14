package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.toko.API.insertSatuanBarangAPI;
import com.example.toko.Adapter.RecyclerViewAdapter_Satuan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class data_satuan_barang extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public void tambahsatbrg_btn(View view) {
        startActivity(new Intent(data_satuan_barang.this, tambah_satuan_barang.class));
    }
    public static final String URL = "http://192.168.43.66/simplecrud/satuan_barang/";
    private List<Result> results = new ArrayList<>();
    private RecyclerViewAdapter_Satuan viewAdapter_satuan;
    @BindView(R.id.recyclerView_satuan) RecyclerView recyclerView_satuan;
    @BindView(R.id.progressbar_satuan)  ProgressBar progressbar_satuan;
    @BindView(R.id.satuan_search) SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_satuan_barang);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Satuan Barang");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewAdapter_satuan = new RecyclerViewAdapter_Satuan(this, results);
        RecyclerView.LayoutManager mLayoutManager_satuan = new LinearLayoutManager(getApplicationContext());
        recyclerView_satuan.setLayoutManager(mLayoutManager_satuan);
        recyclerView_satuan.setItemAnimator(new DefaultItemAnimator());
        recyclerView_satuan.setAdapter(viewAdapter_satuan);
        searchView.setQueryHint("Cari Nama Satuan");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        LoadSatuan();
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

    @Override
    protected void onResume() {
        super.onResume();
        LoadSatuan();
    }

    private void LoadSatuan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertSatuanBarangAPI api = retrofit.create(insertSatuanBarangAPI.class);
        Call<Value> call = api.getsatuanbarang();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressbar_satuan.setVisibility(View.GONE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter_satuan = new RecyclerViewAdapter_Satuan(data_satuan_barang.this, results);
                    recyclerView_satuan.setAdapter(viewAdapter_satuan);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView_satuan.setVisibility(View.GONE);
        progressbar_satuan.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertSatuanBarangAPI api = retrofit.create(insertSatuanBarangAPI.class);
        Call<Value> call = api.search(newText);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressbar_satuan.setVisibility(View.GONE);
                recyclerView_satuan.setVisibility(View.VISIBLE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter_satuan = new RecyclerViewAdapter_Satuan(data_satuan_barang.this, results);
                    recyclerView_satuan.setAdapter(viewAdapter_satuan);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressbar_satuan.setVisibility(View.GONE);
            }
        });
        return true;
    }
}