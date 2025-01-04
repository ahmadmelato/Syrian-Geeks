package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentAboutBinding;
import com.melato.syriangeeks.model.ProfileModel;

public class AboutFragment extends Fragment {

    private static final String ARG_PARAM1 = "data";
    private String mParam1;
    private FragmentAboutBinding binding;
    private ProfileModel profileModel;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1) {
        AboutFragment fragment = new AboutFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileModel = new Gson().fromJson(mParam1, ProfileModel.class);
        if (profileModel != null) {
            binding.fullName.setText((profileModel.name_ar != null ? profileModel.name_ar : "") + " - " + profileModel.name);
            binding.genderBirthday.setText(profileModel.getPublicGender() + " - " + profileModel.date_of_birth + " - " + (profileModel.getPublicNationality() != null ? profileModel.getPublicNationality() : ""));
            binding.edctionsWorks.setText((profileModel.getPublicEducation() != null ? profileModel.getPublicEducation() : "") + " - " + (profileModel.getPublicWork_field() != null ? profileModel.getPublicWork_field() : ""));
            binding.experniceWay.setVisibility(View.GONE);
            if(!profileModel.getPublicDesignation().isEmpty()) {
                binding.experniceWay.setText(profileModel.getPublicDesignation() + " ");
                binding.experniceWay.setVisibility(View.VISIBLE);
            }
            binding.phone.setText((profileModel.phone_dial != null ? profileModel.phone_dial : "")+ " "+ (profileModel.mobile != null ? profileModel.mobile : ""));
            binding.aboutMe.setText(profileModel.about_me != null ? profileModel.about_me : "");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        return binding.getRoot();
    }
}