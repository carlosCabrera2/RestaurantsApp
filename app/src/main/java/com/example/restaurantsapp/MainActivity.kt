package com.example.restaurantsapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.restaurantsapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        binding.bottomNav.setupWithNavController(hostFragment.navController)
        setupActionBarWithNavController(hostFragment.navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.frag_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}