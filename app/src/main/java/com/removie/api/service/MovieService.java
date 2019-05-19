package com.removie.api.service;

import com.removie.model.MovieResponse;
import com.removie.model.CreditsResponse;
import com.removie.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
Created by twin on May 11, 2019
*/

public interface MovieService {

    @GET("movie/{sort}")
    Call<MovieResponse> listOfMovie(@Path("sort") String sort,
                                     @Query("api_key") String apiKey);

    //popular page 1 - page 5
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
    @GET("movie/popular?page=2")
    Call<MovieResponse> getPopularMovies2(@Query("api_key") String apiKey);
    @GET("movie/popular?page=3")
    Call<MovieResponse> getPopularMovies3(@Query("api_key") String apiKey);
    @GET("movie/popular?page=4")
    Call<MovieResponse> getPopularMovies4(@Query("api_key") String apiKey);
    @GET("movie/popular?page=5")
    Call<MovieResponse> getPopularMovies5(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated?page=2")
    Call<MovieResponse> getTopRatedMovies2(@Query("api_key") String apiKey);
    @GET("movie/top_rated?page=3")
    Call<MovieResponse> getTopRatedMovies3(@Query("api_key") String apiKey);
    @GET("movie/top_rated?page=4")
    Call<MovieResponse> getTopRatedMovies4(@Query("api_key") String apiKey);
    @GET("movie/top_rated?page=5")
    Call<MovieResponse> getTopRatedMovies5(@Query("api_key") String apiKey);


    @GET("movie/now_playing?region=us")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey);
    @GET("movie/now_playing?region=us&page=2")
    Call<MovieResponse> getNowPlayingMovies2(@Query("api_key") String apiKey);
    @GET("movie/now_playing?region=us&page=3")
    Call<MovieResponse> getNowPlayingMovies3(@Query("api_key") String apiKey);

    @GET("movie/upcoming?region=us")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    //Trailers
    @GET("movie/{id}/videos")
    Call<TrailersResponse> trailerMovie(@Path("id") long id, @Query("api_key") String apiKey);

    //Credits
    @GET("movie/{id}/reviews")
    Call<CreditsResponse> reviewsMovie(@Path("id") long id, @Query("api_key") String apiKey);

    //credits
    @GET("movie/{id}/credits")
    Call<CreditsResponse> creditsMovie(@Path("id") long id, @Query("api_key") String apiKey);
}
