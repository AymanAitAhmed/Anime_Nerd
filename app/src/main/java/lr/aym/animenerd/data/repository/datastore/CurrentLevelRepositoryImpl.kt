package lr.aym.animenerd.data.repository.datastore

import kotlinx.coroutines.flow.Flow
import lr.aym.animenerd.data.dataStore.CurrentLevel
import lr.aym.animenerd.domain.repository.datastore.CurrentLevelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentLevelRepositoryImpl @Inject constructor(
    private val currentLevelDataStore: CurrentLevel
) : CurrentLevelRepository {
    override suspend fun getCurrentLevel(): Flow<Int> {
        return currentLevelDataStore.getCurrentLevel
    }

    override suspend fun saveCurrentLevel(currentLevel : Int) {
        currentLevelDataStore.saveCurrentLevel(currentLevel)
    }
}