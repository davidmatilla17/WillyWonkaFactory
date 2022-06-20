package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmatillacode.wonkasfactory.ln.LoadAllStaffList
import com.davidmatillacode.wonkasfactory.ln.StoreStaffListDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadAllStaffList: LoadAllStaffList,
    private val storeStaffListDB: StoreStaffListDB
) : ViewModel() {

    val loadEndEvent = MutableLiveData<Boolean>()

    fun loadAllData() {
        viewModelScope.launch {
            val info = loadAllStaffList()
            storeStaffListDB(info)
            loadEndEvent.postValue(true)
        }
    }
}