package com.melato.syriangeeks.model;

public class Section {
    private String title;
    private int iconResId;

    public Section(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}
