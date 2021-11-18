package com.example.projetinf717

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.projetinf717.data.services.NotificationsService
import kotlin.random.Random

class Application : Application() {
    companion object{
        var IP = "localhost"
        var JWT: String? = null
        var appContext: Context? = null
        private var idNotifs : Int = 1000

        fun getIdNotifParis()  : Int{
            idNotifs += 1
            return idNotifs
        }
        private var ID : String? = null
        private fun generateID() : String{
            val alphabet = "ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789"
            val length = alphabet.length -1
            var key = ""
            for(i in 0..10){
                val indice = Random.nextInt(0, length)
                key+=alphabet[indice]
            }
            return key
        }

        fun getID(): String?{
            if(ID === null){
                ID = generateID()
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
        appContext = applicationContext
        createNotificationChannel()
        val serviceIntent = Intent(applicationContext, NotificationsService::class.java)
        startService(serviceIntent)

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