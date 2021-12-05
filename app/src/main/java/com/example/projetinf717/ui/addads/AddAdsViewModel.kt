package com.example.projetinf717.ui.addads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.data.httpServices.VolleyCallbackAds
import com.example.projetinf717.data.httpServices.VolleyCallbackJsonObject
import org.json.JSONArray
import org.json.JSONObject

class AddAdsViewModel : ViewModel() {
    private val ads = Ads()


    private val _text = MutableLiveData<String>().apply {
        value = "This is add ads Fragment"
    }
    val text: LiveData<String> = _text

    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action> {
        return mAction
    }


    fun createAd(title: String, address: String,desc : String,estateType: String,
                   estatePrice: String, numberBath: String, numberBed: String,
                   email: String, phone: String, rent: Boolean){
        val cb: VolleyCallbackJsonObject = object: VolleyCallbackJsonObject {
            override fun onSuccess(result: JSONObject?) {
                showAdsCreated()
            }
            override fun onError() {
                showInvalidArguments()
            }
        }
        ads.createAd(title,address,desc,estateType,estatePrice,numberBath,numberBed
        ,email,phone, rent, cb)

    }
    private fun showInvalidArguments() {
        mAction.value = Action(Action.SHOW_INVALID_FORM)
    }
    private fun showAdsCreated() {
        mAction.value = Action(Action.SHOW_AD_CREATED)
    }
}



class Action(val value: Int) {

    companion object {
        const val SHOW_AD_CREATED = 0
        const val SHOW_INVALID_FORM = 1
    }
}