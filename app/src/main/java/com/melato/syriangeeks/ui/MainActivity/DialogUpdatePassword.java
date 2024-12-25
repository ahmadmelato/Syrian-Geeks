package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogUpdatePasswordBinding;
import com.melato.syriangeeks.databinding.DialogUpdateProfileBinding;
import com.melato.syriangeeks.model.CountriesModel;
import com.melato.syriangeeks.ui.MainViewModel;

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

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

}
