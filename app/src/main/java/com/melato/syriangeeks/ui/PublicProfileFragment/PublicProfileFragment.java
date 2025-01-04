package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentAboutUsBinding;
import com.melato.syriangeeks.databinding.FragmentPublicProfileBinding;
import com.melato.syriangeeks.ui.AboutUsFragment.SliderAboutUsAdapter;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;

public class PublicProfileFragment extends Fragment implements View.OnClickListener{


    private FragmentPublicProfileBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public PublicProfileFragment() {
        // Required empty public constructor
    }

    public static PublicProfileFragment newInstance(String param1) {
        PublicProfileFragment fragment = new PublicProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_profile, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SliderPublicProfileAdapter sliderMainAdapter = new SliderPublicProfileAdapter(requireActivity());
        binding.viewpager.setAdapter(sliderMainAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewpager, (tab, position) -> {
            tab.setText(sliderMainAdapter.getItem(position));
        }).attach();

        binding.toolbarBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        }
    }
}