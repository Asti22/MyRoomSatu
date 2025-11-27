package com.example.myroomsatu.repositori

import com.example.myroomsatu.room.Siswa
import com.example.myroomsatu.room.SiswaDao
import kotlinx.coroutines.flow.Flow

interface RepositoriSiswa {
    fun getAllSiswaStream(): Flow<List<Siswa>>

    suspend fun insertSiswa(siswa: Siswa)
}

class OfflineRepositoriSiswa(
    private val siswaDao: SiswaDao
) : RepositoriSiswa {

    override fun getAllSiswaStream(): Flow<List<Siswa>> =
        siswaDao.getAllSiswa()

    override suspend fun insertSiswa(siswa: Siswa) =
        siswaDao.insert(siswa)
}
