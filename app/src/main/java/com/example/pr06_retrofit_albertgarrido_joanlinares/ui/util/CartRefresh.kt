package com.example.pr06_retrofit_albertgarrido_joanlinares.ui.util

import androidx.lifecycle.MutableLiveData

/**
 * Objeto singleton utilizado para gestionar la actualización del carrito en la aplicación.
 *
 * `refreshTrigger` es un `LiveData` que actúa como un evento de actualización.
 * Cuando su valor cambia a `true`, los ViewModels que observan este LiveData pueden reaccionar
 * y actualizar la vista del carrito en consecuencia.
 */
object CartRefresh {

    /**
     * LiveData utilizado para notificar cuándo se debe refrescar el carrito de compras.
     *
     * - `true`: Indica que se debe actualizar la lista de elementos en el carrito.
     * - `false`: Estado inactivo.
     */
    val refreshTrigger = MutableLiveData<Boolean>(false)
}