package com.alivakili.sweden_police.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class EventModel : ArrayList<EventModel.EventModelItem>(){
    @Parcelize
    data class EventModelItem(
        @SerializedName("datetime")
        val datetime: String? = "",
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("location")
        val location: Location? = Location(),
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("summary")
        val summary: String? = "",
        @SerializedName("type")
        val type: String? = "",
        @SerializedName("url")
        val url: String? = ""
    ) : Parcelable {
        @Parcelize
        data class Location(
            @SerializedName("gps")
            val gps: String? = "",
            @SerializedName("name")
            val name: String? = ""
        ) : Parcelable
    }
}