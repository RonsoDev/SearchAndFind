package com.example.solom.finalproject020618.network;

public interface OnLocationClick {

   // void onLocationClick (double lat, double lng, String address, String name, double distance, String photoRef, double rating, String converter);
    void onLocationClick (String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance, String converter);

}
