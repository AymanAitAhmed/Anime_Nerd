package lr.aym.animenerd.data.repository

import lr.aym.animenerd.data.database.Level
import lr.aym.animenerd.data.database.LevelDao
import lr.aym.animenerd.domain.repository.LevelDbRepository

class LevelDbRepositoryImpl (
    private val dao: LevelDao
        ) : LevelDbRepository {
    override suspend fun getLeveLByNum(levelNum: Int): Level? {
        return dao.getLeveLByNum(levelNum)
    }

    override suspend fun getTotalCountLevels(): Int {
        return dao.getTotalCountLevels()
    }

    override suspend fun insertLevel(level: Level) {
        dao.insertLevel(level)
    }
}