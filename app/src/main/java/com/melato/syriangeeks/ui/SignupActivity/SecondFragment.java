package com.melato.syriangeeks.ui.SignupActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentFirstBinding;
import com.melato.syriangeeks.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.ed_options,
                R.layout.simple_spinner_item
        );
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edSpinner.setAdapter(adapter0);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.work_options,
                R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.workSpinner.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.yesno_options,
                R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freelancerspinner.setAdapter(adapter2);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        return binding.getRoot();
    }
}