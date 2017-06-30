package com.example.model.model_friends;

 public class CommentDetail {

    private String id;
    private String comment;
    private String comment_date;
     private String username;
     private String image;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date =comment_date;
    }

     public String getUsername() {
         return username;
     }

     public void setUsername(String username) {
         this.username =username;
     }
     public String getImage() {
         return image;
     }

     public void setImage(String image) {
         this.image =image;
     }

}
