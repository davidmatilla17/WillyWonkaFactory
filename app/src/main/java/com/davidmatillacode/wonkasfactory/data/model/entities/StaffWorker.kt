package com.davidmatillacode.wonkasfactory.data.model.entities

import com.google.gson.annotations.SerializedName

data class StaffWorker(

    @SerializedName("id")
    var id: Int?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("favorite")
    val favorite: Favorite?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("profession")
    val profession: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("height")
    val height: Int?
)
