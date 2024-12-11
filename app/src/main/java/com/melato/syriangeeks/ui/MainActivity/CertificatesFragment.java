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
import com.melato.syriangeeks.databinding.FragmentCertificatesBinding;

public class CertificatesFragment extends Fragment implements View.OnClickListener {

    private FragmentCertificatesBinding binding;
    private MainViewModel mainViewModel;
    private CertificateRecyclerViewAdapter certificateRecyclerViewAdapter;

    public CertificatesFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_certificates, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        certificateRecyclerViewAdapter = new CertificateRecyclerViewAdapter(requireContext());
        binding.listRecyclerView.setAdapter(certificateRecyclerViewAdapter);

        mainViewModel.certificateModelLiveData.observe(getViewLifecycleOwner(), datumList -> {
            certificateRecyclerViewAdapter.setDatumList(datumList);
        });

        binding.nointernet.setOnClickListener(this);
        binding.toolbarSearch.setOnClickListener(this);
        binding.toolbarBack.setOnClickListener(this);
        if (mainViewModel.certificateModelLiveData.getValue() == null)
            mainViewModel.getCertificate(requireActivity());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else if (v.getId() == R.id.nointernet) {
            mainViewModel.getCertificate(requireActivity());
        } else if (v.getId() == R.id.toolbar_search) {
            //mainViewModel.getCertificate(requireActivity(), binding.editTextSerch.getText().toString());
        }
    }
}