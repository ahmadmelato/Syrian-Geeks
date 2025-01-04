package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogRequestGuidanceBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainActivity.DialogDatePicker;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogRequestGuidance {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogRequestGuidanceBinding binding;

    public DialogRequestGuidance(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_request_guidance,
                null,
                true
        );

        dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(true)
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



        binding.startDate.setOnClickListener(v -> new DialogDatePicker(context,binding.startDate).show());
        binding.endDate.setOnClickListener(v -> new DialogTimePicker(context,binding.endDate).show());

        binding.submitButton.setOnClickListener(v -> {

        });

    }

    public void show() {
        dialog.show();
    }

}
