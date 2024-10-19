package com.melato.syriangeeks.ui.LoginActivity;

import android.annotation.SuppressLint;
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
import com.melato.syriangeeks.databinding.ActivityLoginBinding;
import com.melato.syriangeeks.ui.ForgotPasswordActivity.ForgotPasswordActivity;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;
import com.melato.syriangeeks.ui.SignupActivity.SignupActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.createAccount.setOnClickListener(this);
        binding.btuRestPassword.setOnClickListener(this);
        binding.btuLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccount:
                Intent signupActivity = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupActivity);
                break;
            case R.id.btuRestPassword:
                Intent forgotPasswordActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordActivity);
                break;
            case R.id.btuLogin:
                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainActivity);
                break;
        }
    }
}