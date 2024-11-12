package com.melato.syriangeeks.ui.SignupActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();
    public MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    public List<CountriesModel> countriesModels;


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

    public List<CountriesModel.State> getCountriesCityModels(Context context,int index) {
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

    public void signup(String name, String name_ar, String date_of_birth, String gender, String nationality, String education,
                       String work_field, String other_work_field, String experience_years, String freelancer, String freelancer_years, String cv_file,
                       String country, String state, String location, String place, String disability, String email, String phone, String phone_dial,
                       String password, String password_confirmation, String newsletter, String agree, Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().signup(name, name_ar, date_of_birth, gender, nationality, education, work_field, other_work_field, experience_years, freelancer, freelancer_years, cv_file, country, state, location, place, disability, email, phone, phone_dial, password, password_confirmation, newsletter, agree).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    UserModel userModel = new Gson().fromJson(response.body().getData(), UserModel.class);
                    ClientAPI.setClientAPIToken(userModel.getToken());
                    userLiveData.setValue(userModel);
                    saveData(context, userModel);
                } else {
                    setProgressDeny(ClientAPI.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBodyModel> call, @NonNull Throwable t) {
                setProgressFiled(resources.getString(R.string.FailedtoloaddataChecknetwork));
                Log.println(Log.ERROR, "Syrian Geeks", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public void saveData(Context context, UserModel userModel) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsLoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("loginInfo", new Gson().toJson(userModel));
        editor.apply();
    }


}