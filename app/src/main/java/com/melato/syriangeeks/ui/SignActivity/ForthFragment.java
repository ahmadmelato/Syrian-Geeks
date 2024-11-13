package com.melato.syriangeeks.ui.SignActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentFirstBinding;
import com.melato.syriangeeks.databinding.FragmentForthBinding;
import com.melato.syriangeeks.ui.SignupViewModel;

public class ForthFragment extends Fragment {

    private FragmentForthBinding binding;
    private SignupViewModel signupViewModel;

    public ForthFragment() {
        // Required empty public constructor
    }

    public static ForthFragment newInstance() {
        return new ForthFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupViewModel = ((SignupActivity)requireActivity()).getSignupViewModel();
        binding.setViewmodel(signupViewModel);
        binding.ch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    signupViewModel.newsletter.set("1");
                else
                    signupViewModel.newsletter.set("");
            }
        });

        binding.ch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    signupViewModel.agree.set("programming");
                else
                    signupViewModel.agree.set("");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forth, container, false);
        return binding.getRoot();
    }
}