package com.example.toko.Laporan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.toko.API.insertPembelianAPI;
import com.example.toko.API.insertPenjualanAPI;
import com.example.toko.Adapter.RecyclerViewAdapter_LaporanPembelianDetail;
import com.example.toko.Adapter.RecyclerViewAdapter_LaporanPenjualanDetail;
import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.Value;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class laporan_penjualan_detail extends AppCompatActivity {
    public static final String URL = "http://192.168.43.66/simplecrud/penjualan/";
    Context mContext;
    ProgressDialog loading;
    @BindView(R.id.text_idpenjualan) TextView et_idjual;
    @BindView(R.id.textnama_pel) TextView et_namapel;
    @BindView(R.id.texttgl) TextView et_tgl;
    @BindView(R.id.textgrandtotal) TextView et_grandtotal;
    private List<Result> results = new ArrayList<>();
    private RecyclerViewAdapter_LaporanPenjualanDetail viewAdapter;
    @BindView(R.id.detail_jual)RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_penjualan_detail);
        ButterKnife.bind(this);

        mContext = this;
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id_penjualan");
        String nama = intent.getStringExtra("nama_pelanggan");
        String tgl = intent.getStringExtra("tgl_penjualan");
        String total = intent.getStringExtra("grand_total_penjualan");
        et_idjual.setText(id);
        et_namapel.setText(nama);
        et_tgl.setText(tgl);
        et_grandtotal.setText(total);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Laporan Penjualan");
        viewAdapter = new RecyclerViewAdapter_LaporanPenjualanDetail(this,results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        loadDetail();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDetail();
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

    private void loadDetail(){
        String id_penjualan = et_idjual.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPenjualanAPI api = retrofit.create(insertPenjualanAPI.class);
        Call<Value> call = api.getdetaillaporan(id_penjualan);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter_LaporanPenjualanDetail(laporan_penjualan_detail.this, results);
                    recyclerView.setAdapter(viewAdapter);
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }

}
