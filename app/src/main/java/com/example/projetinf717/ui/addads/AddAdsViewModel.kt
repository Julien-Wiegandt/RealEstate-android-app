package com.example.projetinf717.ui.addads

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.data.httpServices.VolleyCallbackJsonObject
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
                   email: String, phone: String, rent: Boolean, b64Image : String){
        val uploadImageCb : VolleyCallbackJsonObject = object: VolleyCallbackJsonObject {
            override fun onSuccess(result: JSONObject?) {
                val addHousingCb: VolleyCallbackJsonObject = object: VolleyCallbackJsonObject {
                    override fun onSuccess(result: JSONObject?) {
                        showAdsCreated()
                    }
                    override fun onError() {
                        showInvalidArguments()
                    }
                }
                println("onSuccess")
                println(result)
                if(result != null){
                    val data = result.get("data") as JSONObject?
                    if(data != null){
                        val url = data.get("url") as String
                        println("url")
                        println(url)
                        ads.createAd(title,address,desc,estateType,estatePrice,numberBath,numberBed
                            ,email,phone, rent, url,addHousingCb)
                    }
                }
            }

            override fun onError() {
                println("err")
            }
        }
        ads.hostImage(b64Image, uploadImageCb)

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