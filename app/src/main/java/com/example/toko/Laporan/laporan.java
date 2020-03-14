package com.example.toko.Laporan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.toko.R;

public class laporan extends AppCompatActivity {
    public void laporanpembelian_btn (View view) {
        startActivity(new Intent(laporan.this, laporan_pembelian.class));
    }
    public void laporanpenjualan_btn (View view) {
        startActivity(new Intent(laporan.this, laporan_penjualan.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Laporan");
        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
