package com.melato.syriangeeks.ui.AboutUsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;

public class SyrianGeeksFragment extends Fragment {

    public SyrianGeeksFragment() {
        // Required empty public constructor
    }

    public static SyrianGeeksFragment newInstance() {
        SyrianGeeksFragment fragment = new SyrianGeeksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_syrian_geeks, container, false);
    }
}