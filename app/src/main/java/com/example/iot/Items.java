package com.example.iot;

public class Items {
    private String titles, Description;
    private String First;
    private int Images;

    public Items(String first, String titles, String description , int images) {
        this.titles = titles;
        Description = description;
        First = first;
        Images = images;
    }

    public String getTitles() {
        return titles;
    }

    public String getDescription() {
        return Description;
    }

    public String getFirst() {
        return First;
    }

    public int getImages() {
        return Images;
    }
}
