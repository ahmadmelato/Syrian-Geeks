package com.melato.syriangeeks.ui.LoginActivity;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityLoginBinding;
import com.melato.syriangeeks.model.UserModel;
import com.melato.syriangeeks.ui.ForgotPasswordActivity.ForgotPasswordActivity;
import com.melato.syriangeeks.ui.MainActivity.MainActivity;
import com.melato.syriangeeks.ui.SignupActivity.SignupActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.createAccount.setOnClickListener(this);
        binding.btuRestPassword.setOnClickListener(this);
        binding.btuLogin.setOnClickListener(this);

        loginViewModel.working.observe(this, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.btuLogin.setVisibility(working.isFinish());
                if(!working.isRunning())
                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        loginViewModel.userLiveData.observe(this, userModel -> {
            if(userModel != null){
                loginViewModel.saveData(getApplicationContext(),userModel);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });


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
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                if (!email.isEmpty() && password.length() >= 6)
                    loginViewModel.login(email, password,this);
                break;
        }
    }
}