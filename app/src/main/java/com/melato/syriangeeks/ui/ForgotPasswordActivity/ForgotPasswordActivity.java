package com.melato.syriangeeks.ui.ForgotPasswordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityForgotPasswordBinding;
import com.melato.syriangeeks.ui.ActiveCodeActivity.ActiveCodeActivity;
import com.melato.syriangeeks.ui.LoginActivity.LoginActivity;
import com.melato.syriangeeks.ui.SignupActivity.SignupActivity;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.toolbar_back).setOnClickListener(this);
        binding.btuSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                onBackPressed();
                break;
            case R.id.btuSure:
                Intent activeCodeActivity = new Intent(ForgotPasswordActivity.this, ActiveCodeActivity.class);
                startActivity(activeCodeActivity);
                break;
        }
    }
}