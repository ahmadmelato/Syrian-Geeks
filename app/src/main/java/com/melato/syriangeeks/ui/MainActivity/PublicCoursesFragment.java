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
import com.melato.syriangeeks.ui.PublicCourseDetailsActivity.PublicCourseDetailsActivity;

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
        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        publicCourseViewAdapter = new PublicCourseViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(publicCourseViewAdapter);

        viewModel.courseModelLiveData.observe(getViewLifecycleOwner(), courseModels -> {
            publicCourseViewAdapter.setCourseModels(courseModels);
        });

        publicCourseViewAdapter.SetOnItemClickListener(position -> {
            CourseModel.Datum item = publicCourseViewAdapter.CourseModels.get(position);
            Intent intent = new Intent(requireContext(), PublicCourseDetailsActivity.class);
            intent.putExtra("state", "PUK");
            intent.putExtra("id", item.getId());
            startActivity(intent);
        });

        if (viewModel.courseModelLiveData.getValue() == null)
            viewModel.getCourses(requireContext(), "");


        binding.toolbarSearch.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);

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