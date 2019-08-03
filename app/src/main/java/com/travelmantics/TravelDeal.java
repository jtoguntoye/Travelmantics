package com.travelmantics;

import java.io.Serializable;

//to make it possible for TravelDeal objects to be passed using intentextra we implement
// the serializable interface
public class TravelDeal implements Serializable {
    private String ID;
    private  String title;
    private  String price;
    private  String Description;
    private  String imageUrl;

    public  TravelDeal(){}

    public TravelDeal( String title, String price, String description, String imageUrl) {

        this.setTitle(title);
        this.setPrice(price);
        setDescription(description);
        this.setImageUrl(imageUrl);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
