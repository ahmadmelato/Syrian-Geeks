package com.melato.syriangeeks.ui.AboutUsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentAboutUsBinding;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;
import com.melato.syriangeeks.ui.PublicCourseDetailsActivity.SliderMainAdapter;

public class AboutUsFragment extends Fragment implements View.OnClickListener {

    private FragmentAboutUsBinding binding;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SliderAboutUsAdapter sliderMainAdapter = new SliderAboutUsAdapter(requireActivity());
        binding.viewpager.setAdapter(sliderMainAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewpager, (tab, position) -> {
            tab.setText(sliderMainAdapter.getItem(position));
        }).attach();

        binding.toolbarBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        }
    }
}