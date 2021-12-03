package com.example.projetinf717.data.httpServices

import org.json.JSONObject

interface VolleyCallback {
    fun onSuccess(result: JSONObject?)
    fun onError()
}