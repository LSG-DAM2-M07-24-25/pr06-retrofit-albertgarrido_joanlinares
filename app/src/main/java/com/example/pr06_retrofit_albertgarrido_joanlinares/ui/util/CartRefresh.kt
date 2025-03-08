package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util

import androidx.lifecycle.MutableLiveData

object CartRefresh {
    // LiveData que indica cuándo se debe refrescar el carrito
    val refreshTrigger = MutableLiveData<Boolean>(false)
}