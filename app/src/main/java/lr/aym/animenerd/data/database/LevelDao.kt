package lr.aym.animenerd.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LevelDao {

    @Query("SELECT * FROM LEVEL where lvlNum = :levelNum")
    suspend fun getLeveLByNum(levelNum : Int) : Level?

    @Query("SELECT count(*) FROM LEVEL")
    suspend fun getTotalCountLevels() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLevel(level: Level)


}