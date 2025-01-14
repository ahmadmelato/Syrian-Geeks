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

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentCoursesBinding;
import com.melato.syriangeeks.model.MyCourseModel;
import com.melato.syriangeeks.ui.MainViewModel;

public class MyCoursesFragment extends Fragment implements View.OnClickListener {

    private FragmentCoursesBinding binding;
    private MainViewModel mainViewModel;
    private MyCourseViewAdapter myCourseViewAdapter;

    public MyCoursesFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_courses, container, false);
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
            }
        });

        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        myCourseViewAdapter = new MyCourseViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(myCourseViewAdapter);

        mainViewModel.myCourseModelLiveData.observe(getViewLifecycleOwner(), datumList -> {
            myCourseViewAdapter.setCourseModels(datumList);
        });

        myCourseViewAdapter.SetOnItemClickListener(position -> {
            MyCourseModel.Datum item = myCourseViewAdapter.CourseModels.get(position);
            if(item.approved == 1) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.openPublicCourseDetailsFragment("MY",item.course_id);
            }
        });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarSearch.setOnClickListener(this);
        if(mainViewModel.myCourseModelLiveData.getValue() == null)
            mainViewModel.getMyCourses(requireActivity(), "");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getMyCourses(requireActivity(), "");
        } else if (v.getId() == R.id.toolbar_search) {
            mainViewModel.getMyCourses(requireActivity(), binding.editTextSerch.getText().toString());
        }
    }
}