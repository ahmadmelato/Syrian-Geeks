package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

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
import com.melato.syriangeeks.databinding.FragmentOverviewBinding;
import com.melato.syriangeeks.model.CourseDetalsModel;
import com.melato.syriangeeks.ui.MainActivity.PublicEventsDetailsFragment;


public class OverviewFragment extends Fragment {

    private static final String ARG_PARAM1 = "model";
    private FragmentOverviewBinding binding;
    private CourseDetalsModel model;


    public OverviewFragment() {
        // Required empty public constructor
    }


    public static OverviewFragment newInstance(String param1) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = new Gson().fromJson(getArguments().getString(ARG_PARAM1),CourseDetalsModel.class) ;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(model != null) {
            binding.discption.setText(model.getContent());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false);
        return binding.getRoot();
    }
}