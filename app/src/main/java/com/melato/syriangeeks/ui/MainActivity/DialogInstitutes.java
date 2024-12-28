package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogInstitutesBinding;
import com.melato.syriangeeks.databinding.DialogUpdatePasswordBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogInstitutes {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogInstitutesBinding binding;
    private ProfileModel.Institute institute;
    private Integer index;

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


        binding.ch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.endDateLayout.setVisibility(View.GONE);
            }else {
                binding.endDateLayout.setVisibility(View.VISIBLE);
            }
        });


        binding.startDate.setOnClickListener(v -> new DialogDatePicker(context,binding.startDate).show());
        binding.endDate.setOnClickListener(v -> new DialogDatePicker(context,binding.endDate).show());

        binding.submitButton.setOnClickListener(v -> {
            if(index != null){
                viewModel.update_institute(context,index,institute);
            }else {
                viewModel.store_institute(context,institute);
            }
        });

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        index = null;
        institute = new ProfileModel.Institute();
        binding.setViewmodel(institute);
        dialog.show();
    }

    public void show(int index,ProfileModel.Institute institute) {
        this.index=index;
        this.institute = institute;
        binding.setViewmodel(institute);
        dialog.show();
    }

    public void delete(int index) {
        viewModel.delete_institute(context,index);
    }

}
