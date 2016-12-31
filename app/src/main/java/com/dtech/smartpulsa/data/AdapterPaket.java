package com.dtech.smartpulsa.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtech.smartpulsa.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by aris on 31/12/16.
 */

public class AdapterPaket extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private LayoutInflater inflater;
    List<DataPaket> data = Collections.emptyList();
    DataPaket current;
    int currentPos = 0;

    public AdapterPaket(Context context, List<DataPaket> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_item_paketdata, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        DataPaket current = data.get(position);
        myHolder.keterangan.setText(current.keterangan);
        myHolder.kode.setText(current.kode);
        myHolder.harga.setText(current.harga);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView keterangan, kode, harga;

        public MyHolder(View itemView) {
            super(itemView);

            keterangan = (TextView) itemView.findViewById(R.id.detailketeranganPaket);
            kode = (TextView) itemView.findViewById(R.id.detailkodePaket);
            harga = (TextView) itemView.findViewById(R.id.detailhargaPaket);

        }
    }
}
