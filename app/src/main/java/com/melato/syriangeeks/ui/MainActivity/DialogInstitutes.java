package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogInstitutesBinding;
import com.melato.syriangeeks.databinding.DialogUpdatePasswordBinding;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogInstitutes {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogInstitutesBinding binding;// Replace with your actual ViewModel type

    public DialogInstitutes(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_institutes,
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

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

}