package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;

public class CourseDetalsModel {

    public class Category {
        public int id;
        public String title;
        public String slug;
        public int icon;
        public Object thumbnail;
        public int parent_id;
        public int user_id;
        public int status_id;
        public int is_popular;
        public Date created_at;
        public Date updated_at;
    }

    public class Paths {
        @SerializedName("100x100")
        public String _100x100;
        @SerializedName("300x300")
        public String _300x300;
        @SerializedName("600x600")
        public String _600x600;
    }

    public int id;
    public String title;
    public String slug;
    public String short_description;
    public String description;
    public int course_category_id;
    public String requirements;
    public Object outcomes;
    public Object faq;
    public Object tags;
    public String meta_title;
    public Object meta_description;
    public String meta_keywords;
    public String meta_author;
    public int meta_image;
    public int thumbnail;
    public int course_overview_type;
    public String video_url;
    public String language;
    public int course_type;
    public int is_admin;
    public Object price;
    public int is_discount;
    public int discount_type;
    public Object discount_price;
    public Object discount_start_date;
    public Object discount_end_date;
    public int instructor_id;
    public int is_multiple_instructor;
    public Object partner_instructors;
    public int is_free;
    public int level_id;
    public int status_id;
    public int visibility_id;
    public Object last_modified;
    public int rating;
    public int total_review;
    public int total_sales;
    public int course_duration;
    public int point;
    private String instructor_name;
    public int created_by;
    public int updated_by;
    public Object deleted_by;
    public Object deleted_at;
    public Date created_at;
    public Date updated_at;
    public Object cert_title;
    public boolean joined;
    public String time;
    public int lessonsCount;
    public ArrayList<Section> sections;
    public ThumbnailImage thumbnail_image;
    public Category category;

    public String getInstructor_name() {
        instructor_name = instructor_name.replace("المدرب:", "").replaceAll("\\s+", " ").trim();
        instructor_name = instructor_name.replace("المدرب", "").replaceAll("\\s+", " ").trim();
        return instructor_name;
    }

    public String getContent() {
        if(description == null)
            return "";
        Document document = Jsoup.parse(description);
        return document.text();
    }

    public class Section {
        public int id;
        public String title;
        public ArrayList<Lesson> lessons;
        public int course_id;
        public int order;
        public int status_id;
        public int created_by;
        public int updated_by;
        public Object deleted_at;
        public Date created_at;
        public Date updated_at;
        public String time;
        public int lessonsCount;
    }

    public class Lesson{
        public int id;
        public String title;
        public int is_quiz;
        public int is_timer;
        public boolean is_completed;
        public String duration;
        public int point;
        public int section_id;
        public int course_id;
        public int is_free;
        public String lesson_type;
        public String video_url;
        public Object video_type;
        public Object video_file;
        public int attachment_type;
        public Object attachment;
        public Object image_file;
        public int is_online_view;
        public int is_downloadable;
        public Object iframe;
        public Object lesson_text;
        public String content;
        public int order;
        public int marks;
        public int pass_marks;
        public Object instruction;
        public Object last_modified;
        public int created_by;
        public int updated_by;
        public int status_id;
        public Object deleted_at;
        public Date created_at;
        public Date updated_at;
        public String code;
    }

    public class ThumbnailImage {
        public int id;
        public String original;
        public String name;
        public String type;
        public Paths paths;
        public Date created_at;
        public Date updated_at;
    }

}
