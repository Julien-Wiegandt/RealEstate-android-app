package com.example.projetinf717.data.httpServices

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.projetinf717.Application
import org.json.JSONObject

class Authentication() {

    private val queue = Volley.newRequestQueue(Application.appContext)
    private val url = "http://"+ Application.IP+":3000/login"

    fun login(mail: String, password: String){
        val jsonObject = JSONObject()
        jsonObject.put("mail",mail)
        jsonObject.put("password",password)
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                //TODO: store jwt in Application class
                /*jsp si ce sera utile callback.onSuccess(response);*/
            },
            {error ->

            })

    }
}

/*jsp si ce sera utile
interface VolleyCallback {
    fun onSuccess(result: JSONObject?) : JSONObject?
}*/
