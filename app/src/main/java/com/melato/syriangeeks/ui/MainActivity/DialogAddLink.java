package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogAddLinkBinding;
import com.melato.syriangeeks.databinding.DialogAddSkillBinding;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogAddLink {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogAddLinkBinding binding;// Replace with your actual ViewModel type

    public DialogAddLink(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_add_link,
                null,
                true
        );

        dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(false)
                .create();


        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        viewModel.workingLoadMore.observe((LifecycleOwner) context, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.buttonPanel.setVisibility(working.isFinish());
                if (working.isSuccessful()) {
                    dialog.dismiss();
                } else if (!working.isRunning() && !working.isSuccessful())
                    Toast.makeText(context, working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.submitButton.setOnClickListener(v -> {
            String item = binding.value.getText().toString();
            if(!item.isEmpty()) {
                viewModel.store_add_social(context, item);
            }
        });

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

}
