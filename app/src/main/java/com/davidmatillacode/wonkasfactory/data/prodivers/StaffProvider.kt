package com.davidmatillacode.wonkasfactory.data.prodivers

import com.davidmatillacode.wonkasfactory.core.RealmHelper
import com.davidmatillacode.wonkasfactory.data.model.entities.db.StaffWorkerRlm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import javax.inject.Inject

class StaffProvider @Inject constructor(private val realm: Realm) {

    suspend fun storeStaffList(
        workerList:
        List<StaffWorkerRlm>
    ) {
        realm.write {
            workerList.map { copyToRealm(it, UpdatePolicy.ALL) }
        }
    }

    fun getStaffList(
        genderFilter: String,
        professionFilter: String
    ): RealmList<StaffWorkerRlm> {

        if (genderFilter.isNotBlank() && professionFilter.isNotBlank()) {
            val query = "gender == $0 && profession == $1"
            return realm.query(StaffWorkerRlm::class, query, genderFilter, professionFilter).find()
                .toRealmList()
        } else if (genderFilter.isNotBlank()) {
            val query = "gender == $0"
            return realm.query(StaffWorkerRlm::class, query, genderFilter).find().toRealmList()
        } else if (professionFilter.isNotBlank()) {
            val query = "profession == $0"
            return realm.query(StaffWorkerRlm::class, query, professionFilter).find().toRealmList()
        }
        return realm.query(StaffWorkerRlm::class).find().toRealmList()
    }

    fun getWorkerDetail(
        id: Int
    ): StaffWorkerRlm? {
        val query = "id == $0"
        return realm.query(StaffWorkerRlm::class, query, id).first().find()
    }

    fun getProfessions(filter: String): List<String?> {

        val  result = if (filter.isNotBlank()) {
            realm.query(StaffWorkerRlm::class, "profession contains[c] $0", filter)
                .distinct("profession").find().toRealmList()
        } else {
            realm.query(StaffWorkerRlm::class)
                .distinct("profession").find().toRealmList()
        }

        return result.map { it.profession }
    }

}