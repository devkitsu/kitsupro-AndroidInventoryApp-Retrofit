package com.example.toko.Laporan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toko.API.insertPembelianAPI;
import com.example.toko.Adapter.RecyclerViewAdapter_LaporanPembelian;
import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.Value;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class laporan_pembelian extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public static final String URL = "http://192.168.43.66/simplecrud/pembelian/";
    private List<Result> results = new ArrayList<>();
    private RecyclerViewAdapter_LaporanPembelian viewAdapter;
    Context mContext;
    ProgressDialog loading;
    Calendar c;
    DatePickerDialog dpd;
    @BindView(R.id.rview) RecyclerView recyclerView;
    @BindView(R.id.txt_kal) TextView mTv;
    @BindView(R.id.search) SearchView searchView;
    @OnClick(R.id.kal_btn) void kal_btn() {

        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(laporan_pembelian.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                mTv.setText(mday + "/" + (mMonth + 1) + "/" + mYear);
            }
        }, year, month, day);
        dpd.show();
    }
    @OnClick(R.id.btn_faktur) void btn_submit(){
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Loading ...");
        loading.show();
        String tgl_pembelian = mTv.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
        Call<Value> call = api.getlaporan(tgl_pembelian);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("1")) {
                    loading.dismiss();
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter_LaporanPembelian(laporan_pembelian.this, results);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Log.d("ERROR LAPORAN", String.valueOf(t));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_pembelian);
        ButterKnife.bind(this);
        mContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Laporan Pembelian");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewAdapter = new RecyclerViewAdapter_LaporanPembelian(this,results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        searchView.setQueryHint("Cari Berdasarkan Supplier");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setVisibility(View.GONE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
        Call<Value> call = api.searchsup(newText);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter_LaporanPembelian(laporan_pembelian.this, results);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {Log.d("ERROR CARI", String.valueOf(t));
            }
        });
        return true;
    }
}
