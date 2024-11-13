package com.melato.syriangeeks.ui.ActiveCodeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.data.Working;
import com.melato.syriangeeks.model.ResponseBodyModel;
import com.melato.syriangeeks.model.UserModel;
import com.melato.syriangeeks.ui.LoginActivity.LoginActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveCodeViewModel extends ViewModel {

    public MutableLiveData<Working> working = new MutableLiveData<>();

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

    public void verify_email(String email, String code, Context context, Activity activity) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().verify_email(email, code).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
                    saveData(context, null);
                    Intent intent = new Intent(context, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
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

    public void send_verification(String email, Context context) {
        setProgressRun("");
        Resources resources = context.getResources();
        ClientAPI.getClientAPI().send_verification(email).enqueue(new Callback<ResponseBodyModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBodyModel> call, @NonNull Response<ResponseBodyModel> response) {
                if (response.code() == ClientAPI.OK) {
                    assert response.body() != null;
                    setProgressOK(response.body().getMessage());
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

    public void saveData(Context context, String mail) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsSignupData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("mail", mail);
        editor.apply();
    }

    public String getDataVerfiy_Code(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsSignupData", Context.MODE_PRIVATE);
        String mail = preferences.getString("mail", null);
        if (mail != null) {
            return mail;
        }
        return null;
    }





}