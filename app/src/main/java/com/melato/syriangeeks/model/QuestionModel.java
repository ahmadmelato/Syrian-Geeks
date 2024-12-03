package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class QuestionModel {

    public class Category{
        public int id;
        public String title;
        public String slug;
        public int created_by;
        public int status_id;
        public int is_featured;
        public int is_course;
        public ArrayList<String> filters;
        public Date created_at;
        public Date updated_at;
    }

    public class Datum{
        public int id;
        public String title;
        public String slug;
        public String question;
        public int forum_category_id;
        public int created_by;
        public Object updated_by;
        public int status_id;
        public int is_featured;
        public Date created_at;
        public Date updated_at;
        public int answers_count;
        public User user;
    }

    public class Link{
        public String url;
        public String label;
        public boolean active;
    }

    public class Questions{
        public int current_page;
        public ArrayList<Datum> data;
        public String first_page_url;
        public int from;
        public int last_page;
        public String last_page_url;
        public ArrayList<Link> links;
        public Object next_page_url;
        public String path;
        public int per_page;
        public Object prev_page_url;
        @SerializedName("to")
        public int myto;
        public int total;
    }


        public Questions questions;
        public ArrayList<Category> categories;


    public class User{
        public int id;
        public String name;
        public String name_ar;
        public String username;
    }


}
