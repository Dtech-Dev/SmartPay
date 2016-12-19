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

public class TagihanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataTagihan> data = Collections.emptyList();
    DataTagihan current;
    int currentPos = 0;

    public TagihanAdapter(Context context, List<DataTagihan> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_item_tagihan, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        DataTagihan current = data.get(position);
        myHolder.detail.setText(current.detailTagihan);
        myHolder.idtagihan.setText(current.idTagihan);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView detail, idtagihan;
        Button hapus, bayar;
        String id_tagihan;

        public MyHolder(View itemView) {
            super(itemView);

            detail = (TextView) itemView.findViewById(R.id.txtItemTagihan);
            idtagihan = (TextView) itemView.findViewById(R.id.txtItemId);

            hapus = (Button) itemView.findViewById(R.id.btnItemHapus);
            hapus.setOnClickListener(this);
            bayar = (Button) itemView.findViewById(R.id.btnItemBayar);
            bayar.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnItemHapus:
                    id_tagihan = idtagihan.getText().toString();
                    Toast.makeText(v.getContext(), "btn hapus clicked", Toast.LENGTH_SHORT).show();
                    asyncHapus(id_tagihan);
                    break;
                case R.id.btnItemBayar:
                    id_tagihan = idtagihan.getText().toString();
                    Toast.makeText(v.getContext(), "btn bayar clicked with id tagihan = "+id_tagihan, Toast.LENGTH_SHORT).show();
                    asyncBayar(id_tagihan);
                    break;
            }

        }

        private void asyncHapus(final String id_hapustagihan) {
            class HapusAsync extends AsyncTask<Void, Void, String> {
                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> paramsProvider = new HashMap<>();
                    paramsProvider.put(Config.POST_DELETE, "hapus");
                    paramsProvider.put(Config.TAG_ID_TAGIHAN, id_hapustagihan);



                    RequestHandler reqHandler = new RequestHandler();
                    String res = reqHandler.sendPostRequest(Config.URL_SHOW_TAGIHAN, paramsProvider);

                    return res;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    detail.setText("On Proccess Deleting");
                    bayar.setEnabled(false);
                    hapus.setEnabled(false);
                }
            }
            HapusAsync hapusAsync = new HapusAsync();
            hapusAsync.execute();
        }

        private void asyncBayar(final String id_tagihan) {

            class BayarAsync extends AsyncTask<Void, Void, String> {
                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> paramsProvider = new HashMap<>();
                    paramsProvider.put(Config.POST_BAYAR, "bayar");
                    paramsProvider.put(Config.TAG_ID_TAGIHAN, id_tagihan);



                    RequestHandler reqHandler = new RequestHandler();
                    String res = reqHandler.sendPostRequest(Config.URL_SHOW_TAGIHAN, paramsProvider);

                    return res;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    detail.setText("On Proccess");
                    bayar.setEnabled(false);
                    hapus.setEnabled(false);
                }
            }

            BayarAsync bayarAsync = new BayarAsync();
            bayarAsync.execute();
        }
    }
}