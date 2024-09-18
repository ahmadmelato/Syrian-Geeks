package com.melato.syriangeeks.ui.SignupActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;

public class ForthFragment extends Fragment {

    public ForthFragment() {
        // Required empty public constructor
    }

    public static ForthFragment newInstance() {
        ForthFragment fragment = new ForthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forth, container, false);
    }
}