package com.example.projetinf717.data.httpServices

import org.json.JSONObject

interface VolleyCallbackAds {
    fun onSuccess(result: JSONObject)
    fun onError()
}