package lr.aym.animenerd.domain.repository.datastore

import kotlinx.coroutines.flow.Flow

interface CurrentLevelRepository {

    suspend fun getCurrentLevel(): Flow<Int>

    suspend fun saveCurrentLevel(currentLevel : Int)

}