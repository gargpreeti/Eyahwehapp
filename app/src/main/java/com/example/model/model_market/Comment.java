package com.example.model.model_market;


public class Comment {
    private String id;
    private String name;
    private String comment;
    private String userid;



    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getUserid() {

        return userid;
    }

    public void setUserid(String userid) {

        this.userid =userid;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment= comment;
    }




}