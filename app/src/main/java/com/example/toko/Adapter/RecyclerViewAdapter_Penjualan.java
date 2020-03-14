package com.example.toko.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toko.API.insertPenjualanAPI;
import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.Value;
import com.example.toko.penjualan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.toko.penjualan.URL;

public class RecyclerViewAdapter_Penjualan extends RecyclerView.Adapter<RecyclerViewAdapter_Penjualan.ViewHolder> {
    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_Penjualan(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter__penjualan, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_Penjualan.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Penjualan.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txtid_penjualan.setText(result.getId_penjualan());
        holder.txtkode_barang.setText(result.getId_barang());
        holder.txtnama_barang.setText(result.getNama_barang());
        holder.txtqty_barang.setText(result.getQty_penjualan());
        holder.txtsatuan_barang.setText(result.getNama_satuan());
        holder.txtharga_satuan.setText(result.getHarga_satuan_penjualan());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void clear() {
        int size = results.size();
        results.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.txid_penjualan) TextView txtid_penjualan;
        @BindView(R.id.txtkode_barang) TextView txtkode_barang;
        @BindView(R.id.txtnama_barang) TextView txtnama_barang;
        @BindView(R.id.txtqty) TextView txtqty_barang;
        @BindView(R.id.txtsatuan) TextView txtsatuan_barang;
        @BindView(R.id.txtharga) TextView txtharga_satuan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Data dihapus dari keranjang");
            alertDialogBuilder.setIcon(R.drawable.confirm).setCancelable(false);
            alertDialogBuilder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
            String id_barang = txtkode_barang.getText().toString();
            String id_penjualan = txtid_penjualan.getText().toString();
            String qtyjual = txtqty_barang.getText().toString();
            int qty_penjualan = Integer.parseInt(qtyjual);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertPenjualanAPI api = retrofit.create(insertPenjualanAPI.class);
            Call<Value> call = api.hapus(id_barang,id_penjualan,qty_penjualan);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    if (value.equals("1")) {
                        Log.d("BERHASIL", "Berhasil");
                    } else {
                        Log.d("GAGAL", "Gagal");
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("HAPUS", String.valueOf(t));
                }
            });
                }
            });
            results.remove(getAdapterPosition());
            notifyItemRemoved(this.getLayoutPosition());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}

