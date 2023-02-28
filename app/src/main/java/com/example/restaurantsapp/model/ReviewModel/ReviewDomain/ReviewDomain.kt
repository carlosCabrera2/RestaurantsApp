package com.example.restaurantsapp.model.ReviewModel.ReviewDomain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantsapp.model.ReviewModel.Review


@Entity(tableName = "reviews")
data class ReviewDomain (
        @PrimaryKey
        val businessID: String,
        val userId: String,
        val userImgUrl: String,
        val userName: String,
        val text: String,
        val rating: Double,
        val timeCreated: String
)

fun List<Review>?.mapToDomainReview(): List<ReviewDomain> = this?.map{
    ReviewDomain(
        businessID = it.id ?: "no ID available",
        userId= it.user?.id ?: "no users rated this restaurant",
        userImgUrl = it.user?.imageUrl ?: "no image available",
        userName = it.user?.name ?: "no user name available",
        text = it.text ?: "no text available",
        rating = it.rating ?: 0.0,
        timeCreated = (it.timeCreated ?: 0) as String
    )

}?: emptyList()