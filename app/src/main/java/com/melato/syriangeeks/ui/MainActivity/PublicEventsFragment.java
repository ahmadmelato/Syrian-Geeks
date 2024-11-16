package com.melato.syriangeeks.ui.MainActivity;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentPublicEventsBinding;
import com.melato.syriangeeks.model.EventModel;

public class PublicEventsFragment extends Fragment implements View.OnClickListener {

    private FragmentPublicEventsBinding binding;
    private MainViewModel viewModel;
    private PublicEventViewAdapter publicEventViewAdapter;


    public PublicEventsFragment() {
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
        publicEventViewAdapter = new PublicEventViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(publicEventViewAdapter);

        viewModel.eventModelLiveData.observe(getViewLifecycleOwner(), items -> {
            publicEventViewAdapter.setEventList(items);
        });


        publicEventViewAdapter.SetOnItemClickListener(position -> {
            EventModel.Item item = publicEventViewAdapter.itemList.get(position);
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openPublicEventsDetailsFragment(new Gson().toJson(item));
        });

        binding.nointernet.setOnClickListener(this);

        if (viewModel.eventModelLiveData.getValue() == null)
            viewModel.getEvents(requireContext());

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getEvents(requireContext());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_events, container, false);
        return binding.getRoot();
    }
}