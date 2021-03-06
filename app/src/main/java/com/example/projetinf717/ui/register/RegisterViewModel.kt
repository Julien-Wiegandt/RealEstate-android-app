package com.example.projetinf717.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.data.httpServices.Authentication
import com.example.projetinf717.data.httpServices.VolleyCallbackJsonObject
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    //Stores actions for view.
    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    private val authentication = Authentication()


    fun getAction(): LiveData<Action> {
        return mAction
    }

    fun userWantToRegister(name: String, mail: String, password: String, repassword: String ) {
        if(password != repassword){
            showPasswordsNotCorresponding()
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            showBadMail()
        }else{
            val cb: VolleyCallbackJsonObject = object: VolleyCallbackJsonObject {
                override fun onSuccess(result: JSONObject?) {
                    showUserRegistered()
                }
                override fun onError() {
                    showInvalidArguments()
                }
            }
            authentication.register(name,mail,password,repassword, cb)
        }

    }

    private fun showInvalidArguments() {
        mAction.value = Action(Action.INVALID_ARGUMENTS)
    }
    private fun showUserRegistered() {
        mAction.value = Action(Action.REGISTERED)
    }
    private fun showPasswordsNotCorresponding() {
        mAction.value = Action(Action.PASSWORDS_DOES_NOT_CORRESPOND)
    }
    private fun showBadMail() {
        mAction.value = Action(Action.INVALID_MAIL)
    }
}

class Action(val value: Int) {

    companion object {
        const val REGISTERED = 0
        const val INVALID_MAIL = 1
        const val PASSWORDS_DOES_NOT_CORRESPOND = 2
        const val INVALID_ARGUMENTS = 3
    }
}