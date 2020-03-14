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
import com.example.toko.update_satuan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter_Satuan extends RecyclerView.Adapter<RecyclerViewAdapter_Satuan.ViewHolder> {

    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter_Satuan(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewsatuanbarang, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewNamaSat.setText(result.getNama_satuan());
        holder.textViewIdSat.setText(result.getId_satuan());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.textNamaSatuan)
        TextView textViewNamaSat;
        @BindView(R.id.textIdSat)
        TextView textViewIdSat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String id = textViewIdSat.getText().toString();
            String nama = textViewNamaSat.getText().toString();

            Intent i = new Intent(context, update_satuan.class);
            i.putExtra("id_satuan", id);
            i.putExtra("nama_satuan", nama);
            context.startActivity(i);
        }
    }
}
