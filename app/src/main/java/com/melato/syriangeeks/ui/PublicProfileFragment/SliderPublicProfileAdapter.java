package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.ui.AboutUsFragment.ContactUsFragment;
import com.melato.syriangeeks.ui.AboutUsFragment.PrivacyPolicyFragment;
import com.melato.syriangeeks.ui.AboutUsFragment.SyrianGeeksFragment;


public class SliderPublicProfileAdapter extends FragmentStateAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.about, R.string.public_expertise, R.string.public_skill, R.string.social_skill};
    private final Context context;

    public SliderPublicProfileAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return AboutFragment.newInstance("sss");
            case 1:
                return ExperienceFragment.newInstance("sss");
            case 2:
                return SkillFragment.newInstance("sss");
            case 3:
                return SocialFragment.newInstance("assa");
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