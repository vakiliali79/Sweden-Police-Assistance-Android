package com.alivakili.sweden_police.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alivakili.sweden_police.api.StationState
import com.alivakili.sweden_police.api.RetrofitClient
import com.alivakili.sweden_police.model.StationsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StationViewModel() : ViewModel() {
    private var _state = MutableStateFlow<StationState>(StationState.Loading)
    var state: StateFlow<StationState> = _state

    init {
        retrieveStations()
    }

    private fun retrieveStations() {
        val call = RetrofitClient.ApiClient.apiService.getPolicestations()
        call.enqueue(object : Callback<StationsModel> {
            override fun onResponse(call: Call<StationsModel>, response: Response<StationsModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _state.value= StationState.Success(body)
                } else {
                    _state.value= StationState.Failure
                    retrieveStations()
                }
            }

            override fun onFailure(call: Call<StationsModel>, t: Throwable) {
                _state.value= StationState.Failure
                retrieveStations()
            }
        })

    }

    companion object {
        fun factory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return StationViewModel() as T
                }
            }
        }
    }


}