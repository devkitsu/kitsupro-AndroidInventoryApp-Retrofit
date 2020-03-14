package com.example.toko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.R;
import com.example.toko.Result;
import com.example.toko.update_kategori;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Stok extends RecyclerView.Adapter<RecyclerViewAdapter_Stok.ViewHolder>{


    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter_Stok(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewadapter__stok, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.textViewNamaBrg.setText(result.getNama_barang());
        holder.textViewQty.setText(result.getQty_barang());
        holder.textViewSatuan.setText(result.getNama_satuan());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textNamaBrg)
        TextView textViewNamaBrg;
        @BindView(R.id.textqty_barang)
        TextView textViewQty;
        @BindView(R.id.textNamaSatuan)
        TextView textViewSatuan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
