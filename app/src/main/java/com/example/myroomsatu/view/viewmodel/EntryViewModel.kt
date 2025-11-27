package com.example.myroomsatu.view.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.myroomsatu.repositori.RepositoriSiswa
import com.example.myroomsatu.room.Siswa

class EntryViewModel(private val repositoriSiswa: RepositoriSiswa) : ViewModel() {

    var uiStateSiswa by mutableIntStateOf(value = UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa): Boolean {
        return with(receiver = uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    fun updateUIState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(uiState = detailSiswa))
    }
    suspend fun saveSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            repositoriSiswa.insertSiswa(siswa = uiStateSiswa.detailSiswa.toSiswa())
        }
    }
    data class UIStateSiswa(
        val detailSiswa: DetailSiswa = DetailSiswa(),
        val isEntryValid: Boolean = false
    )
    data class DetailSiswa(
        val id: Int = 0,
        val nama: String = "",
        val alamat: String = "",
        val telpon: String = ""
    )
    fun DetailSiswa.toSiswa(): Siswa = Siswa(
        id = id,
        nama = nama,
        alamat = alamat,
        telpon = telpon
    )fun Siswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
        detailSiswa = this.toDetailSiswa(),
        isEntryValid = isEntryValid
    )

    fun Siswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
        id = id,
        nama = nama,
        alamat = alamat,
        telpon = telpon
    )

}