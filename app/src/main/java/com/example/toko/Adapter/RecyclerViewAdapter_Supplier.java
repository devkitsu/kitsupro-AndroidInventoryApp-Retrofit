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
import com.example.toko.update_supplier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Supplier extends RecyclerView.Adapter<RecyclerViewAdapter_Supplier.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter_Supplier(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewsupplier, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.textViewIdSup.setText(result.getId_supplier());
        holder.textViewNamaSupplier.setText(result.getNama_supplier());
        holder.textViewTeleponSupplier.setText(result.getTelepon_supplier());
        holder.textViewAlamatSupplier.setText(result.getAlamat_supplier());
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.textIdSup)
        TextView textViewIdSup;
        @BindView(R.id.textNamaSupplier)
        TextView textViewNamaSupplier;
        @BindView(R.id.textAlamatSupplier)
        TextView textViewAlamatSupplier;
        @BindView(R.id.textTlpSupplier)
        TextView textViewTeleponSupplier;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = textViewIdSup.getText().toString();
            String nama = textViewNamaSupplier.getText().toString();
            String tlp = textViewTeleponSupplier.getText().toString();
            String alamat = textViewAlamatSupplier.getText().toString();

            Intent i = new Intent(context, update_supplier.class);
            i.putExtra("id_supplier", id);
            i.putExtra("nama_supplier", nama);
            i.putExtra("telepon_supplier", tlp);
            i.putExtra("alamat_supplier", alamat);
            context.startActivity(i);
        }
    }
}