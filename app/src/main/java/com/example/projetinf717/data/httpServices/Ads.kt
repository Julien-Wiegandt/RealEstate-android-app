package com.example.projetinf717.data.httpServices

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.projetinf717.Application
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject


class Ads {

    fun getHouses( callback: VolleyCallbackAds) {
        val queue = Volley.newRequestQueue(Application.appContext)
        val url = "http://" + Application.IP + "/houses"

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                callback.onSuccess(response)

            },
            { _ ->
                callback.onError()
            })
        queue.add(jsonRequest)

    }

    fun createAd(title: String, street: String, city: String, codePostal: String, country: String,desc : String,estateType: String,
                   estatePrice: String, numberBath: String, numberBed: String,
                   email: String, phone: String, rent: Boolean, latLng: LatLng,
                   callback: VolleyCallbackJsonObject ){
        val queue = Volley.newRequestQueue(Application.appContext)
        val url = "http://" + Application.IP + "/housings"

        val jsonObject = JSONObject()
        jsonObject.put("title",title)
        jsonObject.put("street",street)
        jsonObject.put("city",city)
        jsonObject.put("codePostal",codePostal)
        jsonObject.put("country",country)
        jsonObject.put("description",desc)
        jsonObject.put("estateType",estateType)
        jsonObject.put("estatePrice",estatePrice.toInt())
        jsonObject.put("numberBath",numberBath.toInt())
        jsonObject.put("numberBed",numberBed.toInt())
        jsonObject.put("email",email)
        jsonObject.put("phone",phone)
        jsonObject.put("rent",rent)
        jsonObject.put("latLong",latLng)

        val jsonRequest : JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, jsonObject,
            Response.Listener<JSONObject?> { response -> callback.onSuccess(response) },
            Response.ErrorListener { callback.onError() }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/json; charset=UTF-8"
                params["Authorization"] = "Bearer " + Application.JWT
                return params
            }
        }

        queue.add(jsonRequest)


    }

}