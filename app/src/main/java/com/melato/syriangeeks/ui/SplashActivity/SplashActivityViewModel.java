package com.melato.syriangeeks.ui.SplashActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.model.UserModel;

public class SplashActivityViewModel extends ViewModel {

    public MutableLiveData<String> txt = new MutableLiveData<>();
    private Handler handler = new Handler();
    public final static String text = "Syrian Geeks";
    private int index;
    private char arr[] = text.toCharArray();


    public void showText() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (index > text.length()) {
                    txt.setValue(" " + txt.getValue() + " ");
                } else if (index == text.length()) {
                    index++;
                    handler.postDelayed(this, 500);
                } else {
                    if (txt.getValue() == null) {
                        txt.setValue("");
                    }
                    txt.setValue(txt.getValue() + arr[index]);
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        });
    }

    public UserModel getData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("MyPrefsLoginData", Context.MODE_PRIVATE);
        String loginInfo = preferences.getString("loginInfo", null);
        if(loginInfo != null){
            UserModel userModel = new Gson().fromJson(loginInfo,UserModel.class);
            ClientAPI.setClientAPIToken(userModel.getToken());
            return userModel;
        }
        return null;
    }

}
