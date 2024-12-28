package com.melato.syriangeeks.ui.SignActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentFirstBinding;
import com.melato.syriangeeks.ui.MainActivity.DialogDatePicker;
import com.melato.syriangeeks.ui.SignupViewModel;

public class FirstFragment extends Fragment implements View.OnClickListener {

    private FragmentFirstBinding binding;
    private SignupViewModel signupViewModel;

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
        signupViewModel = ((SignupActivity) requireActivity()).getSignupViewModel();
        binding.setViewmodel(signupViewModel);
        binding.genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signupViewModel.gender.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.yesnospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signupViewModel.disability.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        binding.brithday.setOnClickListener(this);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.brithday){
            new DialogDatePicker(requireContext(),binding.brithday).show();
        }
    }
}