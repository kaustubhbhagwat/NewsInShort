package com.example.newsinshort.ui.screens.car

import androidx.lifecycle.ViewModel
import com.example.newsinshort.ui.screens.cars.domain.Car
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarViewModel : ViewModel() {
    private var _car = MutableStateFlow<Car?>(null)
    var car = _car.asStateFlow()


    init {
    getCar()
    }

    fun getCar() {
        val db = Firebase.firestore

        db.collection("cars")
            .document("XobLGVApx8YK2nfDBRxm")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                _car.value = documentSnapshot.toObject()
            }

    }

}