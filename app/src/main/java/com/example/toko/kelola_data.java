package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class kelola_data extends AppCompatActivity {

    public void databrg_btn (View view){
        startActivity(new Intent(kelola_data.this, data_barang.class));
    }
    public void datakat_btn(View view){
        startActivity(new Intent(kelola_data.this, data_kategori.class));
    }
    public void data_satuanbtn(View view){
        startActivity(new Intent(kelola_data.this, data_satuan_barang.class));
    }
    public void data_pelangganbtn(View view){
        startActivity(new Intent(kelola_data.this, data_pelanggan.class));
    }public void data_supplierbtn(View view){
        startActivity(new Intent(kelola_data.this, data_supplier.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kelola_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kelola Data");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
