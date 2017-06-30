package com.example.model.model_profile;


public class Event {

    private String event_name;
    private String event_image;
    private double event_price;
    private int event_like;
    private int event_comment;
    private String event_id;
    private String event_liked;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public double getEvent_price() {
        return event_price;
    }

    public void setEvent_price(double event_price) {
        this.event_price = event_price;
    }

    public int getEvent_like() {
        return event_like;
    }

    public void setEvent_like(int event_like) {
        this.event_like = event_like;
    }

    public int getEvent_comment() {
        return event_comment;
    }

    public void setEvent_comment(int event_comment) {
        this.event_comment = event_comment;
    }


    public String getEvent_liked() {
        return event_liked;
    }

    public void setEvent_liked(String event_liked) {

        this.event_liked = event_liked;

    }

}