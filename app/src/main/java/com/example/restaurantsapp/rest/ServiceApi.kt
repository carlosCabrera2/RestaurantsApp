package com.example.restaurantsapp.rest

import com.example.restaurantsapp.model.ReviewModel.ReviewResponse
import com.example.restaurantsapp.model.YelpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET(SEARCH_PATH)
    suspend fun getHotNewRestaurants(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("term") term: String = "restaurants",
        @Query("attributes") attributes: String = "hot_and_new",
        @Query("sort_by") sort: String = "best_match",
        @Query("limit") limit: Int = 20,
    ): Response<YelpResponse>



    @GET(PATH_ID + REVIEW_PATH)
    suspend fun getRestaurantReviews(
        @Path("id") id: String,
        @Query("sort_by") sort: String = "yelp_sort"
    ):Response<ReviewResponse>


    companion object {
        //'https://api.yelp.com/v3/businesses/
        // search?latitude=33.903438
        // &longitude=-84.48234&term=restaurants
        // &attributes=hot_and_new&sort_by=best_match&limit=20'
        //https://api.yelp.com/v3/businesses/business_id_or_alias/reviews?limit=5&sort_by=newest
        const val BASE_URL = "https://api.yelp.com/v3/businesses/"
        private const val SEARCH_PATH = "search"
        private const val REVIEW_PATH= "reviews"
        private const val PATH_ID= "{id}/"

    }

}