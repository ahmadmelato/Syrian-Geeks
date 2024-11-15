package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseModel {

    public int current_page;
    public ArrayList<Datum> data;
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

    public class Datum {
        private int id;
        private String title;
        private String slug;
        private String short_description;
        private String description;
        private int course_category_id;
        private Object requirements;
        private Object outcomes;
        private Object faq;
        private Object tags;
        private String meta_title;
        private String meta_description;
        private String meta_keywords;
        private String meta_author;
        private int meta_image;
        private int thumbnail;
        private int course_overview_type;
        private String video_url;
        private String language;
        private int course_type;
        private int is_admin;
        private int price;
        private int is_discount;
        private int discount_type;
        private Object discount_price;
        private Object discount_start_date;
        private Object discount_end_date;
        private int instructor_id;
        private int is_multiple_instructor;
        private Object partner_instructors;
        private int is_free;
        private int level_id;
        private int status_id;
        private int visibility_id;
        private Object last_modified;
        public ThumbnailImage thumbnail_image;
        private int rating;
        private int total_review;
        private int total_sales;
        private int course_duration;
        private int point;
        private String instructor_name;
        private int created_by;
        private int updated_by;
        private Object deleted_by;
        private Object deleted_at;
        private Date created_at;
        private Date updated_at;
        private String cert_title;

        public class Paths {
            @SerializedName("100x100")
            public String _100x100;
            @SerializedName("300x300")
            public String _300x300;
            @SerializedName("600x600")
            public String _600x600;
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


        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getSlug() {
            return slug;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getDescription() {
            return description;
        }

        public int getCourse_category_id() {
            return course_category_id;
        }

        public Object getRequirements() {
            return requirements;
        }

        public Object getOutcomes() {
            return outcomes;
        }

        public Object getFaq() {
            return faq;
        }

        public Object getTags() {
            return tags;
        }

        public String getMeta_title() {
            return meta_title;
        }

        public String getMeta_description() {
            return meta_description;
        }

        public String getMeta_keywords() {
            return meta_keywords;
        }

        public String getMeta_author() {
            return meta_author;
        }

        public int getMeta_image() {
            return meta_image;
        }

        public int getThumbnail() {
            return thumbnail;
        }

        public int getCourse_overview_type() {
            return course_overview_type;
        }

        public String getVideo_url() {
            return video_url;
        }

        public String getLanguage() {
            return language;
        }

        public int getCourse_type() {
            return course_type;
        }

        public int getIs_admin() {
            return is_admin;
        }

        public int getPrice() {
            return price;
        }

        public int getIs_discount() {
            return is_discount;
        }

        public int getDiscount_type() {
            return discount_type;
        }

        public Object getDiscount_price() {
            return discount_price;
        }

        public Object getDiscount_start_date() {
            return discount_start_date;
        }

        public Object getDiscount_end_date() {
            return discount_end_date;
        }

        public int getInstructor_id() {
            return instructor_id;
        }

        public int getIs_multiple_instructor() {
            return is_multiple_instructor;
        }

        public Object getPartner_instructors() {
            return partner_instructors;
        }

        public int getIs_free() {
            return is_free;
        }

        public int getLevel_id() {
            return level_id;
        }

        public int getStatus_id() {
            return status_id;
        }

        public int getVisibility_id() {
            return visibility_id;
        }

        public Object getLast_modified() {
            return last_modified;
        }

        public int getRating() {
            return rating;
        }

        public int getTotal_review() {
            return total_review;
        }

        public int getTotal_sales() {
            return total_sales;
        }

        public int getCourse_duration() {
            return course_duration;
        }

        public int getPoint() {
            return point;
        }

        public String getInstructor_name() {
            return instructor_name;
        }

        public int getCreated_by() {
            return created_by;
        }

        public int getUpdated_by() {
            return updated_by;
        }

        public Object getDeleted_by() {
            return deleted_by;
        }

        public Object getDeleted_at() {
            return deleted_at;
        }

        public Date getCreated_at() {
            return created_at;
        }

        public Date getUpdated_at() {
            return updated_at;
        }

        public String getCert_title() {
            return cert_title;
        }
    }
}
