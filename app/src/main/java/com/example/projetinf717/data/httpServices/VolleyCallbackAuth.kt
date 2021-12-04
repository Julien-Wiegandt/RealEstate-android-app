package com.example.projetinf717.data.httpServices

import org.json.JSONObject

interface VolleyCallbackAuth {
    fun onSuccess(result: JSONObject?)
    fun onError()
}