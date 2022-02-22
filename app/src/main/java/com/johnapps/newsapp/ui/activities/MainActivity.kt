package com.johnapps.newsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.johnapps.newsapp.R
import com.johnapps.newsapp.databinding.ActivityMainBinding
import com.johnapps.newsapp.ui.viewmodel.NewsViewModel
import com.johnapps.newsapp.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        viewModel = ViewModelProvider(this, ViewModelFactory(application))[NewsViewModel::class.java]

        // val navView: BottomNavigationView = findViewById(R.id.bottom_navigation_bar)

       /* val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainNewsFragment, R.id.details_fragment))

        setupActionBarWithNavController(navController, appBarConfiguration)*/

        //navView.setupWithNavController(navController)

    }





}