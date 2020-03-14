package com.example.toko.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.API.insertPembelianAPI;
import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.Value;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.toko.pembelian.URL;

public class RecyclerViewAdapter_Pembelian extends RecyclerView.Adapter<RecyclerViewAdapter_Pembelian.ViewHolder> {
    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_Pembelian(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter__pembelian, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_Pembelian.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Pembelian.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txtid_pembelian.setText(result.getId_pembelian());
        holder.txtkode_barang.setText(result.getId_barang());
        holder.txtnama_barang.setText(result.getNama_barang());
        holder.txtqty_barang.setText(result.getQty_pembelian());
        holder.txtsatuan_barang.setText(result.getNama_satuan());
        holder.txtharga_satuan.setText(result.getHarga_satuan_pembelian());
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
        @BindView(R.id.txid_pembelian) TextView txtid_pembelian;
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
            String id_pembelian = txtid_pembelian.getText().toString();
            String qtybeli = txtqty_barang.getText().toString();
            int qty_pembelian = Integer.parseInt(qtybeli);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            insertPembelianAPI api = retrofit.create(insertPembelianAPI.class);
            Call<Value> call = api.hapus(id_barang,id_pembelian,qty_pembelian);
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
