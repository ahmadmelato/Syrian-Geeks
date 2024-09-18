package com.melato.syriangeeks.ui.SignupActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivitySignupBinding;

import java.util.Arrays;

public class SignupActivity extends AppCompatActivity {


    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.viewpager.setAdapter(new SliderAdapter(this));


        // Define the steps
        binding.stepView.setSteps(Arrays.asList("Step 1", "Step 2", "Step 3"));

        // Set the current step index
        binding.stepView.setStepsNumber(0); // Start at Step 1


    }
}