package com.example.restaurantsapp.View

import android.app.AlertDialog
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantsapp.ViewModel.RestaurantsViewModel
import com.example.restaurantsapp.databinding.CommonRecyclerViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    protected val bindingRv: CommonRecyclerViewBinding by lazy{
            CommonRecyclerViewBinding.inflate(layoutInflater)
    }

    protected val restaurantsViewModel: RestaurantsViewModel by lazy {
           ViewModelProvider(requireActivity())[RestaurantsViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
    }

    protected fun showError(message: String, action:()->Unit){
        AlertDialog.Builder(requireActivity())
            .setTitle("Error Occurred")
            .setMessage(message)
            .setPositiveButton("RETRY") { dialog, _ ->
                action()
                dialog.dismiss()
            }
            .setNegativeButton("DISMISS") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


}

