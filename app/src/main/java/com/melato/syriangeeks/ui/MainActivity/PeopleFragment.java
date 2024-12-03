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
import com.melato.syriangeeks.databinding.FragmentPeopleBinding;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements View.OnClickListener {

    private FragmentPeopleBinding binding;
    private MainViewModel mainViewModel;
    private PeopleRecyclerViewAdapter peopleRecyclerViewAdapter;

    public PeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.listRecyclerView.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
                if (!working.isRunning())
                    Toast.makeText(requireActivity(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        peopleRecyclerViewAdapter = new PeopleRecyclerViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(peopleRecyclerViewAdapter);

        mainViewModel.questionliveData.observe(getViewLifecycleOwner(), datumList -> {
            List<QuestionModel.Datum> datum = new ArrayList<>();
            for (QuestionModel item : datumList) {
                datum.addAll(item.questions.data);
            }
            peopleRecyclerViewAdapter.setDatumList(datum);
        });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarBack.setOnClickListener(this);
        if (mainViewModel.questionliveData.getValue() == null)
            mainViewModel.getQuestions(requireActivity());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getQuestions(requireActivity());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);
        return binding.getRoot();
    }


}