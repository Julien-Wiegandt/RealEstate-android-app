package com.example.projetinf717.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text
    val jwt = null
    //Stores actions for view.
    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action>? {
        return mAction
    }

    fun userWantToLogin(password: String, login: String) {
        if (validateInfo(password, login)) {
            showWelcomeScreen()
        } else {
            showPasswordOrLoginInvalid()
        }
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

    private fun validateInfo(password: String, login: String): Boolean {
        return password == "2121" && login == "admin"
    }
}

class Action(val value: Int) {

    companion object {
        const val SHOW_WELCOME = 0
        const val SHOW_INVALID_PASSWARD_OR_LOGIN = 1
    }
}