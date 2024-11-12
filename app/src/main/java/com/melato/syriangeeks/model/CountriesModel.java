package com.melato.syriangeeks.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountriesModel {

        public String name;
        public String iso3;
        public String iso2;
        public String numeric_code;
        public String phone_code;
        public String capital;
        public String currency;
        public String currency_name;
        public String currency_symbol;
        public String tld;
        public String region;
        public int region_id;
        public String subregion;
        public int subregion_id;
        public String nationality;
        public Translations translations;
        public String latitude;
        public String longitude;
        public String emoji;
        public String emojiU;
        public ArrayList<State> states;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public class State{
        public int id;
        public String name;
        public String state_code;
        public String latitude;
        public String longitude;
        public String type;

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }



    public class Translations{
        public String ko;
        public String pt;
        public String nl;
        public String hr;
        public String fa;
        public String de;
        public String es;
        public String fr;
        public String ja;
        public String it;
        public String tr;
        public String ru;
        public String uk;
        public String pl;
    }


}
