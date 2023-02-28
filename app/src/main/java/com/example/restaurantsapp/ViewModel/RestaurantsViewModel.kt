package com.example.restaurantsapp.ViewModel

import androidx.lifecycle.*
import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.ReviewDomain
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.rest.RestaurantRepository
import com.example.restaurantsapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    var bissinesId = ""
    var name = ""
    var phone = ""
    var image = ""
    var location = ""
    var rating = 0.0


    private val _restaurants: MutableLiveData<UIState<List<RestaurantDomain>>>
            = MutableLiveData((UIState.LOADING))
    val restaurants: LiveData<UIState<List<RestaurantDomain>>> get() = _restaurants


    private val _favoriteRestuarants: MutableLiveData<UIState<List<RestaurantDomain>>>
            = MutableLiveData(UIState.LOADING)
    val favoriteRestaurants: LiveData<UIState<List<RestaurantDomain>>> = _favoriteRestuarants

    private val _reviews: MutableLiveData<UIState<List<ReviewDomain>>>
            = MutableLiveData((UIState.LOADING))
    val reviews: LiveData<UIState<List<ReviewDomain>>>get() = _reviews



    init {
        getRestaurants()
        getReviews()

    }


    fun getReviews(id: String? = null) {
       id?.let{
            viewModelScope.launch(ioDispatcher) {
                restaurantRepository.getRestaurantReviews(id).collect{
                    _reviews.postValue(it)
                }
            }
        }

    }

    fun getRestaurants(lat: Double? = null, lon: Double? = null){
        if (lat != null && lon!= null){
            viewModelScope.launch(ioDispatcher){
                restaurantRepository.getRestaurants(lat, lon).collect{
                    _restaurants.postValue(it)
                }
            }

        }



        }

//        fun updateFavoriteRestaurants(restaurant: RestaurantDomain){
//            restaurant.isFavorite = !restaurant.isFavorite
//            viewModelScope.launch(ioDispatcher) {
//                if (restaurant.isFavorite){
//                    localRepository.insertRestaurant(restaurant)
//                }else{
//                    localRepository.deleteRestaurant(restaurant)
//                }
//            }
//        }
//
//        fun getFavoriteRestuarants(){
//            viewModelScope.launch(ioDispatcher){
//                _favoriteRestuarants.postValue(localRepository.getRestaurant())
//            }
//        }
}

