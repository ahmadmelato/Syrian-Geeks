package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melato.syriangeeks.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingMethodologyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingMethodologyFragment extends Fragment {

    public TrainingMethodologyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training_methodology, container, false);
    }
}