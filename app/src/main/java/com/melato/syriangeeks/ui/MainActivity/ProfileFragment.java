package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentProfileBinding;
import com.melato.syriangeeks.ui.MainViewModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private FragmentProfileBinding binding;
    private MainViewModel viewModel;
    private SkillsViewAdapter skillsViewAdapter;
    private SkillsViewAdapter linksViewAdapter;

    private DialogUpdateProfile dialogUpdateProfile;
    private DialogUpdatePassword dialogUpdatePassword;
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        dialogUpdateProfile = new DialogUpdateProfile(requireContext(), viewModel);
        dialogUpdatePassword = new DialogUpdatePassword(requireContext(), viewModel);

        binding.toolbarBack.setOnClickListener(this);
        binding.nointernet.setOnClickListener(this);
        binding.expan1.setOnClickListener(this);
        binding.expan2.setOnClickListener(this);
        binding.expan3.setOnClickListener(this);
        binding.expan4.setOnClickListener(this);
        binding.expan5.setOnClickListener(this);
        binding.editaboutfbtu.setOnClickListener(this);
        binding.copyProfileLink.setOnClickListener(this);
        binding.changepassword.setOnClickListener(this);

        binding.skillList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true));
        skillsViewAdapter = new SkillsViewAdapter(requireContext());
        binding.skillList.setAdapter(skillsViewAdapter);
        binding.linksList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        linksViewAdapter = new SkillsViewAdapter(requireContext());
        binding.linksList.setAdapter(linksViewAdapter);


        viewModel.working.observe(getViewLifecycleOwner(), working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.mainLayout.setVisibility(working.isSuccessfulView());
                binding.nointernet.setVisibility(working.isNotSuccessfulView());
            }
        });

        MainViewModel.userLiveData.observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null) {
                binding.publicName.setText(userModel.getUser().getName());
            } else {
                binding.publicName.setText("");
            }
        });

        viewModel.profileModelModelLiveData.observe(getViewLifecycleOwner(), profileModel -> {
            if (profileModel != null) {
                binding.fullName.setText((profileModel.name_ar != null ? profileModel.name_ar : "") + " - " + profileModel.name);
                binding.genderBirthday.setText(profileModel.getGender() + " - " + profileModel.date_of_birth + " - " + (profileModel.getNationality() != null ? profileModel.getNationality() : ""));
                binding.edctionsWorks.setText((profileModel.getEducation() != null ? profileModel.getEducation() : "") + " - " + (profileModel.getWork_field() != null ? profileModel.getWork_field() : ""));
                binding.experniceWay.setText(profileModel.getDesignation() + " ");
                binding.phone.setText((profileModel.phone_dial != null ? profileModel.phone_dial : "")+ " "+ (profileModel.mobile != null ? profileModel.mobile : ""));
                binding.aboutMe.setText(profileModel.about_me != null ? profileModel.about_me : "");
                skillsViewAdapter.setSkillList(profileModel.skills);
                linksViewAdapter.setSkillList(profileModel.social_media_links);
                loadImage(profileModel.avatar, binding.profileImage);
            }
        });


        if (viewModel.profileModelModelLiveData.getValue() == null)
            viewModel.getProfile(requireContext());


    }


    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.profile_logo) // Optional: Placeholder while loading
                .error(R.drawable.profile_logo) // Optional: Image to show on error
                .into(img);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.backPressed();
        } else  if (v.getId() == R.id.expan_1) {
            if (binding.about.getVisibility() == View.GONE) {
                binding.about.setVisibility(View.VISIBLE);
            } else {
                binding.about.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.expan_2) {
            if (binding.education.getVisibility() == View.GONE) {
                binding.education.setVisibility(View.VISIBLE);
            } else {
                binding.education.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.expan_3) {
            if (binding.skill.getVisibility() == View.GONE) {
                binding.skill.setVisibility(View.VISIBLE);
            } else {
                binding.skill.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.expan_4) {
            if (binding.experiences.getVisibility() == View.GONE) {
                binding.experiences.setVisibility(View.VISIBLE);
            } else {
                binding.experiences.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.expan_5) {
            if (binding.links.getVisibility() == View.GONE) {
                binding.links.setVisibility(View.VISIBLE);
            } else {
                binding.links.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.editaboutfbtu) {
            dialogUpdateProfile.show();
        } else if (v.getId() == R.id.copy_profile_link && viewModel.profileModelModelLiveData.getValue()!= null) {
            copyToClipboard(requireContext(),viewModel.profileModelModelLiveData.getValue().public_profile);
        } else if (v.getId() == R.id.changepassword) {
            dialogUpdatePassword.show();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getProfile(requireContext());
        }
    }

    public void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Syrian Geeks", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show();
        }
    }
}