package com.example.projetinf717.data.httpServices

import org.json.JSONObject

interface VolleyCallbackJsonObject {
    fun onSuccess(result: JSONObject?)
    fun onError()
}