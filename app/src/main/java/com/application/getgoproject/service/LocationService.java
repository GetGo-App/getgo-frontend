package com.application.getgoproject.service;

import com.application.getgoproject.models.LocationComment;
import com.application.getgoproject.models.Locations;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LocationService {

    @GET("locations")
    Call<List<Locations>> getAllLocations(@Header("Authorization") String token);

    @GET("locations/{id}")
    Call<Locations> getLocationsById(@Path("id") int id, @Header("Authorization") String token);

    @GET("{city}/locations")
    Call<List<Locations>> getLocationsByCity(@Path("city") String city, @Header("Authorization") String token);

    @GET("locations/{id}/comments")
    Call<List<LocationComment>> getListLocationCommentById(@Path("id") int id, @Header("Authorization") String token);
}
