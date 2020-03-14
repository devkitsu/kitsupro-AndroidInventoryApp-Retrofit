package com.example.toko;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.toko.API.insertBarangAPI;
import com.example.toko.API.insertPelangganAPI;
import com.example.toko.API.insertPembelianAPI;
import com.example.toko.API.insertPenjualanAPI;
import com.example.toko.API.insertSupplierAPI;
import com.example.toko.Adapter.RecyclerViewAdapter_Pembelian;
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

public class pembelian  extends AppCompatActivity {
    public static final String URL2 = "http://192.168.43.66/simplecrud/data_barang/";
    public static final String URL1 = "http://192.168.43.66/simplecrud/supplier/";
    public static final String URL = "http://192.168.43.66/simplecrud/pembelian/";
    Context mContext;
    ProgressDialog loading;
    Calendar c;
    DatePickerDialog dpd;
    String selected_sup, selectedsup, selected_brg, selectedbrg;
    private List<Result> results = new ArrayList<>();
    List<String> listspinnerSup = new ArrayList<String>();
    List<String> listspinnerSupId = new ArrayList<String>();
    List<String> listspinnerBrg = new ArrayList<String>();
    List<String> listspinnerBrgId = new ArrayList<String>();

    private RecyclerViewAdapter_Pembelian viewAdapter;
    @BindView(R.id.rview)  RecyclerView recyclerView;
    @BindView(R.id.spinnerSup)  Spinner spinnerSup;
    @BindView(R.id.spinnerBrg)  Spinner spinnerBrg;
    @BindView(R.id.textFaktur)  EditText txtFaktur;
    @BindView(R.id.txt_kal) EditText txtKal;
    @BindView(R.id.idsup) EditText idSup;
    @BindView(R.id.idbrg) EditText idBrg;
    @BindView(R.id.textJumlah) EditText txtJml;
    @BindView(R.id.textHargaSat) EditText txtHrgSat;
    @OnClick(R.id.kal_btn) void kal_btn() {

        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(pembelian.this, new DatePickerDialog.OnDateSetListener() {
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
        String id_pembelian = txtFaktur.getText().toString();
        String tgl_pembelian = txtKal.getText().toString();
        String qty = txtJml.getText().toString();
        String harga_satuan = txtHrgSat.getText().toString();
        if (TextUtils.isEmpty(id_pembelian)) {
            txtFaktur.setError("Nomor Faktur tidak boleh kosong");
        } else if(TextUtils.isEmpty(tgl_pembelian)) {
            Toast.makeText(pembelian.this, "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(qty)) {
            txtJml.setError("Qty barang tidak boleh kosong");
        } else if(TextUtils.isEmpty(harga_satuan)) {
            txtHrgSat.setError("Harga satuan tidak boleh kosong");
        } else {
            loading = new ProgressDialog(this);
            loading.setCancelable(true);
            loading.setMessage("Loading ...");
            loading.show();
            int id_supplier = Integer.parseInt(selected_sup);
            int id_barang = Integer.parseInt(selected_brg);
            int qty_pembelian = Integer.parseInt(qty);
            int harga_satuan_pembelian = Integer.parseInt(harga_satuan);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
            Call<Value> call = api.pembelian(id_pembelian, tgl_pembelian, id_supplier, id_barang, qty_pembelian, harga_satuan_pembelian);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    loading.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(pembelian.this, message, Toast.LENGTH_SHORT).show();
                        txtJml.setText("");
                        txtHrgSat.setText("");
                        loadCart();
                    } else {
                        Toast.makeText(pembelian.this, message, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(pembelian.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    Log.d("pembelian Error", String.valueOf(t));
                }
            });
        }
    }
    @OnClick(R.id.btn_add) void simpan(){
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Loading ...");
        loading.show();

        String id_pembelian = txtFaktur.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
        Call<Value> call = api.simpan_pembelian(id_pembelian);
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
                    Toast.makeText(pembelian.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(pembelian.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                Log.d("Penjualan Error", String.valueOf(t));
                t.printStackTrace();
            }
        }) ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembelian);
        ButterKnife.bind(this);
        mContext = this;
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        initSpinnerSup();
        initSpinnerBrg();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Pembelian");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewAdapter = new RecyclerViewAdapter_Pembelian(this,results);
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
        String id_pembelian = txtFaktur.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
        Call<Value> call = api.getdetail(id_pembelian);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("1")) {
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter_Pembelian(pembelian.this, results);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }

    public void initSpinnerSup(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertSupplierAPI api = retrofit.create(insertSupplierAPI.class);
        Call<Value> call = api.getSupplier();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Result> semuasup = response.body().getResult();
                    listspinnerSup = new ArrayList<String>();
                    listspinnerSupId = new ArrayList<String>();
                    //List<Integer> listspinner2 = new ArrayList<Integer>();
                    for (int i = 0; i <semuasup.size(); i++) {
                        listspinnerSupId.add(semuasup.get(i).getId_supplier());
                        listspinnerSup.add(semuasup.get(i).getNama_supplier());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listspinnerSup);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSup.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data supplier", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerSup.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedsup = listspinnerSup.get(position);
                selected_sup = listspinnerSupId.get(position);
                Toast.makeText(mContext, "Nama supplier adalah " + selectedsup+", dengan ID " + selected_sup, Toast.LENGTH_SHORT).show();
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