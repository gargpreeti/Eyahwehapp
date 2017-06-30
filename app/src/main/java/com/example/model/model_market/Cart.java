package com.example.model.model_market;


public class Cart {

    private String item_type;
    private String item_quantity;
    private String item_price;
    private String item_image;
    private String item_name;
    private int item_qty;
    private int itemid;
    private String mrchntid;
    private String seller_name;
    private String admin_id;
    private String seller_id;


    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity =item_quantity;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price=item_price;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image=item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name=item_name;
    }


    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id=admin_id;
    }


    public String getMrchntid() {
        return mrchntid;
    }

    public void setMrchntid(String mrchntid) {
        this.mrchntid=mrchntid;
    }



    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name=seller_name;
    }



    public int getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(int item_qty) {
        this.item_qty=item_qty;
    }


    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id=seller_id;
    }

}

