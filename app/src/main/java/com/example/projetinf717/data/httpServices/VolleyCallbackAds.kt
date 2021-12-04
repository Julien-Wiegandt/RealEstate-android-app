package com.example.projetinf717.data.httpServices

import org.json.JSONArray

interface VolleyCallbackAds {
    fun onSuccess(result: JSONArray?)
    fun onError()
}