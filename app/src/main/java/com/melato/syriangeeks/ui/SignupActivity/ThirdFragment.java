package com.melato.syriangeeks.ui.SignupActivity;

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
import com.melato.syriangeeks.databinding.FragmentMainBinding;
import com.melato.syriangeeks.databinding.FragmentThirdBinding;
import com.melato.syriangeeks.model.CountriesModel;

import java.util.Objects;


public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;
    private SignupViewModel signupViewModel;

    public ThirdFragment() {
        // Required empty public constructor
    }

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupViewModel = ((SignupActivity) requireActivity()).getSignupViewModel();
        ArrayAdapter<CountriesModel> adapter1 = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                signupViewModel.getCountriesModels(requireContext())
        );

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.conutery.setAdapter(adapter1);

        binding.conutery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountriesModel selectedCountry = (CountriesModel) parent.getItemAtPosition(position);
                String countryName = selectedCountry.name;
                ArrayAdapter<CountriesModel.State> adapter1 = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        signupViewModel.getCountriesCityModels(requireContext(),position)
                );

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.city.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.state_options,
                R.layout.simple_spinner_item
        );
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.stateSpinner.setAdapter(adapter4);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false);
        return binding.getRoot();
    }
}