package com.example.projetinf717.ui.ads

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.data.httpServices.VolleyCallbackAds
import org.json.JSONArray
import org.json.JSONObject

class AdsViewModel : ViewModel() {

    private val mAction: MutableLiveData<AdAction> = MutableLiveData<AdAction>()

    var adsArray = JSONArray()

    fun getAction(): LiveData<AdAction> {
        return mAction
    }
    private val ads = Ads()

    private val _text = MutableLiveData<String>().apply {
        value = "This is ad Fragment"
    }

    fun displayAds(){
        val cb: VolleyCallbackAds = object: VolleyCallbackAds {
            override fun onSuccessObject(result: JSONObject) {
                if (result != null) {
                    adsArray = result.getJSONArray("housings") as JSONArray
                }
                showDataLoaded()
            }
            override fun onSuccessArray(result: JSONArray) {
            // Not used
            }
            override fun onError() {
                showNetworkError()
            }
        }
        ads.getMyHouses(cb)

    }

    private fun showDataLoaded() {
        mAction.value = AdAction(AdAction.HOMES_LOADED)

    }
    private fun showNetworkError() {
        mAction.value = AdAction(AdAction.NETWORK_ERROR)
    }
}

class AdAction(val value: Int) {
    companion object {
        const val HOMES_LOADED = 0
        const val NETWORK_ERROR = 1
    }
}