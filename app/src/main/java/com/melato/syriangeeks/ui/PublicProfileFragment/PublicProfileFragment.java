package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentAboutUsBinding;
import com.melato.syriangeeks.databinding.FragmentPublicProfileBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.AboutUsFragment.SliderAboutUsAdapter;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;
import com.melato.syriangeeks.ui.MainViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PublicProfileFragment extends Fragment implements View.OnClickListener {


    private FragmentPublicProfileBinding binding;
    private MainViewModel viewModel;
    private static final String ARG_PARAM1 = "user_id";
    private Integer user_id;

    public PublicProfileFragment() {
        // Required empty public constructor
    }

    public static PublicProfileFragment newInstance(Integer user_id) {
        PublicProfileFragment fragment = new PublicProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, user_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public_profile, container, false);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbarBack.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        viewModel.profileModelModelLiveData.observe(getViewLifecycleOwner(), model -> {
            if (model != null) {
                binding.publicName.setText(model.name_ar);
                binding.layoutLocation.setVisibility(View.GONE);
                if (model.address != null && !model.address.isEmpty()) {
                    binding.location.setText(model.address);
                    binding.layoutLocation.setVisibility(View.VISIBLE);
                }

                binding.layoutEducation.setVisibility(View.GONE);
                if (model.education != null && !model.education.isEmpty()) {
                    binding.education.setText(model.getPublicEducation());
                    binding.layoutEducation.setVisibility(View.VISIBLE);
                }

                binding.layoutWork.setVisibility(View.GONE);
                if (model.work_field != null && !model.work_field.isEmpty()) {
                    binding.work.setText(model.getPublicWork_field());
                    binding.layoutWork.setVisibility(View.VISIBLE);
                }

                binding.layoutExp.setVisibility(View.GONE);
                if (model.experience_years != null && !model.experience_years.isEmpty()) {
                    binding.exp.setText(model.experience_years + " سنوات");
                    binding.layoutExp.setVisibility(View.VISIBLE);
                }

                SliderPublicProfileAdapter sliderMainAdapter = new SliderPublicProfileAdapter(requireActivity(),new Gson().toJson(model));
                binding.viewpager.setAdapter(sliderMainAdapter);

                new TabLayoutMediator(binding.tabs, binding.viewpager, (tab, position) -> {
                    tab.setText(sliderMainAdapter.getItem(position));
                }).attach();

                loadImage(model.avatar, binding.profileImage);
            }
        });

        viewModel.getPublicProfile(requireContext(), user_id);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        }
    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.profile_logo) // Optional: Placeholder while loading
                .error(R.drawable.profile_logo) // Optional: Image to show on error
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }
}