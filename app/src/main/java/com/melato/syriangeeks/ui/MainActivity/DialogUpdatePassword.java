package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogUpdatePasswordBinding;
import com.melato.syriangeeks.databinding.DialogUpdateProfileBinding;
import com.melato.syriangeeks.model.CountriesModel;
import com.melato.syriangeeks.ui.ActiveCodeActivity.ActiveCodeActivity;
import com.melato.syriangeeks.ui.MainViewModel;
import com.melato.syriangeeks.ui.SignActivity.SignupActivity;

import java.util.Objects;

public class DialogUpdatePassword {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogUpdatePasswordBinding binding;// Replace with your actual ViewModel type

    public DialogUpdatePassword(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_update_password,
                null,
                true
        );

        dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(false)
                .create();


        binding.setViewmodel(viewModel);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        viewModel.workingLoadMore.observe((LifecycleOwner) context, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.buttonPanel.setVisibility(working.isFinish());
                if (working.isSuccessful())
                {
                    dialog.dismiss();
                }
                else if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(context, working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = binding.oldPassword.getText().toString();
                String password = binding.password.getText().toString();
                String password_confirmation = binding.passwordConfirmation.getText().toString();
                if(!old_password.isEmpty() &&!password.isEmpty() &&!password_confirmation.isEmpty()){
                    viewModel.update_password(context,old_password,password,password_confirmation);
                }
            }
        });

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

}
