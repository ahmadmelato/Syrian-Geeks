package com.melato.syriangeeks.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationModel {

    public List<Notification> notifications;

    public static class Data {
        public String message;
    }

    public static class Notification {
        public String id;
        public String type;
        public String notifiable_type;
        public int notifiable_id;
        public Data data;
        public Date read_at;
        public Date created_at;
        public Date updated_at;
    }
}
