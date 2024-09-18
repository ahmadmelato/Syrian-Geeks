package com.melato.syriangeeks.ui.SignupActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SliderAdapter extends FragmentStateAdapter {


    public SliderAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return FirstFragment.newInstance();
            case 1:return FirstFragment.newInstance();
            case 2:return FirstFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
