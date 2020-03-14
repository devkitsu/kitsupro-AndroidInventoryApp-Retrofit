package com.example.toko.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.R;
import com.example.toko.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_LaporanPenjualanDetail extends RecyclerView.Adapter<RecyclerViewAdapter_LaporanPenjualanDetail.ViewHolder> {
    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_LaporanPenjualanDetail(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter__laporan_penjualan_detail, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_LaporanPenjualanDetail.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_LaporanPenjualanDetail.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txt_barang.setText(result.getNama_barang());
        holder.txt_qty.setText(result.getQty_penjualan());
        holder.txt_harga.setText(result.getHarga_satuan_penjualan());
        holder.txt_total.setText(result.getTotal_harga_penjualan());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtnama_barang) TextView txt_barang;
        @BindView(R.id.txtqty) TextView txt_qty;
        @BindView(R.id.txtharga) TextView txt_harga;
        @BindView(R.id.txttotalharga) TextView txt_total;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
