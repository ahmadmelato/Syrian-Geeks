package com.melato.syriangeeks.model;

import java.util.ArrayList;
import java.util.Date;

public class DashbordModel {

    public String title;
    public Student student;
    public Enrolls enrolls;
    public ArrayList<Certificate> certificates;
    public ArrayList<Object> assignment;


    public String getState(){
        return  "لقد انتهيت من " + certificates.size() + " دورات اكتشف المزيد";
    }

    public Course getLastVisitCourse(){
        if(enrolls!=null && enrolls.courses!=null && !enrolls.courses.isEmpty()){
            return enrolls.courses.get(enrolls.courses.size()-1);
        }
        return null;
    }

    public static class Certificate {
        public int id;
        public String title;
        public String description;
        public String image;
        public Enroll enroll;
    }

    public static class Course {
        public int id;
        public String title;
        public int price;
        public int discount_price;
        public String image;
        public int rate;
        public int total_sales;
        public int reviewCount;
        public int is_free;
        public int is_discount;
        public Date created_at;
        public String course_creator;
        public String details;
        public boolean is_bookmark;
        public boolean is_purchased;
    }

    public static class Course2 {
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
        public int price;
        public int is_discount;
        public int discount_type;
        public Object discount_price;
        public Object discount_start_date;
        public Object discount_end_date;
        public Object instructor_id;
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
        public String cert_title;
    }

    public static class Enroll {
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
        public Object visited;
        public String completed_at;
        public int approved;
        public Date created_at;
        public Date updated_at;
        public Course course;
    }

    public static class Enrolls {
        public ArrayList<Course> courses;
        public Pagination pagination;
    }

    public static class Pagination {
        public int total;
        public int count;
        public int per_page;
        public int current_page;
        public int total_pages;
    }



    public static class Student {
        public int userId;
        public String name;
        public String name_ar;
        public String email;
        public String mobile;
        public String avatar;
        public int status_id;
        public String status;
        public String date_of_birth;
        public String joinDate;
    }


}
