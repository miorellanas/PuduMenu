package com.example.pudumenu.ui.estadisticas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EstadisticasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is estadisticas Fragment"
    }
    val text: LiveData<String> = _text
}