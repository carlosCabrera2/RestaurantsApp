package com.example.restaurantsapp.utils

class NullRestaurantResponse(message: String = "The Hot and New Restaurants Response is Null"): Exception(message)
class NullReviewResponse(message: String= "There are no reviews (Null)"): Exception(message)
class FailureResponse(message:String?) : Exception(message)

class NoLocationPermissionResponse(message: String = "Location Services was not permitted"): Exception(message)