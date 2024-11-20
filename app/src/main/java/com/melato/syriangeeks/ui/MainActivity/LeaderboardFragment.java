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
import com.melato.syriangeeks.databinding.FragmentLeaderboardBinding;
import com.melato.syriangeeks.model.LeaderBoardModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LeaderboardFragment extends Fragment implements View.OnClickListener {

    private FragmentLeaderboardBinding binding;
    private MainViewModel mainViewModel;
    private LeaderBoradRecyclerViewAdapter leaderBoradRecyclerViewAdapter;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leaderboard, container, false);
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

        mainViewModel.workingLoadMore.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.moreprogress.setVisibility(working.isProgressing());
            }
        });

        binding.listRecyclerView.setHasFixedSize(true);
        binding.listRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        leaderBoradRecyclerViewAdapter = new LeaderBoradRecyclerViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(leaderBoradRecyclerViewAdapter);

        mainViewModel.leaderBoardModelLiveData.observe(getViewLifecycleOwner(), datumList -> {
            List<LeaderBoardModel.Datum> datum = new ArrayList<>();
            for (LeaderBoardModel item : datumList) {
                datum.addAll(item.students.data);
            }
            leaderBoradRecyclerViewAdapter.setDatumList(datum);
        });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarSearch.setOnClickListener(this);
        if (mainViewModel.leaderBoardModelLiveData.getValue() == null)
            mainViewModel.getLeaderBoard(requireActivity());

        binding.listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.listRecyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int currentItem = linearLayoutManager.getChildCount();
                int totalItem = linearLayoutManager.getItemCount();
                int secrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (!Objects.requireNonNull(mainViewModel.workingLoadMore.getValue()).isRunning() && currentItem + secrollOutItem == totalItem) {
                    mainViewModel.getLeaderBoardMore(requireContext());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getLeaderBoard(requireActivity());
        } else if (v.getId() == R.id.toolbar_search) {
            //mainViewModel.getCertificate(requireActivity(), binding.editTextSerch.getText().toString());
        }
    }

}