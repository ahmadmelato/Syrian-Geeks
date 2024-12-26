package com.melato.syriangeeks.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileModel {

    public static class EducationEnum {
        public String primary_school;
        public String middle_school;
        public String high_school;
        public String institute;
        public String university;
        public String higher_education;
    }

    public static class GenderEnum {
        public int male;
        public int female;
    }

    public static class Institute{
        public String name;
        public String program;
        public String degree;
        public int current;
        public String start_date;
        public String end_date;
        public String description;
    }


    public static class Experience{
        public String title;
        public String name;
        private String employee_type;
        public String location;
        public String location_type;
        public int current;
        public String start_date;
        public String end_date;
        public String description;


        public String getEmployee_type() {
            switch (employee_type) {
                case "full_time": return "وقت كامل";
                case "part_time": return "وقت جزئي";
                case "internship": return "تدريب";
                case "volunteer": return "تطوع";
                case "contract": return "عقد";
                case "temporary": return "مؤقت";

            }
            return employee_type;
        }

        public int getEmployee_typeIndex() {
            switch (employee_type) {
                case "full_time": return 0;
                case "part_time": return 1;
                case "internship": return 2;
                case "volunteer": return 3;
                case "contract": return 4;
                case "temporary": return 5;

            }
            return 0;
        }

        public int getLocation_typeIndex() {
            if (location_type.equals("hybrid")) {
                return 1;
            }
            return 0;
        }

    }

    public int id;
    public String about_me;
    public String designation;
    public String address;
    public int gender;
    public String date_of_birth;
    public Object badges;
    public ArrayList<Experience> experience;
    public ArrayList<Skill> skills;
    public ArrayList<Skill> social_media_links;
    public int points;
    public Integer country_id;
    public int user_id;
    public Object cv_file_id;
    public String cv_file;
    public String name;
    public String name_ar;
    public String nationality;
    public String education;
    public String email;
    public String phone_dial;
    public String mobile;
    public String avatar;
    public int status_id;
    public String status;
    public int newsletter;
    public int freelancer;
    public int freelancer_years;
    public String work_field;
    public int experience_years;
    public String joinDate;
    public String public_profile;
    public ArrayList<Institute> institutes;
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

    public String getWork_field() {
        switch (work_field) {
            case "Content Creator": return "صانع محتوى";
            case "Web Programming": return "مبرمج ويب";
            case "Graphic Design": return "مصمم جرافيكي";
            case "other": return "غير ذلك";
        }
        return work_field;
    }

    public int getWork_fieldPostions() {
        switch (work_field) {
            case "Content Creator": return 0;
            case "Web Programming": return 1;
            case "Graphic Design": return 2;
            case "other": return 3;
        }
        return 3;
    }

    public int getEducationPostions() {
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
