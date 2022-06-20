package com.davidmatillacode.wonkasfactory.ln

import com.davidmatillacode.wonkasfactory.data.repository.StaffRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfessionsDB @Inject constructor(private val repository: StaffRepository) {
    operator fun invoke(professionFilter: String): List<String?> =
        repository.getprofessionsDB(professionFilter)
}