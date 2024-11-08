package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentProfileBinding;
import com.melato.syriangeeks.model.Section;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener{


    private FragmentProfileBinding binding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbarBack.setOnClickListener(this);
        binding.sectionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Section> sections = new ArrayList<>();
        sections.add(new Section("حول", R.drawable.book));
        sections.add(new Section("الخبرات", R.drawable.book));
        sections.add(new Section("التعليم", R.drawable.book));
        sections.add(new Section("المهارات", R.drawable.book));
        sections.add(new Section("روابط التواصل الاجتماعي", R.drawable.book));
        sections.add(new Section("السيرة الذاتية", R.drawable.book));
        sections.add(new Section("رقم الهاتف", R.drawable.book));
        binding.sectionsRecyclerView.setAdapter(new ProfileSectionAdapter(getContext(),sections));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.openMain();
        }
    }
}