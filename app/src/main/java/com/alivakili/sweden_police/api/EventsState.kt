package com.alivakili.sweden_police.api

import com.alivakili.sweden_police.model.EventModel

sealed class EventsState {
    object Loading: EventsState()
    data class Success(val events: EventModel?): EventsState()
    object Failure: EventsState()
}