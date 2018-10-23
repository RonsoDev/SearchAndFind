package com.example.solom.finalproject020618.db;

public class Place {

    private String address;
    private double lat;
    private double lng;
    private String icon;
    private String locationName;
   // private String place_id;
    private double rating;
    //private String type;
   // private int height;
    private String photoRef;
    //private int width;
    private double distance;
    private int id;
    private String converter;

    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance, int id, String converter) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
        this.distance = distance;
        this.id = id;
        this.converter = converter;
    }

    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance, String converter) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
        this.distance = distance;
        this.converter = converter;
    }

    public String getConverter() {

        return converter;
    }

    public void setConverter(String converter) {
        converter = converter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance, int id) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
        this.distance = distance;
        this.id = id;
    }

    /*public Place(String address, double lat, double lng, String icon, String locationName, double rating, String type, String photoRef, double distance) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.type = type;
        this.photoRef = photoRef;
        this.distance = distance;
    }*/

/*    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
        this.distance = distance;
    }*/

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

/*    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
    }*/

/*    public Place(String address, double lat, double lng, String icon, String locationName, double rating, String type, String photoRef) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.type = type;
        this.photoRef = photoRef;
    }*/


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

 /*   public Place(double lat, double lng, String icon, String locationName, double rating, String photoRef, String address) {
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
        this.address = address;
    }*/

/*
    public Place(double lat, double lng, String icon, String locationName, double rating, String photoRef) {
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.photoRef = photoRef;
    }
*/

/*    public Place(double lat, double lng, String icon, String locationName, double rating) {
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
    }*/
/*
    public Place(int height, String photoRef, int width) {
        this.height = height;
        this.photoRef = photoRef;
        this.width = width;
    }*/


/*    public Place(double lat, double lng, String icon, String locationName, double rating, String type,
                 int height, String photoRef, int width) {
        this.lat = lat;
        this.lng = lng;
        this.icon = icon;
        this.locationName = locationName;
        this.rating = rating;
        this.type = type;
        this.height = height;
        this.photoRef = photoRef;
        this.width = width;
    }*/

/*    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }*/

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

/*    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }*/

/*    public Place(String type) {
        this.type = type;
    }*/


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


/*    public String getPlace_id() {
        return place_id;
    }*/

/*    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }*/

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

/*    public String getType() {
        return type;
    }*/

/*    public void setType(String type) {
        this.type = type;
    }*/

    @Override
    public String toString() {
        return "Place{" +
                "address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", icon='" + icon + '\'' +
                ", locationName='" + locationName + '\'' +
                ", photoRef='" + photoRef + '\'' +
/*
                ", place_id='" + place_id + '\'' +
*/
                ", rating=" + rating +
                ", distance=" + distance +
                ", converter=" + converter +


                '}';
    }
}
