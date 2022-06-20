package com.davidmatillacode.wonkasfactory.data.network

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.model.requests.StaffListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StaffApiClient {

    @GET("oompa-loompas")
    suspend fun getStaffList(): Response<StaffListResponse>

    @GET("oompa-loompas/{id}")
    suspend fun getWorkerDetail(@Path("id") id: Int): Response<StaffWorker>
}