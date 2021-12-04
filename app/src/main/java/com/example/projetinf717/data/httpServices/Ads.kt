package com.example.projetinf717.data.httpServices

import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projetinf717.Application


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

}