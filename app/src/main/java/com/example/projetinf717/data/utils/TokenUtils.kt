package com.example.projetinf717.data.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.example.projetinf717.Application

class TokenUtils {
    companion object{
        fun checkIfExpired(): Boolean {
            val sharedPreferences: SharedPreferences? =
                Application.appContext?.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
            val token : String? = sharedPreferences?.getString("jwt", null)
            if(token != null) {
                Application.JWT = token
                val jwt = JWT(token)
                return(jwt.isExpired(10))
            }
            return true
        }

    }
}