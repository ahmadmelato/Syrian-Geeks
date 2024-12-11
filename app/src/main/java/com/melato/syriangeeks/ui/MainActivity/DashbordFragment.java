package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentDashbordBinding;

public class DashbordFragment extends Fragment implements View.OnClickListener {

    private FragmentDashbordBinding binding;
    private MainViewModel mainViewModel;

    public DashbordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashbord, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbarBack.setOnClickListener(this);
        binding.btuMycourse.setOnClickListener(this);
        binding.btuActive.setOnClickListener(this);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
//                binding.mainprogress.setVisibility(working.isProgressing());
//                binding.main.setVisibility(working.isSuccessfulView());
//                binding.nointernet.setVisibility(working.isNotSuccessfulView());

            }
        });

        MainViewModel.userLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null) {
                binding.toolbarName.setText("أهلا " + userModel.getUser().getName());
            } else {
                binding.toolbarName.setText("مرحباً بك");
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        }else if(v.getId() == R.id.btuMycourse){
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openMyCoursesFragment();
        }else if(v.getId() == R.id.btuActive){
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openCourseActivitiesFragment();
        }
    }
}