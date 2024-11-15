package com.melato.syriangeeks.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.data.Working;
import com.melato.syriangeeks.model.CountriesModel;
import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();
    public MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    public List<CountriesModel> countriesModels;

    @SuppressLint("SimpleDateFormat")
    public ObservableField<String> name = new ObservableField<>(""),
            name_ar = new ObservableField<>(""),
            date_of_birth = new ObservableField<>(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
            gender = new ObservableField<>(""),
            nationality = new ObservableField<>(""),
            education = new ObservableField<>(""),
            work_field = new ObservableField<>(""),
            other_work_field = new ObservableField<>(""),
            experience_years = new ObservableField<>(""),
            freelancer = new ObservableField<>(""),
            freelancer_years = new ObservableField<>("");

    public ObservableField<String> cv_file = new ObservableField<>(""),
            country = new ObservableField<>(""),
            state = new ObservableField<>(""),
            location = new ObservableField<>(""),
            place = new ObservableField<>(""),
            disability = new ObservableField<>(""),
            email = new ObservableField<>(""),
            phone = new ObservableField<>(""),
            phone_dial = new ObservableField<>("00963"),
            password = new ObservableField<>(""),
            password_confirmation = new ObservableField<>(""),
            newsletter = new ObservableField<>(""),
            agree = new ObservableField<>("");


    private void setProgressOK(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.OK, msg));
        }
    }

    private void setProgressFiled(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Filed, msg));
        }
    }

    private void setProgressDeny(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Deny, msg));
        }
    }

    private void setProgressRun(String msg) {
        synchronized (working) {
            working.setValue(new Working(ClientAPI.Run, msg));
        }
    }

    public List<CountriesModel> getCountriesModels(Context context) {
        if (countriesModels == null) {
            Type listType = new TypeToken<List<CountriesModel>>() {
            }.getType();
            countriesModels = new Gson().fromJson(loadJSONFromRaw(context, R.raw.countriesstates), listType);
        }
        return countriesModels;
    }

    public List<CountriesModel.State> getCountriesCityModels(Context context, int index) {
        if (countriesModels == null) {
            Type listType = new TypeToken<List<CountriesModel>>() {
            }.getType();
            countriesModels = new Gson().fromJson(loadJSONFromRaw(context, R.raw.countriesstates), listType);
        }
        return countriesModels.get(index).states;
    }

    private String loadJSONFromRaw(Context context, int resourceId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(resourceId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    //    String name, String name_ar, String date_of_birth, String gender, String nationality, String education,
//    String work_field, String other_work_field, String experience_years, String freelancer, String freelancer_years, String cv_file,
//    String country, String state, String location, String place, String disability, String email, String phone, String phone_dial,
//    String password, String password_confirmation, String newsletter, String agree,
    public void signup(Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().signup(name.get(), name_ar.get(), date_of_birth.get(), gender.get(), nationality.get(), education.get(), work_field.get(), other_work_field.get(), experience_years.get(), freelancer.get(), experience_years.get(), cv_file.get(), country.get(), state.get(), location.get(), place.get(), disability.get(), email.get(), phone.get(), phone_dial.get(), password.get(), password_confirmation.get(), newsletter.get(), agree.get()).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    saveData(context, email.get());
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                    saveData(context, null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
                saveData(context, null);
            }
        });
    }

    public void saveData(Context context, String mail) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsSignupData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("mail", mail);
        editor.apply();
    }

}