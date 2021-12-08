package com.example.projetinf717.data.httpServices

import android.net.Uri
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.projetinf717.Application
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.StringBuilder
import kotlin.collections.HashMap


class Ads {

    private var BOUNDARY =
        "s2retfgsGSRFsERFGHfgdfgw734yhFHW567TYHSrf4yarg" //This the boundary which is used by the server to split the post parameters.
    private var MULTIPART_FORMDATA = "multipart/form-data;boundary=$BOUNDARY"

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

<<<<<<< HEAD
    private fun createPostBody(params: Map<String, String?>?): String? {
        val sbPost = StringBuilder()
        if (params != null) {
            for (key in params.keys) {
                if (params[key] != null) {
                    sbPost.append("\r\n--$BOUNDARY\r\n")
                    sbPost.append("Content-Disposition: form-data; name=\"$key\"\r\n\r\n")
                    sbPost.append(params[key].toString())
                }
            }
        }
        return sbPost.toString()
    }

    fun hostImage(b64Image : String, cb : VolleyCallbackJsonObject){
        val queue = Volley.newRequestQueue(Application.appContext)
        val url = "https://api.imgbb.com/1/upload?key=${"ce5f8c85e3cbe0c433c7002c95659dcb"}"

        val jsonObject = JSONObject()

        val jsonRequest : JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, jsonObject,
            Response.Listener { response -> cb.onSuccess(response) },
            Response.ErrorListener { cb.onError() }) {
            override fun getBodyContentType(): String? {
                return MULTIPART_FORMDATA
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val map : MutableMap<String,String> = HashMap()
                map["image"] = b64Image
                return createPostBody(map)!!.toByteArray()
            }
        }

        queue.add(jsonRequest)
    }

    fun createAd(title: String, address: String,desc : String,estateType: String,
                   estatePrice: String, numberBath: String, numberBed: String,
                   email: String, phone: String, rent: Boolean, imgUri : String,
=======
    fun createAd(title: String, street: String, city: String, codePostal: String, country: String,desc : String,estateType: String,
                   estatePrice: String, numberBath: String, numberBed: String,
                   email: String, phone: String, rent: Boolean, latLng: LatLng,
>>>>>>> 94733d17ae807f11f6979fa80cce83e33ce3d0ca
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