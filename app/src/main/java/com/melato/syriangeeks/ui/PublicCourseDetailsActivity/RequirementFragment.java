package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentRequirementBinding;
import com.melato.syriangeeks.databinding.FragmentTrainingMethodologyBinding;
import com.melato.syriangeeks.model.CourseDetalsModel;


public class RequirementFragment extends Fragment {
    private FragmentRequirementBinding binding;
    private CourseDetalsModel model;

    public RequirementFragment() {
        // Required empty public constructor
    }

    public RequirementFragment(CourseDetalsModel model) {
        this.model = model;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textContenet.setText(model.requirements);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requirement, container, false);
        return binding.getRoot();
    }
}