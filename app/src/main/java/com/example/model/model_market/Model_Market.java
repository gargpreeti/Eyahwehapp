package com.example.model.model_market;

import java.util.ArrayList;


public class Model_Market {

    private String filter_type;
    private String search_text;
    private String type;
    private String latitude;
    private String longitude;

   public ArrayList<ProductDetailMarket> al_product_detail_market = new ArrayList<ProductDetailMarket>();
   public ArrayList<ServiciesMarket> al_servicies_detail_market = new ArrayList<ServiciesMarket>();
    public ArrayList<EventMarket> al_event_detail_market = new ArrayList<EventMarket>();


    public String getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text= search_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }


    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<ProductDetailMarket> getAl_product_detail_market() {
        return al_product_detail_market;
    }

    public void setAl_product_detail_market(ArrayList<ProductDetailMarket> al_product_detail_market) {
        this.al_product_detail_market= al_product_detail_market;
    }

  public ArrayList<ServiciesMarket> getAl_servicies_detail_market() {
        return al_servicies_detail_market;
    }

    public void setAl_servicies_detail_market(ArrayList<ServiciesMarket> al_servicies_detail_market) {
        this.al_servicies_detail_market = al_servicies_detail_market;
    }

   public ArrayList<EventMarket> getAl_event_detail_market() {
        return al_event_detail_market;
    }

    public void setAl_event_detail(ArrayList<EventMarket> al_event_detail_market) {
        this.al_event_detail_market = al_event_detail_market;


    }}