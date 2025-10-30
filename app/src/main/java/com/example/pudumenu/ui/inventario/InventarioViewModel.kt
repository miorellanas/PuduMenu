package com.example.pudumenu.ui.inventario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InventarioViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is inventario Fragment"
    }
    val text: LiveData<String> = _text
}