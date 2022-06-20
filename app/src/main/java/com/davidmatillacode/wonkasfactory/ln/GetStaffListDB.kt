package com.davidmatillacode.wonkasfactory.ln

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.data.repository.StaffRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetStaffListDB @Inject constructor(private val repository: StaffRepository) {

    operator fun invoke(genderFilter: String, professionFilter: String): List<StaffWorker> =
        repository.getStaffListDB(genderFilter, professionFilter)
}