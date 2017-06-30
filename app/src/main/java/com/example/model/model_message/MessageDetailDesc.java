package com.example.model.model_message;

public class MessageDetailDesc {

    private String sender;
    private String msg;
    private String user_image;
     private String msg_date;
     private String msg_thread;
    private String name;

    public String getSender() {

        return sender;
    }

    public void setSender(String sender) {

        this.sender= sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }


    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }


     public String getMsg_date() {
         return msg_date;
     }

     public void setMsg_date(String msg_date) {
         this.msg_date=msg_date;
     }

     public String getMsg_thread() {
         return msg_thread;
     }

     public void setMsg_thread(String msg_thread) {
         this.msg_thread=msg_thread;
     }

}
