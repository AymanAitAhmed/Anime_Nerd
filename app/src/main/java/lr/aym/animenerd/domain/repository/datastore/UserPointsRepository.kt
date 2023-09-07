package lr.aym.animenerd.domain.repository.datastore

import kotlinx.coroutines.flow.Flow

interface UserPointsRepository {

    fun getUserPoints():Flow<Int>

    suspend fun incrementUserPointBy(count : Int)

    suspend fun decrementUserPointBy(count : Int)
}