package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class LeaderBoardModel {

    public String title;
    public Students students;

    public class Datum {
        public int id;
        public String about_me;
        public String designation;
        public String address;
        public int gender;
        public String date_of_birth;
        public Object badges;
        public Object education;
        public Object experience;
        public Object skills;
        public Object social_media_links;
        public int points;
        public Object country_id;
        public int user_id;
        public int cv_file_id;
        public Date created_at;
        public Date updated_at;
        public User user;
    }

    public class Link {
        public String url;
        public String label;
        public boolean active;
    }


    public class Students {
        public int current_page;
        public ArrayList<Datum> data;
        public String first_page_url;
        public int from;
        public int last_page;
        public String last_page_url;
        public ArrayList<Link> links;
        public String next_page_url;
        public String path;
        public int per_page;
        public Object prev_page_url;
        @SerializedName("to")
        public int myto;
        public int total;
    }

    public class User {
        public int id;
        public String name;
        public String name_ar;
        public String username;
        public String country;
    }


}
