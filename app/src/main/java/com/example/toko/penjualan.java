package com.example.toko;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toko.API.insertBarangAPI;
import com.example.toko.API.insertPelangganAPI;
import com.example.toko.API.insertPenjualanAPI;
import com.example.toko.Adapter.RecyclerViewAdapter_Penjualan;

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

public class penjualan extends AppCompatActivity {
    public static final String URL2 = "http://192.168.43.66/simplecrud/data_barang/";
    public static final String URL1 = "http://192.168.43.66/simplecrud/pelanggan/";
    public static final String URL = "http://192.168.43.66/simplecrud/penjualan/";
    Context mContext;
    ProgressDialog loading;
    Calendar c;
    DatePickerDialog dpd;
    String selected_pel, selectedpel, selected_brg, selectedbrg;
    private List<Result> results = new ArrayList<>();
    List<String> listspinnerPel = new ArrayList<String>();
    List<String> listspinnerPelId = new ArrayList<String>();
    List<String> listspinnerBrg = new ArrayList<String>();
    List<String> listspinnerBrgId = new ArrayList<String>();

    private RecyclerViewAdapter_Penjualan viewAdapter;
    @BindView(R.id.rview) RecyclerView recyclerView;
    @BindView(R.id.spinnerPel)  Spinner spinnerPel;
    @BindView(R.id.spinnerBrg)  Spinner spinnerBrg;
    @BindView(R.id.textFaktur) EditText txtFaktur;
    @BindView(R.id.txt_kal) EditText txtKal;
    @BindView(R.id.idpel) EditText idPel;
    @BindView(R.id.idbrg) EditText idBrg;
    @BindView(R.id.textJumlah) EditText txtJml;
    @BindView(R.id.textHargaSat) EditText txtHrgSat;
    @OnClick(R.id.kal_btn) void kal_btn() {

        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(penjualan.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mday) {
                txtKal.setText(mday + "/" + (mMonth + 1) + "/" + mYear);
            }
        }, year, month, day);
        dpd.show();
    }
    @OnClick(R.id.btn_faktur) void fakturbaru(){
        txtFaktur.setText("");
        txtKal.setText("");
        clearAdapter();
        notif2();
    }
    @OnClick(R.id.btn_cart) void insert_penjualan(){

        String id_penjualan = txtFaktur.getText().toString();
        String tgl_penjualan = txtKal.getText().toString();
        String qty = txtJml.getText().toString();
        String harga_satuan = txtHrgSat.getText().toString();
        if (TextUtils.isEmpty(id_penjualan)) {
            txtFaktur.setError("Nomor Faktur tidak boleh kosong");
        } else if(TextUtils.isEmpty(tgl_penjualan)) {
            Toast.makeText(penjualan.this, "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(qty)) {
            txtJml.setError("Qty barang tidak boleh kosong");
        } else if(TextUtils.isEmpty(harga_satuan)) {
            txtHrgSat.setError("Harga satuan tidak boleh kosong");
        } else {
            loading = new ProgressDialog(this);
            loading.setCancelable(true);
            loading.setMessage("Loading ...");
            loading.show();
            int id_pelanggan = Integer.parseInt(selected_pel);
            int id_barang = Integer.parseInt(selected_brg);
            int qty_penjualan = Integer.parseInt(qty);
            int harga_satuan_penjualan = Integer.parseInt(harga_satuan);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertPenjualanAPI api = retrofit.create(insertPenjualanAPI.class);
            Call<Value> call = api.penjualan(id_penjualan, tgl_penjualan, id_pelanggan, id_barang, qty_penjualan, harga_satuan_penjualan);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    loading.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(penjualan.this, message, Toast.LENGTH_SHORT).show();
                        txtJml.setText("");
                        txtHrgSat.setText("");
                        loadCart();
                    } else {
                        Toast.makeText(penjualan.this, message, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(penjualan.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    Log.d("Penjualan Error", String.valueOf(t));
                }
            });
        }
    }
    @OnClick(R.id.btn_add) void simpan(){
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Loading ...");
        loading.show();

        String id_penjualan = txtFaktur.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPenjualanAPI api = retrofit.create(insertPenjualanAPI.class);
        Call<Value> call = api.simpan_penjualan(id_penjualan);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                loading.dismiss();
                if (value.equals("1")) {
                    notif();
                    txtFaktur.setText("");
                    txtKal.setText("");
                    clearAdapter();
                } else {
                    Toast.makeText(penjualan.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(penjualan.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                Log.d("Penjualan Error", String.valueOf(t));
                t.printStackTrace();
            }
        }) ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penjualan);
        ButterKnife.bind(this);
        mContext = this;
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

       initSpinnerPel();
       initSpinnerBrg();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Penjualan");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewAdapter = new RecyclerViewAdapter_Penjualan(this,results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }

    public void clearAdapter() {
        viewAdapter.clear();
    }

    private void loadCart(){
        String id_penjualan = txtFaktur.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPenjualanAPI api = retrofit.create(insertPenjualanAPI.class);
        Call<Value> call = api.getdetail(id_penjualan);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter_Penjualan(penjualan.this, results);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }

    public void initSpinnerPel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPelangganAPI api = retrofit.create(insertPelangganAPI.class);
        Call<Value> call = api.getPelanggan();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Result> semuapel = response.body().getResult();
                    listspinnerPel = new ArrayList<String>();
                    listspinnerPelId = new ArrayList<String>();
                    //List<Integer> listspinner2 = new ArrayList<Integer>();
                    for (int i = 0; i <semuapel.size(); i++) {
                        listspinnerPelId.add(semuapel.get(i).getId_pelanggan());
                        listspinnerPel.add(semuapel.get(i).getNama_pelanggan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listspinnerPel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPel.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data pelanggan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerPel.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedpel = listspinnerPel.get(position);
                selected_pel = listspinnerPelId.get(position);
                Toast.makeText(mContext, "Nama pelanggan adalah " + selectedpel+", dengan ID " + selected_pel, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerBrg(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertBarangAPI api = retrofit.create(insertBarangAPI.class);
        Call<Value> call = api.getbarang();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Result> semuabrg = response.body().getResult();
                    listspinnerBrg = new ArrayList<String>();
                    listspinnerBrgId = new ArrayList<String>();
                    //List<Integer> listspinner2 = new ArrayList<Integer>();
                    for (int i = 0; i <semuabrg.size(); i++) {
                        listspinnerBrgId.add(semuabrg.get(i).getId_barang());
                        listspinnerBrg.add(semuabrg.get(i).getNama_barang());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listspinnerBrg);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBrg.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data barang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerBrg.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedbrg = listspinnerBrg.get(position);
                selected_brg = listspinnerBrgId.get(position);
                Toast.makeText(mContext, "Nama Barang adalah " + selectedbrg+", dengan ID " + selected_brg, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void notif(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Data telah disimpan");
        alertDialogBuilder.setIcon(R.drawable.confirm).setCancelable(true);
        alertDialogBuilder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void notif2(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Faktur Baru");
        alertDialogBuilder.setIcon(R.drawable.confirm).setCancelable(true);
        alertDialogBuilder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
