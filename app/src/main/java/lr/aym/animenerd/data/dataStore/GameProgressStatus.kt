package lr.aym.animenerd.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameProgressStatus(private val context : Context) {
    //create the preference datastore
    companion object{
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("gameProgressStatus")
        val GAME_PROGRESS_STATUS_KEY = booleanPreferencesKey("game_Progress_Status")
    }
    val getGameProgressStatus: Flow<Boolean> = context.datastore.data.map { preferences->
        preferences[GAME_PROGRESS_STATUS_KEY] ?: false
    }
    suspend fun saveGameProgressStatus(gameCompleted : Boolean){
        context.datastore.edit {preferences ->
            preferences[GAME_PROGRESS_STATUS_KEY] = gameCompleted
        }
    }
}