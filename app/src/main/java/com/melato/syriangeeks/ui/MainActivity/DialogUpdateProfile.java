package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogUpdateProfileBinding;
import com.melato.syriangeeks.model.CountriesModel;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainViewModel;

import java.util.Objects;

public class DialogUpdateProfile {

    private Context context;
    private MainViewModel viewModel;
    private AlertDialog dialog;
    private DialogUpdateProfileBinding binding;// Replace with your actual ViewModel type
    private ProfileModel model;

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

        binding.brithday.setOnClickListener(v -> new DialogDatePicker(context, binding.brithday).show());

        binding.genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.gender = position + 1;
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
                model.freelancer = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.edSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.education = model.getEducationByIbdex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.workSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.work_field = model.getWork_fieldByIndex(position);
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


        ArrayAdapter<CountriesModel> adapter111 = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                viewModel.getCountriesModels(context)
        );

        adapter111.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.conutery.setAdapter(adapter111);

        binding.conutery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountriesModel selectedCountry = (CountriesModel) parent.getItemAtPosition(position);
                if (selectedCountry.name != null && !selectedCountry.name.isEmpty()) {
                    model.country = selectedCountry.name;
                    ArrayAdapter<CountriesModel.State> adapter1 = new ArrayAdapter<>(
                            context,
                            android.R.layout.simple_spinner_item,
                            viewModel.getCountriesCityModels(context, position)
                    );

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.city.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountriesModel.State selectedCountry = (CountriesModel.State) parent.getItemAtPosition(position);
                model.state = selectedCountry.name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.nationalityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.nationality = position == 0 ? "syrian" : "other";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                context,
                R.array.nationality_options,
                R.layout.simple_spinner_item
        );
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.nationalityspinner.setAdapter(adapter3);


        binding.ch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                model.newsletter = 1;
            else
                model.newsletter = 0;
        });

        binding.submitButton.setOnClickListener(v -> viewModel.update_profile(context, model));

        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show(ProfileModel profileModel) {
        model = new ProfileModel();
        model.name = profileModel.name;
        model.name_ar = profileModel.name_ar;
        model.date_of_birth = profileModel.date_of_birth;
        model.gender = profileModel.gender;
        model.education = profileModel.education;
        model.work_field = profileModel.work_field;
        model.phone_dial = profileModel.phone_dial;
        model.phone = profileModel.mobile;
        model.nationality = profileModel.nationality;
        model.newsletter = profileModel.newsletter;
        model.experience_years = profileModel.experience_years;
        model.email = profileModel.email;
        model.about_me = profileModel.about_me;
        model.address = profileModel.address;
        model.freelancer = profileModel.freelancer;
        model.freelancer_years = profileModel.freelancer_years;
        model.country_id = profileModel.country_id;
        model.designation = profileModel.designation;
        model.country = profileModel.country;
        model.workFieldEnum = profileModel.workFieldEnum;
        model.educationEnum = profileModel.educationEnum;

        binding.setViewmodel(model);
        binding.genderspinner.setSelection(model.gender - 1);
        binding.ch1.setChecked(model.newsletter == 1);
        binding.nationalityspinner.setSelection(Objects.equals(model.nationality, "syrian") ? 0 : 1);
        binding.edSpinner.setSelection(getEducationPostions(model.education));
        binding.workSpinner.setSelection(getWork_fieldPostions(model.work_field));
        binding.freelancerspinner.setSelection(Objects.requireNonNull(model).freelancer);
        binding.conutery.setSelection(
                Objects.requireNonNull(model).country_id != null ? model.country_id - 1 : 214);
        dialog.show();
    }

    private int getEducationPostions(String education) {
        switch (education) {
            case "primary_school":
                return 0;
            case "middle_school":
                return 1;
            case "high_school":
                return 2;
            case "institute":
                return 3;
            case "university":
                return 4;
            case "higher_education":
                return 5;
        }
        return 0;
    }

    public int getWork_fieldPostions(String work_field) {
        switch (work_field) {
            case "Content Creator":
                return 0;
            case "Web Programming":
                return 1;
            case "Graphic Design":
                return 2;
            case "other":
                return 3;
        }
        return 3;
    }
}
