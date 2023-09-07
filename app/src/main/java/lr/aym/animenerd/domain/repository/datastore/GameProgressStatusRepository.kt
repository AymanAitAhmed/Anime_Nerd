package lr.aym.animenerd.domain.repository.datastore

import kotlinx.coroutines.flow.Flow
import lr.aym.animenerd.data.dataStore.GameProgressStatus

interface GameProgressStatusRepository {

    suspend fun getGameProgressStatus(): Flow<Boolean>

    suspend fun saveGameProgressStatus(gameCompleted: Boolean)

}