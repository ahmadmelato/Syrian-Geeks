package com.melato.syriangeeks.data;

import android.view.View;

public class Working {
    private int connectionState;
    private String smg;

    public Working(int connectionState, String msg) {
        smg = msg;
        this.connectionState = connectionState;
    }


    public boolean isSuccessful() {
        return (connectionState == ClientAPI.OK);
    }

    public boolean isRunning() {
        return (connectionState == ClientAPI.Run);
    }

    public int isProgressing(){
        if(connectionState == ClientAPI.Run)
            return View.VISIBLE;
        return View.GONE;
    }

    public int isFinish(){
        if(connectionState == ClientAPI.Run)
            return View.GONE;
        return View.VISIBLE;
    }

    public boolean isBooleanFinish(){
        if(connectionState == ClientAPI.Run)
            return false;
        return true;
    }

    public String getsSmg() {
        return smg;
    }

    public int getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(int connectionState) {
        this.connectionState = connectionState;
    }

}
