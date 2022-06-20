package com.davidmatillacode.wonkasfactory.data.model.requests

import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.google.gson.annotations.SerializedName

data class StaffListResponse (@SerializedName("current")val current : Int, @SerializedName("total") val total : Int, @SerializedName("results") val result : List<StaffWorker>)