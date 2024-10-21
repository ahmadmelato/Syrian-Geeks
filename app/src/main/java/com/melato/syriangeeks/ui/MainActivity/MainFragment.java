package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.FragmentMainBinding;


public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding binding;

    public MainFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton toolbar_openmenu = view.findViewById(R.id.toolbar_openmenu);
        ImageButton toolbar_notify = view.findViewById(R.id.toolbar_notify);
        toolbar_openmenu.setOnClickListener(this);
        toolbar_notify.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        int id = v.getId();
        if (id == R.id.toolbar_openmenu) {
            assert mainActivity != null;
            mainActivity.openDrawerLayout();
        } else if (id == R.id.toolbar_notify) {
            assert mainActivity != null;
            mainActivity.openNotifications();
        }
    }

}