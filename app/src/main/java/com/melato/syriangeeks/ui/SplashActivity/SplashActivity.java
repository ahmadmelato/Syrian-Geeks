package com.melato.syriangeeks.ui.SplashActivity;

import static com.melato.syriangeeks.ui.SplashActivity.SplashActivityViewModel.text;

import android.content.Intent;
import android.graphics.Insets;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivitySplashBinding;
import com.melato.syriangeeks.ui.LoginActivity.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel splashActivityViewModel;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        splashActivityViewModel = ViewModelProviders.of(this).get(SplashActivityViewModel.class);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        splashActivityViewModel.txt.observe(this, s -> {
            if (s.length() > text.length()) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            } else if (s.length() > text.length()) {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                SplashActivity.this.finish();
            }
            binding.title.setText(s);
        });
        splashActivityViewModel.showText();

    }
}