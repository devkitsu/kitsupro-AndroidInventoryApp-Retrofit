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
import com.example.toko.update_pelanggan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Pelanggan extends RecyclerView.Adapter<RecyclerViewAdapter_Pelanggan.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter_Pelanggan(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewpelanggan, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.textViewIdPel.setText(result.getId_pelanggan());
        holder.textViewNamaPelanggan.setText(result.getNama_pelanggan());
        holder.textViewTeleponPelanggan.setText(result.getTelepon_pelanggan());
        holder.textViewAlamatPelanggan.setText(result.getAlamat_pelanggan());
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.textIdPel)
        TextView textViewIdPel;
        @BindView(R.id.textNamaPelanggan)
        TextView textViewNamaPelanggan;
        @BindView(R.id.textTlpPelanggan)
        TextView textViewTeleponPelanggan;
        @BindView(R.id.textAlamatPelanggan)
        TextView textViewAlamatPelanggan;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = textViewIdPel.getText().toString();
            String nama = textViewNamaPelanggan.getText().toString();
            String tlp = textViewTeleponPelanggan.getText().toString();
            String alamat = textViewAlamatPelanggan.getText().toString();

            Intent i = new Intent(context, update_pelanggan.class);
            i.putExtra("id_pelanggan", id);
            i.putExtra("nama_pelanggan", nama);
            i.putExtra("telepon_pelanggan", tlp);
            i.putExtra("alamat_pelanggan", alamat);
            context.startActivity(i);
        }
    }
}
