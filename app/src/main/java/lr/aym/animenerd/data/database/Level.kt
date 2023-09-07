package lr.aym.animenerd.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level")
data class Level(
    @PrimaryKey(autoGenerate = false)
    val lvlNum : Int ,
    val textQuestion : String? = null,
    val drawableImgName : String? = null,
    val internalImgPath : String? = null,
    val correctAnswer : String
)