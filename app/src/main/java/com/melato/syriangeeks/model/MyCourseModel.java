package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class MyCourseModel {
    public class Course{
        public int id;
        public String title;
        public int course_duration;
        public int course_category_id;
        public String slug;
        public int thumbnail;
        public String instructor_name;
        public ThumbnailImage thumbnail_image;
    }

    public class Datum{
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
        public Object completed_at;
        public int approved;
        public Date created_at;
        public Date updated_at;
        public Course course;
    }

    public class Enrolls{
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

    public class Link{
        public String url;
        public String label;
        public boolean active;
    }

    public class Paths{
        @SerializedName("100x100")
        public String _100x100;
        @SerializedName("300x300")
        public String _300x300;
        @SerializedName("600x600")
        public String _600x600;
    }

        public String title;
        public Enrolls enrolls;

    public class ThumbnailImage{
        public int id;
        public String original;
        public String name;
        public String type;
        public Paths paths;
        public Date created_at;
        public Date updated_at;
    }
}
