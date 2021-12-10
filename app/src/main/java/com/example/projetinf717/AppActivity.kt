package com.example.projetinf717

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.projetinf717.data.utils.TokenUtils
import com.example.projetinf717.databinding.ActivityAppBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_app)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_ads
            )
        )
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        Application.activityResumed()
        val isExpired : Boolean = TokenUtils.checkIfExpired()
        if(!isExpired){
            val appActivityIntent = Intent(applicationContext, AppActivity::class.java)
            startActivity(appActivityIntent)
            this.finish()
        }
    }

    override fun onPause() {
        super.onPause()
        Application.activityPaused()
    }
}