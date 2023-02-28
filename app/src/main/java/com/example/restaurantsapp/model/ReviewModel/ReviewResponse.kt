package com.example.restaurantsapp.model.ReviewModel


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("possible_languages")
    val possibleLanguages: List<String?>? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("total")
    val total: Int? = null
)