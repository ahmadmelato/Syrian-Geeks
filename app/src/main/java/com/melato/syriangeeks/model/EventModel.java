package com.melato.syriangeeks.model;

import java.util.ArrayList;
import java.util.Date;

public class EventModel {

    public int current_page;
    public ArrayList<Item> data;
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

    public class Image {
        public int id;
        public String original;
        public String name;
        public String type;
        public ArrayList<Object> paths;
        public Date created_at;
        public Date updated_at;
    }

    public class Item {
        public int id;
        public String title;
        public String slug;
        public String content;
        public String address;
        public int image_id;
        public Date start_date;
        public Image image;
    }

}
