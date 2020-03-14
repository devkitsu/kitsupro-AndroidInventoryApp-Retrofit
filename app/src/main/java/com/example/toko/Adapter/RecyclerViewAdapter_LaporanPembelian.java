package com.example.toko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.Laporan.laporan_pembelian_detail;
import com.example.toko.R;
import com.example.toko.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_LaporanPembelian extends RecyclerView.Adapter<RecyclerViewAdapter_LaporanPembelian.ViewHolder> {

    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_LaporanPembelian(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter__laporan_pembelian, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_LaporanPembelian.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_LaporanPembelian.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txtId_pembelian.setText(result.getId_pembelian());
        holder.txt_sup.setText(result.getNama_supplier());
        holder.txt_tgl.setText(result.getTgl_pembelian());
        holder.txt_total.setText(result.getGrand_total_pembelian());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.txtid_pembelian) TextView txtId_pembelian;
        @BindView(R.id.txtnama_sup) TextView txt_sup;
        @BindView(R.id.txttgl_pembelian) TextView txt_tgl;
        @BindView(R.id.txt_total) TextView txt_total;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = txtId_pembelian.getText().toString();
            String tgl = txt_tgl.getText().toString();
            String nama = txt_sup.getText().toString();
            String total = txt_total.getText().toString();

            Intent i = new Intent(context, laporan_pembelian_detail.class);
            i.putExtra("id_pembelian", id);
            i.putExtra("nama_supplier", nama);
            i.putExtra("tgl_pembelian", tgl);
            i.putExtra("grand_total_pembelian", total);
            context.startActivity(i);
        }

    }
}
