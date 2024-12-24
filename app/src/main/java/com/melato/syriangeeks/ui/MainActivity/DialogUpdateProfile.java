package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogUpdateProfileBinding;
import com.melato.syriangeeks.model.CountriesModel;
import com.melato.syriangeeks.ui.MainViewModel;

import java.util.Objects;

public class DialogUpdateProfile {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogUpdateProfileBinding binding;// Replace with your actual ViewModel type

    public DialogUpdateProfile(Context context, MainViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_update_profile,
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

        binding.genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.gender.set(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                context,
                R.array.gender_options,
                R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderspinner.setAdapter(adapter1);


        binding.freelancerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.freelancer.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.edSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.education.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.workSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.work_field.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(
                context,
                R.array.ed_options,
                R.layout.simple_spinner_item
        );
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.edSpinner.setAdapter(adapter0);

        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(
                context,
                R.array.work_options,
                R.layout.simple_spinner_item
        );
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.workSpinner.setAdapter(adapter11);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                context,
                R.array.yesno_options,
                R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.freelancerspinner.setAdapter(adapter2);


        binding.city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountriesModel.State selectedCountry = (CountriesModel.State) parent.getItemAtPosition(position);
                viewModel.state.set(selectedCountry.name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.conutery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountriesModel selectedCountry = (CountriesModel) parent.getItemAtPosition(position);
                viewModel.country.set(selectedCountry.name);
                ArrayAdapter<CountriesModel.State> adapter1 = new ArrayAdapter<>(
                        context,
                        android.R.layout.simple_spinner_item,
                        viewModel.getCountriesCityModels(context,position)
                );

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.city.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.nationalityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.nationality.set(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CountriesModel> adapter111 = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                viewModel.getCountriesModels(context)
        );

        adapter111.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.conutery.setAdapter(adapter111);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                context,
                R.array.nationality_options,
                R.layout.simple_spinner_item
        );
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.nationalityspinner.setAdapter(adapter3);


        binding.ch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                viewModel.newsletter.set(1);
            else
                viewModel.newsletter.set(0);
        });

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {

        binding.genderspinner.setSelection(viewModel.gender.get() - 1);
        binding.ch1.setChecked(viewModel.newsletter.get()==1);
        binding.nationalityspinner.setSelection(Objects.equals(viewModel.nationality.get(), "syrian") ?0:1);
        binding.edSpinner.setSelection(getEducationPostions(Objects.requireNonNull(viewModel.education.get())));
        binding.workSpinner.setSelection(getWork_fieldPostions(Objects.requireNonNull(viewModel.work_field.get())));
        binding.conutery.setSelection(
                (Objects.requireNonNull(viewModel.profileModelModelLiveData.getValue())).country_id!=null?viewModel.profileModelModelLiveData.getValue().country_id - 1:214);
        dialog.show();
    }

    private int getEducationPostions(String education) {
        switch (education) {
            case "primary_school": return 0;
            case "middle_school": return 1;
            case "high_school": return 2;
            case "institute": return 3;
            case "university": return 4;
            case "higher_education": return 5;
        }
        return 0;
    }

    public int getWork_fieldPostions(String work_field) {
        switch (work_field) {
            case "Content Creator": return 0;
            case "Web Programming": return 1;
            case "Graphic Design": return 2;
            case "other": return 3;
        }
        return 3;
    }
}
