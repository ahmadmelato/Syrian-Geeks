package com.melato.syriangeeks.model;

import android.content.Intent;

public class UserModel {

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public class User {
        private Integer userId;
        private String name;
        private String name_ar;
        private String email;
        private String mobile;
        private String avatar;
        private Integer status_id;
        private String status;
        private Object date_of_birth;
        private String joinDate;

        public Integer getUserId() {
            return userId;
        }

        public String getName() {
            return name_ar;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public Integer getStatus_id() {
            return status_id;
        }

        public String getStatus() {
            return status;
        }

        public Object getDate_of_birth() {
            return date_of_birth;
        }

        public String getJoinDate() {
            return joinDate;
        }
    }

}
