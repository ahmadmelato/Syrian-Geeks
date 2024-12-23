package com.melato.syriangeeks.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileModel {

    public class EducationEnum {
        public String primary_school;
        public String middle_school;
        public String high_school;
        public String institute;
        public String university;
        public String higher_education;
    }

    public class GenderEnum {
        public int male;
        public int female;
    }

    public int id;
    public String about_me;
    private String designation;
    public String address;
    private int gender;
    public String date_of_birth;
    public Object badges;
    public Object experience;
    public ArrayList<Skill> skills;
    public ArrayList<Skill> social_media_links;
    public int points;
    public Object country_id;
    public int user_id;
    public Object cv_file_id;
    public String cv_file;
    public String name;
    public Object name_ar;
    private String nationality;
    private String education;
    public Object work_field;
    public String email;
    public String mobile;
    public String avatar;
    public int status_id;
    public String status;
    public int newsletter;
    public String joinDate;
    public GenderEnum genderEnum;
    public WorkFieldEnum workFieldEnum;
    public EducationEnum educationEnum;


    public String getDesignation() {
        return designation;
    }

    public String getNationality() {
        switch (nationality) {
            case "syrian": return "سوري";
        }
        return nationality;
    }

    public String getEducation() {
        switch (education) {
            case "primary_school": return "ابتدائي";
            case "middle_school": return "اعدادي";
            case "high_school": return "ثانوي";
            case "institute": return "المعهد";
            case "university": return "جامعي";
            case "higher_education": return "دراسات عليا";

        }
        return education;
    }

    public String getGender() {
        if(gender == 2)
            return "انثى";
        return "ذكر";
    }

    public class WorkFieldEnum {
        @SerializedName("Content Creator")
        public String contentCreator;
        @SerializedName("Web Programming")
        public String webProgramming;
        @SerializedName("Graphic Design")
        public String graphicDesign;
        @SerializedName("other")
        public String other;
    }

    public class Skill{
        public String value;
    }


}
