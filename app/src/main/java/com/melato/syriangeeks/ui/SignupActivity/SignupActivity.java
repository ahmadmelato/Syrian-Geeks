package com.melato.syriangeeks.ui.SignupActivity;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.On;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivitySignupBinding binding;
    private SliderAdapter sliderAdapter;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sliderAdapter = new SliderAdapter(this);
        binding.viewpager.setAdapter(sliderAdapter);
        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.stepView.go(position, true);
                if(position+1>=sliderAdapter.getItemCount()){
                    binding.btuNext.setText(getResources().getString(R.string.signup));
                }
                else {
                    binding.btuNext.setText(getResources().getString(R.string.next));
                }
            }
        };

        binding.viewpager.registerOnPageChangeCallback(onPageChangeCallback);
        binding.stepView.setStepsNumber(sliderAdapter.getItemCount());
        binding.btuBack.setOnClickListener(this);
        binding.btuNext.setOnClickListener(this);


    }

    public SignupViewModel getSignupViewModel() {
        return signupViewModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btuNext:
                if (binding.viewpager.getCurrentItem() < sliderAdapter.getItemCount()) {
                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
                }
                break;

            case R.id.btuBack:
                if (binding.viewpager.getCurrentItem() > 0) {
                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() - 1);
                }
                break;
        }
    }
}