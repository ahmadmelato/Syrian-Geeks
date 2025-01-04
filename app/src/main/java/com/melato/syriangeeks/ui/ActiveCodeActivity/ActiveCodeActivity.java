package com.melato.syriangeeks.ui.ActiveCodeActivity;

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

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityActiveCodeBinding;
import com.melato.syriangeeks.ui.NewPasswordActivity.NewPasswordActivity;

public class ActiveCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityActiveCodeBinding binding;
    private ActiveCodeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_active_code);
        viewModel = ViewModelProviders.of(this).get(ActiveCodeViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel.working.observe(this, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.btuSure.setVisibility(working.isFinish());
                binding.btuResend.setVisibility(working.isFinish());
                if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.toolbar_back).setOnClickListener(this);
        binding.btuSure.setOnClickListener(this);
        binding.btuResend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            finish();
        } else if (v.getId() == R.id.btuSure) {
            String code = binding.c1.getText().toString()+binding.c2.getText().toString()+binding.c3.getText().toString()+binding.c4.getText().toString();
            viewModel.verify_email(viewModel.getDataVerfiy_Code(getApplicationContext()),code,getApplicationContext(),this);
        } else if (v.getId() == R.id.btuResend) {
            viewModel.send_verification(viewModel.getDataVerfiy_Code(getApplicationContext()),getApplicationContext());
        }
    }
}
