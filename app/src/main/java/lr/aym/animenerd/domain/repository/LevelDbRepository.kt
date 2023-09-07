package lr.aym.animenerd.domain.repository


import lr.aym.animenerd.data.database.Level

interface LevelDbRepository {

    suspend fun getLeveLByNum(levelNum : Int) : Level?

    suspend fun getTotalCountLevels() : Int

    suspend fun insertLevel(level: Level)
}