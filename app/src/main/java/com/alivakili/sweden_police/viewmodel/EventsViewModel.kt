package com.alivakili.sweden_police.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alivakili.sweden_police.api.EventsState
import com.alivakili.sweden_police.api.RetrofitClient
import com.alivakili.sweden_police.model.EventModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsViewModel () : ViewModel() {
    private var _state = MutableStateFlow<EventsState>(EventsState.Loading)
    var state: StateFlow<EventsState> = _state

    init {
        retrieveEvents()
    }

    private fun retrieveEvents() {
        val call = RetrofitClient.ApiClient.apiService.getEvents()
        call.enqueue(object : Callback<EventModel> {
            override fun onResponse(call: Call<EventModel>, response: Response<EventModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _state.value= EventsState.Success(body)
                } else {
                    _state.value= EventsState.Failure
                    retrieveEvents()
                }
            }

            override fun onFailure(call: Call<EventModel>, t: Throwable) {
                _state.value= EventsState.Failure
                retrieveEvents()
            }
        })

    }

    companion object {
        fun factory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EventsViewModel() as T
                }
            }
        }
    }


}