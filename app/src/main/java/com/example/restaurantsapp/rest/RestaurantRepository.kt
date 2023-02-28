package com.example.restaurantsapp.rest

import android.util.Log
import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.ReviewDomain
import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.mapToDomainReview
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.model.domain.mapToDomainRestaurants
import com.example.restaurantsapp.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "RestaurantRepository"
interface RestaurantRepository {
    fun getRestaurants(lat : Double, long: Double ): Flow<UIState<List<RestaurantDomain>>>
    fun getRestaurantReviews( id: String): Flow<UIState<List<ReviewDomain>>>
}

class RestaurantRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
    ) : RestaurantRepository {

        override fun getRestaurants(lat: Double, long: Double):
                Flow<UIState<List<RestaurantDomain>>> = flow {
            emit(UIState.LOADING)

            try {
                val response = serviceApi.getHotNewRestaurants(lat, long)
                if(response.isSuccessful){
                    Log.d(TAG, "getRestaurants: isSuccessful")
                        response.body()?.let {
                            Log.d("Here", "getRestaurants: ok")
                            emit(UIState.SUCCESS(it.businesses.mapToDomainRestaurants()))
                        }?: throw NullRestaurantResponse()
                    Log.d(TAG, "getRestaurants:  isSuccessful null")
                }else{
                    throw FailureResponse(response.errorBody()?.string())
                    Log.d(TAG, "getRestaurants: isSuccessful Failure")
                }
            }catch (e: Exception){
                emit(UIState.ERROR(e))
                Log.d(TAG, "getRestaurants: Error ")
            }

        }

        override fun getRestaurantReviews(id: String):
                Flow<UIState<List<ReviewDomain>>> = flow {
            emit(UIState.LOADING)

            try{
                val response = serviceApi.getRestaurantReviews(id)
                if(response.isSuccessful){
                    Log.d(TAG, "getRestaurantReviews: isSuccessful ")
                    response.body()?.let{
                        emit(UIState.SUCCESS(it.reviews.mapToDomainReview()))

                    }?:throw NullReviewResponse()
                    Log.d(TAG, "getRestaurantReviews: null")
                }else{
                    throw FailureResponse(response.errorBody()?.string())
                    Log.d(TAG, "getRestaurantReviews:  failure")
                }
            }catch(e: Exception){
                emit(UIState.ERROR(e))
                Log.d(TAG, "getRestaurantReviews: error")
            }
        }

}