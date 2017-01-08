package com.dtech.smartpulsa.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dtech.smartpulsa.Configuration.Config;
import com.dtech.smartpulsa.Configuration.RequestHandler;
import com.dtech.smartpulsa.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aris on 16/12/16.
 */

public class AdapterKota extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    //List<DataInbox> data = Collections.emptyList();
    String[] dataKode;
    String[] dataKota;
    DataInbox current;
    int currentPos = 0;

    public AdapterKota(Context context, String[] dataKode, String[] dataKota) {
        this.context = context;
        this.dataKode = dataKode;
        this.dataKota = dataKota;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_item_kota, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.kode.setText(dataKode[position]);
        myHolder.kota.setText(dataKota[position]);
        /*DataInbox current = data.get(position);
        myHolder.detail.setText(current.mes);
        myHolder.idtagihan.setText(current.ket);
        myHolder.ketag.setText(current.ketag);
        myHolder.jenis.setText(current.jenis);*/

    }

    @Override
    public int getItemCount() {
        return dataKode.length;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView kode, kota;
        Button hapus, bayar;
        String id_tagihan;

        public MyHolder(View itemView) {
            super(itemView);

            kode = (TextView) itemView.findViewById(R.id.kode);
            kota = (TextView) itemView.findViewById(R.id.kota);

        }

        @Override
        public void onClick(View v) {


        }
    }
}
