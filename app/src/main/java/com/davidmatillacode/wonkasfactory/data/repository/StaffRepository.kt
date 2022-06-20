package com.davidmatillacode.wonkasfactory.data.repository

import com.davidmatillacode.wonkasfactory.data.model.DataMapper
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.model.entities.db.StaffWorkerRlm
import com.davidmatillacode.wonkasfactory.data.network.StaffService
import com.davidmatillacode.wonkasfactory.data.prodivers.StaffProvider
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StaffRepository @Inject constructor(
    private val api: StaffService,
    private val provider: StaffProvider
) {


    suspend fun getAllWorkers(): List<StaffWorker> {
        val result = api.getStaffList()
        return result?.result ?: emptyList<StaffWorker>()
    }

    suspend fun loadWorkerDetailWithId(id: Int): StaffWorker? {
        return api.getWorkerDetail(id)
    }

    suspend fun storeStaffListDB(staffWorkers: List<StaffWorker>) {
        val mappedData = DataMapper.staffListEntityToRlm(staffWorkers)
        provider.storeStaffList(mappedData)
    }

    fun getStaffListDB(genderFilter: String, professionFilter: String): List<StaffWorker> {
        val dbData = provider.getStaffList(genderFilter, professionFilter)
        return DataMapper.staffListRlmToEntity(dbData)
    }

    fun getWorkerDetailWithIdDB(id: Int): StaffWorker? {
        val worker = provider.getWorkerDetail(id)
        if (worker != null)
            return DataMapper.staffRlmToEntity(worker)
        return null
    }

    fun getprofessionsDB(professionFilter: String): List<String?> =
        provider.getProfessions(professionFilter)

    suspend fun updateWorkerDetail(worker: StaffWorker) {
        val data = DataMapper.staffEntityToRlm(worker)
        val list = ArrayList<StaffWorkerRlm>()
        list.add(data)
        provider.storeStaffList(list)
    }

}