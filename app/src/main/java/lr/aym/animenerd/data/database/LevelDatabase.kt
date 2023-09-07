package lr.aym.animenerd.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Level::class],
    version = 1
)
abstract class LevelDatabase : RoomDatabase() {

        abstract fun dao(): LevelDao

}