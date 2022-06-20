package com.davidmatillacode.wonkasfactory.core

import com.davidmatillacode.wonkasfactory.data.model.entities.db.FavoriteRlm
import com.davidmatillacode.wonkasfactory.data.model.entities.db.StaffWorkerRlm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object RealmHelper {
    fun getRealm(): Realm {
        val config = RealmConfiguration.Builder(
            setOf(
                StaffWorkerRlm::class,
                FavoriteRlm::class
            )
        )
            .build()
        return Realm.open(config)
    }
}