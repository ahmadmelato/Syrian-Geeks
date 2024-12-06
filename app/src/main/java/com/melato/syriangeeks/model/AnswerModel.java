package com.melato.syriangeeks.model;

import com.google.gson.annotations.SerializedName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;

public class AnswerModel {

    public class Datum {
        public int id;
        public int forum_question_id;
        private String answer;
        public int status_id;
        public int created_by;
        public Object updated_by;
        public Date created_at;
        public Date updated_at;
        public ArrayList<Reply> reply;
        public User user;

        public String getAnswer() {
            Document document = Jsoup.parse(answer);
            return document.text();
        }
    }

    public class Link {
        public String url;
        public String label;
        public boolean active;
    }

    public class Reply {
        public int id;
        public int forum_answer_id;
        private String comment;
        public int status_id;
        public int created_by;
        public Date created_at;
        public Date updated_at;
        public User user;

        public String getComment() {
            Document document = Jsoup.parse(comment);
            return document.text();
        }
    }

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


    public class User {
        public int id;
        public String name;
        public String name_ar;
        public String username;
    }


}
