package com.melato.syriangeeks.ui.SignupActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    public FirstFragment() {
        // Required empty public constructor
    }


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_options,
                R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderspinner.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.yesno_options,
                R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yesnospinner.setAdapter(adapter2);

        binding.brithday.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        return binding.getRoot();
    }
}