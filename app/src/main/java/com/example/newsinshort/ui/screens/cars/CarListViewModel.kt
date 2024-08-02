package com.example.newsinshort.ui.screens.cars

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.newsinshort.ui.screens.cars.domain.Car
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarListViewModel : ViewModel() {

    private var _carList = MutableStateFlow<List<Car>>(emptyList())
    var carList = _carList.asStateFlow()
    private val db = Firebase.firestore


    init {
        getCarList()
    }

    private fun getCarList() {
        db.collection("cars")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _carList.value = value.toObjects()
                }
            }

    }

    fun createCar() {
        val car = hashMapOf(
            "id" to 4,
            "brand" to "Suzuki"
        )

        db.collection("cars").add(car)
            .addOnSuccessListener {
                Log.d("Document","Created" )
            }
    }
}