package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

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

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentTrainingMethodologyBinding;
import com.melato.syriangeeks.model.CourseDetalsModel;


public class TrainingMethodologyFragment extends Fragment {

    private FragmentTrainingMethodologyBinding binding;
    private CourseDetalsModel model;
    private SectionRecyclerViewAdapter sectionRecyclerViewAdapter;

    public TrainingMethodologyFragment() {
        // Required empty public constructor
    }

    public TrainingMethodologyFragment(CourseDetalsModel model) {
        this.model = model;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.lessianList.setHasFixedSize(true);
        binding.lessianList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        sectionRecyclerViewAdapter = new SectionRecyclerViewAdapter(requireContext());
        binding.lessianList.setAdapter(sectionRecyclerViewAdapter);
        sectionRecyclerViewAdapter.setSectionList(model.sections);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_training_methodology, container, false);
        return binding.getRoot();
    }
}