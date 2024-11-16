package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentCourseactivitiesBinding;

public class CourseActivitiesFragment extends Fragment implements View.OnClickListener {

    private FragmentCourseactivitiesBinding binding;
    private MainViewModel mainViewModel;
    private CourseActivityRecyclerViewAdapter courseActivityRecyclerViewAdapter;


    public CourseActivitiesFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courseactivities, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbarBack.setOnClickListener(this);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
                if (!working.isRunning())
                    Toast.makeText(requireActivity(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        courseActivityRecyclerViewAdapter = new CourseActivityRecyclerViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(courseActivityRecyclerViewAdapter);

        mainViewModel.courseActivitiesModelLiveData.observe(getViewLifecycleOwner(), datumList -> {
            courseActivityRecyclerViewAdapter.setDatumList(datumList);
        });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarSearch.setOnClickListener(this);
        if (mainViewModel.certificateModelLiveData.getValue() == null)
            mainViewModel.getCourseActivities(requireActivity());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getCourseActivities(requireActivity());
        } else if (v.getId() == R.id.toolbar_search) {
            //mainViewModel.getCourseActivities(requireActivity(), binding.editTextSerch.getText().toString());
        }
    }
}