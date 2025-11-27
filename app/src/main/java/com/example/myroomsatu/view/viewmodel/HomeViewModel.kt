package com.example.myroomsatu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myroomsatu.repositori.RepositoriSiswa
import com.example.myroomsatu.data.Siswa
import com.example.myroomsatu.room.Siswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList

class HomeViewModel(private val repositoriSiswa: RepositoriSiswa) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        repositoriSiswa.getAllSiswaStream()
            .filterNotNull()
            .map { list ->
                HomeUiState(listSiswa = list.toList())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    data class HomeUiState(
        val listSiswa: List<Siswa> = listOf()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}