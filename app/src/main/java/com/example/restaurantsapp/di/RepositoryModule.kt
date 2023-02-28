package com.example.restaurantsapp.di


import com.example.restaurantsapp.rest.RestaurantRepository
import com.example.restaurantsapp.rest.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent



@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun RestaurantRepository(
            restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository



}