package com.melato.syriangeeks.ui.LoginActivity;

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
import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();
    public MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();


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

    public void login(String mail_p, String password_p, Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().login(mail_p, password_p).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    UserModel userModel =  new Gson().fromJson(response.body().getData(), UserModel.class);
                    ClientAPI.setClientAPIToken(userModel.getToken());
                    userLiveData.setValue(userModel);
                    System.out.println(userModel.getToken());
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