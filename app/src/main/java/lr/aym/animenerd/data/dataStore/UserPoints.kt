package lr.aym.animenerd.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPoints(private val context: Context) {
    //create the preference datastore
    companion object{
        private val Context.datastore : DataStore<Preferences> by preferencesDataStore("userPoints")
        val CURRENT_POINTS_KEY = intPreferencesKey("user_points")
    }
    //get the current points
    val getPoints: Flow<Int> =context.datastore.data.map { preferences->
        preferences[CURRENT_POINTS_KEY] ?: 0
    }
    suspend fun incrementPointsBy(count : Int){
        context.datastore.edit { preferences->
            val currentPoints = preferences[CURRENT_POINTS_KEY] ?: 0
            preferences[CURRENT_POINTS_KEY] = currentPoints + count

        }
    }
    suspend fun decrementPointsBy(count:Int){
        context.datastore.edit { preferences ->
            val currentPoints = preferences[CURRENT_POINTS_KEY] ?: 0
            preferences[CURRENT_POINTS_KEY] = currentPoints - count
        }
    }
}