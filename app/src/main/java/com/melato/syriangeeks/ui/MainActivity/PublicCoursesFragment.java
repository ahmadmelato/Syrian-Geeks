package com.melato.syriangeeks.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentPublicCoursesBinding;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.ui.PublicCourseDetailsActivity.PublicCourseDetailsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PublicCoursesFragment extends Fragment implements View.OnClickListener {

    private FragmentPublicCoursesBinding binding;
    private MainViewModel viewModel;
    private PublicCourseViewAdapter publicCourseViewAdapter;

    public PublicCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbarBack.setOnClickListener(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.listRecyclerView.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        viewModel.workingLoadMore.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.moreprogress.setVisibility(working.isProgressing());
            }
        });

        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        publicCourseViewAdapter = new PublicCourseViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(publicCourseViewAdapter);

        viewModel.courseModelLiveData.observe(getViewLifecycleOwner(), courseModels -> {
            List<CourseModel.Datum> datum = new ArrayList<>();
            for (CourseModel item : courseModels) {
                datum.addAll(item.data);
            }
            publicCourseViewAdapter.setCourseModels(datum);
        });

        publicCourseViewAdapter.SetOnItemClickListener(position -> {
            CourseModel.Datum item = publicCourseViewAdapter.CourseModels.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicCourseDetailsFragment("PUK",item.getId());
        });

        if (viewModel.courseModelLiveData.getValue() == null)
            viewModel.getCourses(requireContext(), "");


        binding.toolbarSearch.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);

        binding.listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.listRecyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int currentItem = linearLayoutManager.getChildCount();
                int totalItem = linearLayoutManager.getItemCount();
                int secrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (!Objects.requireNonNull(viewModel.workingLoadMore.getValue()).isRunning() && currentItem + secrollOutItem == totalItem) {
                    viewModel.getCoursesMore(requireContext(),"");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_courses, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getCourses(requireContext(), "");
        } else if (v.getId() == R.id.toolbar_search) {
            viewModel.getCourses(requireActivity(), binding.editText.getText().toString());
        }

    }


}