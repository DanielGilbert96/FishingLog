package com.example.dan.totalfishing;

/**
 * Created by dan on 23/11/2017.
 */

public class Fishing {
    private String datetime;
    private String image;
    private String location;
    private String method;
    private String specie;
    private String weight;

    public Fishing(){

    }

    public Fishing(String datetime, String image, String location, String method, String specie, String weight) {
        this.datetime = datetime;
        this.image = image;
        this.location = location;
        this.method = method;
        this.specie = specie;
        this.weight = weight;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}