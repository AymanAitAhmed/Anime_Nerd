package lr.aym.animenerd.data.repository.datastore

import kotlinx.coroutines.flow.Flow
import lr.aym.animenerd.data.dataStore.GameProgressStatus
import lr.aym.animenerd.domain.repository.datastore.GameProgressStatusRepository
import javax.inject.Inject

class GameProgressStatusRepositoryImpl @Inject constructor(
    private val gameProgressStatus: GameProgressStatus
) : GameProgressStatusRepository {
    override suspend fun getGameProgressStatus(): Flow<Boolean> {
        return gameProgressStatus.getGameProgressStatus
    }

    override suspend fun saveGameProgressStatus(gameCompleted: Boolean) {
        gameProgressStatus.saveGameProgressStatus(gameCompleted)
    }
}