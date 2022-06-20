package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.ln.GetProfessionsDB
import com.davidmatillacode.wonkasfactory.ln.GetStaffListDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffListViewModel @Inject constructor(
    private val getStaffListDB: GetStaffListDB,
    private val getProfessionsDB: GetProfessionsDB
) : ViewModel() {


    val staffListData = MutableLiveData<List<StaffWorker>>()
    val genderFilterData = MutableLiveData<String>()
    val professionFilterData = MutableLiveData<String>()
    val professionListFilterData = MutableLiveData<List<String?>>()

    fun getStaffList(genderFilter: String = "", professionFilter: String = "") {
        viewModelScope.launch {
            genderFilterData.postValue(genderFilter)
            professionFilterData.postValue(professionFilter)

            val list = getStaffListDB(genderFilter, professionFilter)
            staffListData.postValue(list)
        }
    }

    fun reloadStaffList() {
        viewModelScope.launch {
            val genderFilter = genderFilterData.value ?: ""
            val professionFilter = professionFilterData.value ?: ""

            val list = getStaffListDB(genderFilter, professionFilter)
            staffListData.postValue(list)
        }
    }

    fun applyGenderFilter(genderFilter: String) {
        viewModelScope.launch {
            val professionFilter = professionFilterData.value ?: ""
            getStaffList(genderFilter, professionFilter)
        }
    }

    fun applyProfesionFilter(professionfilter: String) {
        viewModelScope.launch {
            val genderFilter = genderFilterData.value ?: ""
            getStaffList(genderFilter, professionfilter)
        }
    }

    fun seachProfession(filter: String = "") {
        viewModelScope.launch {
            val professionList = getProfessionsDB(filter)
            professionListFilterData.postValue(professionList)
        }
    }

}