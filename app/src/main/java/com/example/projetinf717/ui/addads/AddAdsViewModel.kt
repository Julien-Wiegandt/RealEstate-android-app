package com.example.projetinf717.ui.addads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddAdsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add ads Fragment"
    }
    val text: LiveData<String> = _text
}