package com.melato.syriangeeks.ui.SignActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.melato.syriangeeks.ui.ActiveCodeActivity.ActiveCodeActivity;
import com.melato.syriangeeks.ui.LoginActivity.LoginActivity;
import com.melato.syriangeeks.ui.SignupViewModel;

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

        signupViewModel.working.observe(this, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.buttonPanel.setVisibility(working.isFinish());
                binding.btuLogin.setEnabled(working.isBooleanFinish());
                if (working.isSuccessful()){
                    Intent intent = new Intent(SignupActivity.this, ActiveCodeActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                else if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
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
        binding.btuLogin.setOnClickListener(this);


    }

    public SignupViewModel getSignupViewModel() {
        return signupViewModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btuNext:
                if (binding.viewpager.getCurrentItem() < sliderAdapter.getItemCount() - 1) {
                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
                }else{
                    signupViewModel.signup(getApplicationContext());
                }
                break;

            case R.id.btuBack:
                if (binding.viewpager.getCurrentItem() > 0) {
                    binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() - 1);
                }
                break;
            case R.id.btuLogin:
                Intent signupActivity = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(signupActivity);
                this.finish();
                break;

        }
    }
}