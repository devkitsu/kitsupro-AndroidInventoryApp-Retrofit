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
import com.example.toko.update_kategori;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Kategori extends RecyclerView.Adapter<RecyclerViewAdapter_Kategori.ViewHolder>{

    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter_Kategori(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewkategori, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Result result = results.get(position);
        holder.textViewNamaKat.setText(result.getNama_kategori());
        holder.textViewIdKat.setText(result.getId_kategori());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.textNamaKat) TextView textViewNamaKat;
        @BindView(R.id.textIdKat) TextView textViewIdKat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = textViewIdKat.getText().toString();
            String nama = textViewNamaKat.getText().toString();

            Intent i = new Intent(context, update_kategori.class);
            i.putExtra("id_kategori", id);
            i.putExtra("nama_kategori", nama);
            context.startActivity(i);
        }
    }
}
