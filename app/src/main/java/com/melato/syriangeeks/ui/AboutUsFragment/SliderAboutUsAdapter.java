package com.melato.syriangeeks.ui.AboutUsFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseDetalsModel;


public class SliderAboutUsAdapter extends FragmentStateAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tabus_text_1, R.string.tabus_text_2, R.string.tabus_text_3};
    private final Context context;

    public SliderAboutUsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return SyrianGeeksFragment.newInstance();
            case 1:
                return ContactUsFragment.newInstance();
            case 2:
                return PrivacyPolicyFragment.newInstance();
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