package lr.aym.animenerd.data.repository.datastore

import kotlinx.coroutines.flow.Flow
import lr.aym.animenerd.data.dataStore.UserPoints
import lr.aym.animenerd.domain.repository.datastore.UserPointsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPointsRepositoryImpl @Inject constructor(
    private val userPoints: UserPoints
) : UserPointsRepository {
    override fun getUserPoints(): Flow<Int> {
        return userPoints.getPoints
    }

    override suspend fun incrementUserPointBy(count: Int) {
        userPoints.incrementPointsBy(count)
    }

    override suspend fun decrementUserPointBy(count: Int) {
        userPoints.decrementPointsBy(count)
    }
}