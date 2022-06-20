package com.davidmatillacode.wonkasfactory.ln

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.repository.StaffRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWorkerDetailWithIdDB @Inject constructor(private val repository : StaffRepository) {
    operator fun invoke(id:Int):StaffWorker? = repository.getWorkerDetailWithIdDB(id)
}