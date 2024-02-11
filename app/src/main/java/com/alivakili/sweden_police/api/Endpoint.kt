package com.alivakili.sweden_police.api

import com.alivakili.sweden_police.model.EventModel
import com.alivakili.sweden_police.model.StationsModel
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("events")
    fun getEvents():Call<EventModel>

    @GET("policestations")
    fun getPolicestations():Call<StationsModel>
}
