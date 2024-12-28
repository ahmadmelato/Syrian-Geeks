package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogExpertiseBinding;
import com.melato.syriangeeks.databinding.DialogInstitutesBinding;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogExpertise {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogExpertiseBinding binding;// Replace with your actual ViewModel type
    private ProfileModel.Experience experience;
    private Integer index;

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



        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                context,
                R.array.work_type,
                R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.typeofemployment.setAdapter(adapter1);

        binding.typeofemployment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                experience.employee_type = experience.getEmployee_typeByIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                context,
                R.array.site_type,
                R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sitetype.setAdapter(adapter2);

        binding.sitetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                experience.location_type = experience.getLocation_typeByIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                viewModel.update_experience(context,index,experience);
            }else {
                viewModel.store_experience(context,experience);
            }
        });

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        index = null;
        experience = new ProfileModel.Experience();
        binding.setViewmodel(experience);
        dialog.show();
    }

    public void show(int index,ProfileModel.Experience experience) {
        this.index=index;
        this.experience = experience;
        binding.setViewmodel(experience);
        binding.typeofemployment.setSelection(experience.getEmployee_typeIndex());
        binding.sitetype.setSelection(experience.getLocation_typeIndex());
        dialog.show();
    }

    public void delete(int index) {
        viewModel.delete_experience(context,index);
    }

}
