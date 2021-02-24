package com.enigmacamp.myviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationFragmentViewModel : ViewModel() {
    private var _isNameValid = MutableLiveData<ResourceState>()
    val isNameValid: LiveData<ResourceState>
        get() {
            return _isNameValid
        }

    private var _districtList = MutableLiveData<List<String>>()
    val districtList: LiveData<List<String>>
        get() {
            return _districtList
        }

    fun inputNameValidation(name: String) {
        //Simulasi delay dari backend API
        GlobalScope.launch {
            _isNameValid.postValue(ResourceState.loading())
            delay(3000)
            if (!name.isNullOrBlank()) {
                _isNameValid.postValue(ResourceState.success(true))
            } else {
                _isNameValid.postValue(ResourceState.fail("Name can not empty"))
            }
        }
//        if (!name.isNullOrBlank()) {
//            _isNameValid.value = ResourceState.success(true)
//        } else {
//            _isNameValid.value = ResourceState.fail("Name can not empty")
//        }
    }

    fun onAddressGetDistrict(address: String) {
        GlobalScope.launch {
            delay(2000)
            if (address == "11") {
                _districtList.postValue(listOf("123", "234", "456"))
            } else {
                _districtList.postValue(listOf("XYZ", "ABC", "WHC"))
            }
        }
    }
}