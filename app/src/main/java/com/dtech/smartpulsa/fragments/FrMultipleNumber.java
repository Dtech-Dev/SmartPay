package com.dtech.smartpulsa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtech.smartpulsa.R;

/**
 * Created by lenovo on 30/11/2016.
 */

public class FrMultipleNumber extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_multiple_number, container, false);
    }
}
