package com.davidmatillacode.wonkasfactory.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.ln.GetWorkerDetailWithIdDB
import com.davidmatillacode.wonkasfactory.ln.LoadWorkerDetailWithId
import com.davidmatillacode.wonkasfactory.ln.UpdateWorkerDetailDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffDetailViewModel @Inject constructor(
    private val getWorkerDetailDB: GetWorkerDetailWithIdDB,
    private val loadWorkerDetail: LoadWorkerDetailWithId,
    private val updateWorkerDetailDB: UpdateWorkerDetailDB,
) : ViewModel() {


    val workerDetailData = MutableLiveData<StaffWorker>()


    fun loadWorkerInfo(id: Int) {
        viewModelScope.launch {
            val worker = getWorkerDetailDB(id)
            if (worker != null) {
                workerDetailData.postValue(worker!!)
            }
            val networkWorker = loadWorkerDetail(id)
            if (networkWorker != null) {
                networkWorker.id = id
                workerDetailData.postValue(networkWorker!!)
                updateWorkerDetailDB(networkWorker)
            }
        }
    }

}