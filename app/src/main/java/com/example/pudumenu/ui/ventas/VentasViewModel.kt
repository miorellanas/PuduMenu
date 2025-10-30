package com.example.pudumenu.ui.ventas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VentasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ventas Fragment"
    }
    val text: LiveData<String> = _text
}