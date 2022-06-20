package com.davidmatillacode.wonkasfactory.data.model.entities.db

import io.realm.kotlin.types.RealmObject

open class FavoriteRlm : RealmObject {
    var color: String? = null
    var food: String? = null
    var randomString: String? = null
    var song: String? = null
}