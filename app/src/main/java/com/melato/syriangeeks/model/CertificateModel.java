package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class CertificateModel {
    public int id;
    public String title;
    public String description;
    public String image;
    public Enroll enroll;

    public class Course{
        public int id;
        public String title;
        public String slug;
        public String short_description;
        public String description;
        public int course_category_id;
        public Object requirements;
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
        public String instructor_name;
        public int created_by;
        public int updated_by;
        public Object deleted_by;
        public Object deleted_at;
        public Date created_at;
        public Date updated_at;
        public Object cert_title;
    }

    public class Enroll{
        public int id;
        public int order_item_id;
        public int course_id;
        public int user_id;
        public int instructor_id;
        public int progress;
        public int is_completed;
        public ArrayList<String> completed_lessons;
        public Object completed_quizzes;
        public Object completed_assignments;
        public int lesson_point;
        public int quiz_point;
        public int assignment_point;
        public String visited;
        public String completed_at;
        public int approved;
        public Date created_at;
        public Date updated_at;
        public Course course;
    }
}
