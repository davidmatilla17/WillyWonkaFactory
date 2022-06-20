package com.davidmatillacode.wonkasfactory.ln

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.repository.StaffRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadAllStaffList @Inject constructor(private val repository : StaffRepository){
    suspend operator fun invoke():List<StaffWorker> = repository.getAllWorkers()
}