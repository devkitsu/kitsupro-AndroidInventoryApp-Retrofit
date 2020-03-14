package com.example.toko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.edt_kode) EditText kode;
    @OnClick(R.id.btn_login) void Login(){
        String kodelogin = "jayamakmursentosa";
        String kode_login = kode.getText().toString();
        if (kode_login.isEmpty()) {
            kode.setError("Tidak boleh kosong");
        } else if (kode_login.equals(kodelogin)){
            Intent mainIntent = new Intent(Login.this, home.class);
            startActivity(mainIntent);
            Toast.makeText(Login.this, "Login Berhasil...", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(Login.this, "Gagal Login, Kode Salah...", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
    }

}
