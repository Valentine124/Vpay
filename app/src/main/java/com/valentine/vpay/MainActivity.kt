package com.valentine.vpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.valentine.vpay.databinding.ActivityMainBinding
import com.valentine.vpay.main.VpayApplication

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.transactionsFragment, R.id.historyFragment))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
}