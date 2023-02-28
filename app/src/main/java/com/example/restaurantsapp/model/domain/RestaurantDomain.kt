package com.example.restaurantsapp.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantsapp.model.Business

@Entity(tableName = "restaurants")
data class RestaurantDomain(
    @PrimaryKey
    val id: String,
    val image: String,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val distance: Double,
    var isFavorite: Boolean = false
)

fun List<Business>?.mapToDomainRestaurants(): List<RestaurantDomain> =
    this?.map {
        RestaurantDomain(
            id = it.id ?: "ID not available",
            image = it.imageUrl ?: "not available",
            name = it.name ?: "not available",
            phone = it.phone ?: "not available",
            price = it.price ?: "not available",
            rating = it.rating ?: 0.0,
            distance = it.distance ?: 0.0,
            isFavorite = false
        )
    } ?: emptyList()
