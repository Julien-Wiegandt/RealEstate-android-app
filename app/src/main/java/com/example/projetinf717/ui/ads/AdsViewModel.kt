package com.example.projetinf717.ui.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdsViewModel : ViewModel() {

        private val _text = MutableLiveData<String>().apply {
            value = "This is ads Fragment"
        }
        val text: LiveData<String> = _text
}