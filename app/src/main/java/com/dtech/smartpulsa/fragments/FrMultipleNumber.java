package com.dtech.smartpulsa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtech.smartpulsa.R;

/**
 * Created by lenovo on 30/11/2016.
 */

public class FrMultipleNumber extends Fragment implements View.OnClickListener{

    View view;
    TextView txt7;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fr_multiple_number, container, false);

        initUI();
        
        txt7 = (TextView) view.findViewById(R.id.textView7);
        txt7.setText("Boo");
        return view;


    }

    private void initUI() {
    }

    @Override
    public void onClick(View v) {
        
    }
}
