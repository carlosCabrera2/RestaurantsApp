package com.example.restaurantsapp.utils

import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.ReviewDomain
import com.example.restaurantsapp.model.domain.RestaurantDomain

sealed class ViewType {
        data class RestuarantList(val restaurantList: RestaurantDomain) : ViewType()
        data class ReviewList(val reviewList: ReviewDomain ) : ViewType()
    }