package com.melato.syriangeeks.ui.AboutUsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;


public class ContactUsFragment extends Fragment {



    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }
}