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

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private FragmentProfileBinding binding;
    public MainViewModel viewModel;
    private SkillsViewAdapter skillsViewAdapter;
    private SocialViewAdapter linksViewAdapter;
    private EducationRecyclerViewAdapter educationRecyclerViewAdapter;
    private ExperienceRecyclerViewAdapter experienceRecyclerViewAdapter;


    private DialogUpdateProfile dialogUpdateProfile;
    private DialogUpdatePassword dialogUpdatePassword;
    private DialogInstitutes dialogInstitutes;
    private DialogExpertise dialogExpertise;
    private DialogAddSkill dialogAddSkill;
    private DialogAddLink dialogAddLink;

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
        dialogInstitutes = new DialogInstitutes(requireContext(), viewModel);
        dialogExpertise = new DialogExpertise(requireContext(),viewModel);
        dialogAddSkill =  new DialogAddSkill(requireContext(),viewModel);
        dialogAddLink =  new DialogAddLink(requireContext(),viewModel);

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
        binding.addInstitutes.setOnClickListener(this);
        binding.addExperie.setOnClickListener(this);
        binding.addSkill.setOnClickListener(this);
        binding.addlink.setOnClickListener(this);

        binding.skillList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true));
        skillsViewAdapter = new SkillsViewAdapter(this);
        binding.skillList.setAdapter(skillsViewAdapter);
        binding.linksList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        linksViewAdapter = new SocialViewAdapter(this);
        binding.linksList.setAdapter(linksViewAdapter);

        binding.educationList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        educationRecyclerViewAdapter = new EducationRecyclerViewAdapter(requireContext(),dialogInstitutes);
        binding.educationList.setAdapter(educationRecyclerViewAdapter);

        binding.experiencesList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        experienceRecyclerViewAdapter = new ExperienceRecyclerViewAdapter(requireContext(),dialogExpertise);
        binding.experiencesList.setAdapter(experienceRecyclerViewAdapter);


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
                binding.genderBirthday.setText(profileModel.getPublicGender() + " - " + profileModel.date_of_birth + " - " + (profileModel.getPublicNationality() != null ? profileModel.getPublicNationality() : ""));
                binding.edctionsWorks.setText((profileModel.getPublicEducation() != null ? profileModel.getPublicEducation() : "") + " - " + (profileModel.getPublicWork_field() != null ? profileModel.getPublicWork_field() : ""));
                binding.experniceWay.setVisibility(View.GONE);
                if(!profileModel.getPublicDesignation().isEmpty()) {
                    binding.experniceWay.setText(profileModel.getPublicDesignation() + " ");
                    binding.experniceWay.setVisibility(View.VISIBLE);
                }
                binding.phone.setText((profileModel.phone_dial != null ? profileModel.phone_dial : "")+ " "+ (profileModel.mobile != null ? profileModel.mobile : ""));
                binding.aboutMe.setText(profileModel.about_me != null ? profileModel.about_me : "");
                skillsViewAdapter.setSkillList(profileModel.skills != null ? profileModel.skills : new ArrayList<>());
                linksViewAdapter.setSkillList(profileModel.social_media_links != null ? profileModel.skills : new ArrayList<>());
                educationRecyclerViewAdapter.setDatumList(profileModel.institutes != null ? profileModel.institutes : new ArrayList<>());
                experienceRecyclerViewAdapter.setDatumList(profileModel.experience != null ? profileModel.experience : new ArrayList<>());
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
            dialogUpdateProfile.show(Objects.requireNonNull(viewModel.profileModelModelLiveData.getValue()));
        } else if (v.getId() == R.id.copy_profile_link && viewModel.profileModelModelLiveData.getValue()!= null) {
            copyToClipboard(requireContext(),viewModel.profileModelModelLiveData.getValue().public_profile);
        } else if (v.getId() == R.id.changepassword) {
            dialogUpdatePassword.show();
        } else if (v.getId() == R.id.nointernet) {
            viewModel.getProfile(requireContext());
        } else if (v.getId() == R.id.addInstitutes) {
            dialogInstitutes.show();
        }else if (v.getId() == R.id.addExperie) {
            dialogExpertise.show();
        }else if (v.getId() == R.id.addSkill) {
            dialogAddSkill.show();
        }else if (v.getId() == R.id.addlink) {
            dialogAddLink.show();
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