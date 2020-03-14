package com.example.toko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.update_barang;
import com.example.toko.update_kategori;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Barang extends RecyclerView.Adapter<RecyclerViewAdapter_Barang.ViewHolder> {
    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_Barang(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewbarang, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_Barang.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Barang.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txtkode_barang.setText(result.getId_barang());
        holder.txtnama_barang.setText(result.getNama_barang());
        holder.txtkategori_barang.setText(result.getNama_kategori());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.txtkode_barang) TextView txtkode_barang;
        @BindView(R.id.txtnama_barang) TextView txtnama_barang;
        @BindView(R.id.txtkat_barang) TextView txtkategori_barang;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = txtkode_barang.getText().toString();
            String nama = txtnama_barang.getText().toString();
            String kat = txtkategori_barang.getText().toString();

            Intent i = new Intent(context, update_barang.class);
            i.putExtra("id_barang", id);
            i.putExtra("nama_barang", nama);
            i.putExtra("id_kategori", kat);
            context.startActivity(i);
        }
    }
}
