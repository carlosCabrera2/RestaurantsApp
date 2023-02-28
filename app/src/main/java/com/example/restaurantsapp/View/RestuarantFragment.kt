package com.example.restaurantsapp.View

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsapp.View.Adapter.RestaurantAdapter
import com.example.restaurantsapp.R
import com.example.restaurantsapp.databinding.CommonRecyclerViewBinding
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.UIState
import com.example.restaurantsapp.utils.ViewType
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private const val TAG = "RestuarantFragment"

class RestuarantFragment : BaseFragment() {

    private val restaurantAdapter by lazy{
        RestaurantAdapter{item->
            restaurantsViewModel.bissinesId = item.id
            restaurantsViewModel.name = item.name
            restaurantsViewModel.phone = item.phone
            restaurantsViewModel.rating = item.rating
            restaurantsViewModel.image = item.image
            restaurantsViewModel.location = item.distance.toString()
            findNavController().navigate(R.id.action_menu_home_to_menu_detail)
        }
    }

    private val binding by lazy{
        CommonRecyclerViewBinding.inflate(layoutInflater)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
                binding.rvCommonView.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = restaurantAdapter
                }

                getUserLocation()

                getRestaurants()
            return binding.root
        }


    private fun getUserLocation(){
            val task = fusedLocationClient.lastLocation
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    0
                )
                return
            }
                task.addOnSuccessListener {
                    it?.let {
                        restaurantsViewModel.getRestaurants(it.latitude, it.longitude)
                        Toast.makeText(requireContext(), "lat: ${it.latitude}", Toast.LENGTH_SHORT).show()

                    }
                }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            }
            else -> {}

        }
    }

    override fun onResume() {
        super.onResume()
        getUserLocation()
    }



    private fun getRestaurants(){
        restaurantsViewModel.restaurants.observe(viewLifecycleOwner){state->
            val  listVT: MutableList<ViewType> = mutableListOf()

            when(state){
                is UIState.LOADING ->{
                    Log.d(TAG, "getRestaurants: loading ")
                }
                is UIState.SUCCESS<List<RestaurantDomain>> ->{
                 state.response.forEach{
                        Log.d(TAG, "getRestaurants: success")
                        listVT.add(ViewType.RestuarantList(it))
                    }
                    restaurantAdapter.updateItems(listVT)
                }
                is UIState.ERROR ->{
                    Log.d(TAG, "getRestaurants: Error")
                    state.error.localizedMessage?.let{
                        throw Exception("Error")
                    }
                }
            }
        }
    }

}