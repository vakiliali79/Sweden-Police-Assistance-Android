package com.alivakili.sweden_police.api

import com.alivakili.sweden_police.model.StationsModel

sealed class StationState {
    object Loading: StationState()
    data class Success(val stations: StationsModel?): StationState()
    object Failure: StationState()
}