package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseDetalsModel;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;


public class SliderMainAdapter extends FragmentStateAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context context;
    private final CourseDetalsModel model;
    public SliderMainAdapter(@NonNull FragmentActivity fragmentActivity,CourseDetalsModel model) {
        super(fragmentActivity);
        this.context = fragmentActivity;
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return OverviewFragment.newInstance(new Gson().toJson(model));
            case 1:
                return TrainingMethodologyFragment.newInstance(new Gson().toJson(model));
            case 2:
                return RequirementFragment.newInstance(new Gson().toJson(model));
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return TAB_TITLES.length;
    }

    public String getItem(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

}