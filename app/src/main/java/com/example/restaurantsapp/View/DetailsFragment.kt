package com.example.restaurantsapp.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantsapp.View.Adapter.RestaurantAdapter
import com.example.restaurantsapp.R
import com.example.restaurantsapp.databinding.FragmentDetailItemBinding
import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.ReviewDomain
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.utils.ViewType
import com.google.android.gms.location.FusedLocationProviderClient


class DetailsFragment: BaseFragment() {

    private val binding by lazy {
        FragmentDetailItemBinding.inflate(layoutInflater)
    }


    private val restaurantAdapter by lazy {
        RestaurantAdapter {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvDetails.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
             adapter = restaurantAdapter
        }

        restaurantsViewModel.getReviews(restaurantsViewModel.bissinesId)
        getReviews()
        getBussinesDetails()
        //getReviews()
        return binding.root
    }

    private fun getBussinesDetails() {

        Glide
            .with(binding.root)
            .load(restaurantsViewModel.image)
            .centerCrop()
            .placeholder(R.drawable.baseline_fastfood_24)
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.imageDetail)


        binding.tvDetailName.text = restaurantsViewModel.name

        var distance = restaurantsViewModel.location.toFloat() * 0.000621
        binding.tvAddrress.text = "${distance.toString().substring(0, 4)} miles away "
        binding.tvDetailPhone.text = restaurantsViewModel.phone
        binding.rbDetails.rating = restaurantsViewModel.rating.toFloat()
    }

    private fun getReviews() {
        restaurantsViewModel.reviews.observe(viewLifecycleOwner) { state ->
            val listVT: MutableList<ViewType> = mutableListOf()

            when (state) {
                is UIState.LOADING -> {

                }
                is UIState.SUCCESS<List<ReviewDomain>> -> {
                    state.response.forEach {

                        listVT.add(ViewType.ReviewList(it))
                    }
                    restaurantAdapter.updateItems(listVT)
                }
                is UIState.ERROR -> {

                    state.error.localizedMessage?.let {
                        throw Exception("Error")
                    }
                }
            }
        }
    }
}