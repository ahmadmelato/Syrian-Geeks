package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentAboutBinding;
import com.melato.syriangeeks.databinding.FragmentExperienceBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainActivity.ExperienceRecyclerViewAdapter;

import java.util.ArrayList;


public class ExperienceFragment extends Fragment {

    private static final String ARG_PARAM1 = "data";
    private String mParam1;
    private FragmentExperienceBinding binding;

    public ExperienceFragment() {
        // Required empty public constructor
    }

    public static ExperienceFragment newInstance(String param1) {
        ExperienceFragment fragment = new ExperienceFragment();
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

        binding.experiencesList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        PublicExperienceRecyclerViewAdapter experienceRecyclerViewAdapter = new PublicExperienceRecyclerViewAdapter(requireContext());
        binding.experiencesList.setAdapter(experienceRecyclerViewAdapter);
        ProfileModel profileModel = new Gson().fromJson(mParam1, ProfileModel.class);
        if (profileModel != null) {
            experienceRecyclerViewAdapter.setDatumList(profileModel.experience != null ? profileModel.experience : new ArrayList<>());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false);
        return binding.getRoot();
    }
}