package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;

public class BlogModel {

    public int current_page;
    public ArrayList<Blog> data;
    public String first_page_url;
    public int from;
    public int last_page;
    public String last_page_url;
    public String next_page_url;
    public String path;
    public int per_page;
    public Object prev_page_url;
    public int myto;
    public int total;


    public class IconImage {
        public int id;
        public String original;
        public String name;
        public String type;
        public Paths paths;
        public Date created_at;
        public Date updated_at;
    }

    public class Paths {
        @SerializedName("35x35")
        public String _35x35;
        @SerializedName("220x300")
        public String _220x300;
        @SerializedName("400x750")
        public String _400x750;
    }

    public class Blog {
        public int id;
        public String title;
        private String description;
        public int image_id;
        public Date created_at;
        public IconImage icon_image;

        public String getDescription() {
            Document document = Jsoup.parse(description);
            return document.text();
        }
    }
}
