package com.davidmatillacode.wonkasfactory.data.network

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.model.requests.StaffListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class StaffService @Inject constructor(private val api: StaffApiClient) {

    suspend fun getStaffList(): StaffListResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<StaffListResponse> = api.getStaffList()
                response.body()
            } catch (e: Exception) {
                null
            }
        }

    }

    suspend fun getWorkerDetail(id: Int): StaffWorker? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<StaffWorker> = api.getWorkerDetail(id)
                response.body()
            } catch (e: Exception) {
                null
            }
        }


    }
}