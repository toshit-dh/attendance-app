package tech.toshitworks.attendancechahiye.domain.repository

interface DataStoreRepository {

    suspend fun saveScreenSelection(screenSelection: Int)

    suspend fun readScreenSelection(): Int

}