package com.example.toko;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toko.API.insertBarangAPI;
import com.example.toko.API.insertKatAPI;
import com.example.toko.API.insertSatuanBarangAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tambah_barang extends AppCompatActivity {

    public static final String URL2 = "http://192.168.43.66/simplecrud/satuan_barang/";
    public static final String URL1 = "http://192.168.43.66/simplecrud/kategori/";
    public static final String URL = "http://192.168.43.66/simplecrud/data_barang/";

    String selected_sat;
    String selected_kat;
    String selectedkat;
    String selectedsat;
    Context mContext;
    ProgressDialog loading;

    List<String> listspinnerSat = new ArrayList<String>();
    List<String> listspinnerSatId = new ArrayList<String>();
    List<String> listspinnerKat = new ArrayList<String>();
    List<String> listspinnerKatId = new ArrayList<String>();
    @BindView(R.id.spinnerSat) Spinner spinnerSat;
    @BindView(R.id.spinnerKat) Spinner spinnerKat;
    @BindView(R.id.textnama_barang) EditText textnama_barang;
    @BindView(R.id.textqty_barang) EditText textqty_barang;
    @BindView(R.id.idbrg) EditText idsat;
    @OnClick(R.id.brgsimpan) void insert_barang(){
        String nama_barang = textnama_barang.getText().toString();
        String qtybrg = textqty_barang.getText().toString();
        if (TextUtils.isEmpty(nama_barang)) {
            textnama_barang.setError("Nama barang tidak boleh kosong");
        } else if(TextUtils.isEmpty(qtybrg)) {
            textqty_barang.setError("Qty barang tidak boleh kosong");
        } else{
            loading = new ProgressDialog(this);
            loading.setCancelable(true);
            loading.setMessage("Loading ...");
            loading.show();
            int qty_barang = Integer.parseInt(qtybrg);
            int id_kategori = Integer.parseInt(selected_kat);
            int id_satuan = Integer.parseInt(selected_sat);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertBarangAPI api = retrofit.create(insertBarangAPI.class);
        Call<Value> call = api.barang(nama_barang,id_kategori ,qty_barang, id_satuan );
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(tambah_barang.this, message, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(tambah_barang.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(tambah_barang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        }) ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_barang);
        ButterKnife.bind(this);
        mContext = this;
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        initSpinnerSat();
        initSpinnerKat();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Barang");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initSpinnerSat(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertSatuanBarangAPI api = retrofit.create(insertSatuanBarangAPI.class);
        Call<Value> call = api.getsatuanbarang();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Result> semuasatuan = response.body().getResult();
                    listspinnerSat = new ArrayList<String>();
                    listspinnerSatId = new ArrayList<String>();
                   //List<Integer> listspinner2 = new ArrayList<Integer>();
                    for (int i = 0; i <semuasatuan.size(); i++) {
                        listspinnerSatId.add(semuasatuan.get(i).getId_satuan());
                        listspinnerSat.add(semuasatuan.get(i).getNama_satuan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listspinnerSat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSat.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data satuan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerSat.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedsat = listspinnerSat.get(position);
                selected_sat = listspinnerSatId.get(position);
                Toast.makeText(mContext, "Satuan Barang adalah " + selectedsat+", dengan ID " + selected_sat, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerKat() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        insertKatAPI api = retrofit.create(insertKatAPI.class);
        Call<Value> call = api.getkategori();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Result> semuakate = response.body().getResult();
                    listspinnerKat = new ArrayList<String>();
                    listspinnerKatId = new ArrayList<String>();
                    for (int i = 0; i < semuakate.size(); i++) {
                        listspinnerKatId.add(semuakate.get(i).getId_kategori());
                        listspinnerKat.add(semuakate.get(i).getNama_kategori());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listspinnerKat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKat.setAdapter(adapter);
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data kategori", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerKat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedkat = listspinnerKat.get(position);
                selected_kat = listspinnerKatId.get(position);
                Toast.makeText(mContext, "Kategori adalah " + selectedkat+", dengan ID " + selected_kat, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
