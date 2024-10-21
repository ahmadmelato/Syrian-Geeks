package com.melato.syriangeeks.ui.ActiveCodeActivity;

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
import com.melato.syriangeeks.databinding.ActivityActiveCodeBinding;
import com.melato.syriangeeks.ui.NewPasswordActivity.NewPasswordActivity;

public class ActiveCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityActiveCodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_active_code);
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
        if (v.getId() == R.id.toolbar_back) {
            finish();
        } else if (v.getId() == R.id.btuSure) {
            Intent activeCodeActivity = new Intent(ActiveCodeActivity.this, NewPasswordActivity.class);
            startActivity(activeCodeActivity);
        }
    }
}