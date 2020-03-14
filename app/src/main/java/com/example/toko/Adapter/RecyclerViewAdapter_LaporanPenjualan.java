package com.example.toko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toko.Laporan.laporan_penjualan_detail;
import com.example.toko.R;
import com.example.toko.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_LaporanPenjualan extends RecyclerView.Adapter<RecyclerViewAdapter_LaporanPenjualan.ViewHolder> {

    private Context context;
    private List<Result> results;


    public RecyclerViewAdapter_LaporanPenjualan(Context context, List<Result> results) {
        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter__laporan_penjualan, parent, false);
        ViewHolder holder = new RecyclerViewAdapter_LaporanPenjualan.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_LaporanPenjualan.ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.txtId_penjualan.setText(result.getId_penjualan());
        holder.txt_pel.setText(result.getNama_pelanggan());
        holder.txt_tgl.setText(result.getTgl_penjualan());
        holder.txt_total.setText(result.getGrand_total_penjualan());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.txtid_penjualan) TextView txtId_penjualan;
        @BindView(R.id.txtnama_pel) TextView txt_pel;
        @BindView(R.id.txttgl_penjualan) TextView txt_tgl;
        @BindView(R.id.txt_total) TextView txt_total;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = txtId_penjualan.getText().toString();
            String tgl = txt_tgl.getText().toString();
            String nama = txt_pel.getText().toString();
            String total = txt_total.getText().toString();

            Intent i = new Intent(context, laporan_penjualan_detail.class);
            i.putExtra("id_penjualan", id);
            i.putExtra("nama_pelanggan", nama);
            i.putExtra("tgl_penjualan", tgl);
            i.putExtra("grand_total_penjualan", total);
            context.startActivity(i);
        }

    }
}
