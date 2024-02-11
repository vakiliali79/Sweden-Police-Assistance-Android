package com.alivakili.sweden_police.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class StationsModel : ArrayList<StationsModel.StationsModelItem>(){
    @Parcelize
    data class StationsModelItem(
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("location")
        val location: Location? = Location(),
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("services")
        val services: List<Service?>? = listOf(),
        @SerializedName("Url")
        val url: String? = ""
    ) : Parcelable {
        @Parcelize
        data class Location(
            @SerializedName("gps")
            val gps: String? = "",
            @SerializedName("name")
            val name: String? = ""
        ) : Parcelable

        @Parcelize
        data class Service(
            @SerializedName("name")
            val name: String? = ""
        ) : Parcelable
    }
}