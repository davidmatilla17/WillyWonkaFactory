package com.davidmatillacode.wonkasfactory.data.model.entities.db

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class StaffWorkerRlm : RealmObject {
    @PrimaryKey
    var id: Int? = null
    var firstName: String? = null
    var lastName: String? = null
    var favorite: FavoriteRlm? = null
    var gender: String? = null
    var image: String? = null
    var profession: String? = null
    var email: String? = null
    var age: Int? = null
    var country: String? = null
    var height: Int? = null
}
