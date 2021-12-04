package com.example.projetinf717.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.Application
import com.example.projetinf717.data.httpServices.Authentication
import com.example.projetinf717.data.httpServices.VolleyCallbackAuth
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Real Estate login"
    }
    private val authentication = Authentication()

    val text: LiveData<String> = _text

    //Stores actions for view.
    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action> {
        return mAction
    }

    fun userWantToLogin(password: String, login: String) {
        val cb:VolleyCallbackAuth = object: VolleyCallbackAuth{
            override fun onSuccess(result: JSONObject?) {
                if (result != null) {
                    Application.JWT = result.get("jwt") as String?
                }
                showWelcomeScreen()
            }
            override fun onError() {
                showPasswordOrLoginInvalid()
            }
        }
        authentication.login(login, password, cb)

    }


    /*
         * Changes LiveData. Does not act directly with view.
         * View can implement any way to show info
          * to user (show new activity, alert or toast)
         */
    private fun showPasswordOrLoginInvalid() {
        mAction.value = Action(Action.SHOW_INVALID_PASSWARD_OR_LOGIN)
    }

    /*
         * Changes LiveData. Does not act directly with view.
         * View can implement any way to show info
         * to user (show new activity, alert or toast)
         */
    private fun showWelcomeScreen() {
        mAction.value = Action(Action.SHOW_WELCOME)
    }


}

class Action(val value: Int) {

    companion object {
        const val SHOW_WELCOME = 0
        const val SHOW_INVALID_PASSWARD_OR_LOGIN = 1
    }
}