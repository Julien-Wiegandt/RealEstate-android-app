package com.example.projetinf717.data.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.projetinf717.Application
import com.example.projetinf717.MainActivity
import com.example.projetinf717.R
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

class NotificationsService : Service() {
    var handler: Handler? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private lateinit var mSocket: Socket

    override fun onCreate() {
        super.onCreate()

        try {
            val options : IO.Options = IO.Options.builder().setUpgrade(false).build()
            mSocket = IO.socket("http://"+Application.IP+":8000", options)
        } catch (e: URISyntaxException) {
            print(e)
        }
        mSocket.connect();

        handler = Handler()

    }

    private fun runOnUiThread(runnable: Runnable) {
        handler!!.post(runnable)
    }

    //example to update
    private val onResultBet = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val json : JSONObject = args[0] as JSONObject
            val win : Boolean = json.getBoolean("win")
            val amount : Double = json.getDouble("amount")
            val title : String
            val text : String
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            if(win){
                title = "Vous avez gagné votre pari"
                text = "Vous avez gagné $amount euros"
            }else{
                title = "Vous avez perdu votre pari"
                text = "Vous avez perdu $amount euros"
            }
            createNotification(title,text,intent,Application.getIdNotifParis())
        })

    }



    private fun createNotification(title: String, text: String, intent: Intent, id: Int){
        if(Application.isActivityVisible()){
            Toast.makeText(applicationContext,text, Toast.LENGTH_SHORT).show();
        }else{
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val builder = NotificationCompat.Builder(this, "Notifications")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(id, builder.build())
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags_: Int, startId: Int): Int {
        val jsonObj = JSONObject()
        jsonObj.put("userId",Application.getID())
        mSocket.emit("login", jsonObj)
        mSocket.on("newAd",onResultBet)
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
        mSocket.off()
    }




}