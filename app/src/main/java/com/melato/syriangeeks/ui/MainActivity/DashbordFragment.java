package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentDashbordBinding;
import com.melato.syriangeeks.model.DashbordModel;
import com.melato.syriangeeks.ui.MainViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DashbordFragment extends Fragment implements View.OnClickListener {

    private FragmentDashbordBinding binding;
    private MainViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        MainViewModel.userLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null) {
                binding.toolbarName.setText("أهلا " + userModel.getUser().getName());
            } else {
                binding.toolbarName.setText("مرحباً بك");
            }
        });

        viewModel.dashbordModelLiveData.observe(getViewLifecycleOwner(), dashbordModel -> {
            if (dashbordModel != null) {
                binding.stdState.setText(dashbordModel.getState());
                DashbordModel.Course course = dashbordModel.getLastVisitCourse();
                if (course != null) {
                    binding.courseName.setText(course.title);
                    binding.HeaderView1.setVisibility(View.VISIBLE);
                    loadImage(course.image,binding.img);
                }else {
                    binding.HeaderView1.setVisibility(View.GONE);
                }
            }
        });

        binding.btuStart.setOnClickListener(this);
        binding.showMore1.setOnClickListener(this);
        binding.showMore2.setOnClickListener(this);

        if (viewModel.dashbordModelLiveData.getValue() == null)
            viewModel.getMyProfile(requireContext());

    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_my_course) // Optional: Placeholder while loading
                .error(R.drawable.img_my_course) // Optional: Image to show on error
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.btuMycourse) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openMyCoursesFragment();
        } else if (v.getId() == R.id.btuActive) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openCourseActivitiesFragment();
        }else if (v.getId() == R.id.btuStart){
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicCourseDetailsFragment("MY", Objects.requireNonNull(viewModel.dashbordModelLiveData.getValue()).getLastVisitCourse().id);
        }else if (v.getId() == R.id.show_more1 || v.getId() == R.id.show_more2){
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openMyCoursesFragment();
        }
    }
}