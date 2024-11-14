package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BlogDetalsModel {
    public int id;
    public String title;
    public String slug;
    public String description;
    public int image_id;
    public int status_id;
    public int blog_categories_id;
    public int created_by;
    public Object updated_by;
    public Object deleted_by;
    public String meta_title;
    public String meta_description;
    public String meta_keywords;
    public int meta_image_id;
    public Object deleted_at;
    public Date created_at;
    public Date updated_at;
    public Category category;
    public MetaImage meta_image;
    public IconImage icon_image;
    public User user;

    public class Category {
        public int id;
        public String title;
    }

    public class IconImage {
        public int id;
        public String original;
        public String name;
        public String type;
        public Paths paths;
        public Date created_at;
        public Date updated_at;
    }

    public class MetaImage {
        public int id;
        public String original;
        public String name;
        public String type;
        public Paths paths;
        public Date created_at;
        public Date updated_at;
    }

    public class Paths {
        @SerializedName("700x700")
        public String _700x700;
        @SerializedName("220x300")
        public String _220x300;
        @SerializedName("400x750")
        public String _400x750;
        @SerializedName("35x35")
        public String _35x35;
    }


    public class User {
        public int id;
        public String name;
        public Object image_id;
    }


}
