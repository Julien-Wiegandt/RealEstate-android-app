package com.example.projetinf717

import android.Manifest
import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projetinf717.data.services.NotificationsService
import com.example.projetinf717.data.utils.TokenUtils
import java.util.ArrayList
import kotlin.random.Random

class Application : Application() {
    companion object{
        var IP = "10.238.67.214:3000/api"
        var IPSocket = IP.split(":")[0]
        var JWT: String? = null
        var appContext: Context? = null
        var allowNotifications = false
        //true = map and false = list
        var homeListOrMap: Boolean = false
        private var idNotifs : Int = 1000

        fun getIdNotifParis()  : Int{
            idNotifs += 1
            return idNotifs
        }
        private var ID : Int? = null


        fun getID(): Int?{
            if(ID === null){
                ID = TokenUtils.getId()
            }
            return ID
        }

        fun isActivityVisible(): Boolean {
            return activityVisible
        }

        fun activityResumed() {
            activityVisible = true
        }

        fun activityPaused() {
            activityVisible = false
        }

        private var activityVisible = false
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences : SharedPreferences = applicationContext.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        allowNotifications = sharedPreferences.getBoolean("allowNotifs",true)
        appContext = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        createNotificationChannel()

    }



    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notifications", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }




}