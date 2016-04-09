package com.example.richmond.uspotmobileapplication;

/**
 * Created by Richmond on 02/03/2016.
 */
public class Spot {
    private long id;
    private String spotName;
    private String spotType;
    private String spotImage;
    private int spotRating;


    public Spot(){

    }
    public Spot (long id, String spotName, String spotType, String spotImage, int spotRating){
        this.id = id;
        this.spotName = spotName;
        this.spotType = spotType;
        this.spotImage = spotImage;
        this.spotRating = spotRating;
    }

    public long getid(){
        return id;
    }

    public void setid(long id){
        this.id = id;
    }

    public String getSpotName(){
        return spotName;
    }

    public void setSpotName(String spotName){
        this.spotName = spotName;
    }

    public String getSpotType(){
        return spotName;
    }

    public void setSpotType(String spotType){
        this.spotType = spotType;
    }

    public String getSpotImage(){
        return spotName;
    }

    public void setSpotImage(String spotImage){
        this.spotImage = spotImage;
    }

    public int getSpotRating(){
        return spotRating;
    }

    public void setSpotRating(int spotRating){
        this.spotRating = spotRating;
    }

    public String toString(){
        return "ID: " +id+
                "SpotName "+spotName+
                "SpotType" +spotType+
                "SpotImage" +spotImage+
                "SpotRating" +spotRating;
    }
}
