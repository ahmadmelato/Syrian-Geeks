package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogExpertiseBinding;
import com.melato.syriangeeks.databinding.DialogInstitutesBinding;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogExpertise {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogExpertiseBinding binding;// Replace with your actual ViewModel type

    public DialogExpertise(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_expertise,
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


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                context,
                R.array.work_type,
                R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.typeofemployment.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                context,
                R.array.site_type,
                R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sitetype.setAdapter(adapter2);


        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

}
