package lr.aym.animenerd.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentLevel(private val context : Context) {
    //create the preference datastore
    companion object{
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("currentLevel")
        val CURRENT_LEVEL_KEY = intPreferencesKey("current_Level")
    }
    //get the current Level
    val getCurrentLevel: Flow<Int> = context.datastore.data.map { preferences->
        preferences[CURRENT_LEVEL_KEY] ?: 1
    }
    // to save current Level
    suspend fun saveCurrentLevel(currentLevel : Int){
        context.datastore.edit {preferences ->
            preferences[CURRENT_LEVEL_KEY] = currentLevel
        }
    }
}