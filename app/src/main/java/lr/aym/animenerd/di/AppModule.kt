package lr.aym.animenerd.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lr.aym.animenerd.data.dataStore.CurrentLevel
import lr.aym.animenerd.data.dataStore.GameProgressStatus
import lr.aym.animenerd.data.dataStore.UserPoints
import lr.aym.animenerd.data.database.LevelDatabase
import lr.aym.animenerd.data.repository.datastore.CurrentLevelRepositoryImpl
import lr.aym.animenerd.data.repository.LevelDbRepositoryImpl
import lr.aym.animenerd.data.repository.datastore.GameProgressStatusRepositoryImpl
import lr.aym.animenerd.data.repository.datastore.UserPointsRepositoryImpl
import lr.aym.animenerd.domain.repository.datastore.CurrentLevelRepository
import lr.aym.animenerd.domain.repository.LevelDbRepository
import lr.aym.animenerd.domain.repository.datastore.GameProgressStatusRepository
import lr.aym.animenerd.domain.repository.datastore.UserPointsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserPointsDataStore(@ApplicationContext context: Context): UserPoints {
        return UserPoints(context)
    }

    @Provides
    @Singleton
    fun provideCurrentLevelDataStore(@ApplicationContext context: Context): CurrentLevel {
        return CurrentLevel(context)
    }

    @Provides
    fun provideCurrentLevelRepo(@ApplicationContext context: Context): CurrentLevelRepository = CurrentLevelRepositoryImpl(
            currentLevelDataStore = provideCurrentLevelDataStore(
                context
            )
        )


    @Provides
    fun provideUserPointsRepo(@ApplicationContext context: Context): UserPointsRepository = UserPointsRepositoryImpl(
            userPoints = provideUserPointsDataStore(
                context
            )
        )

    @Provides
    @Singleton
    fun provideDatabase(app:Application) : LevelDatabase {
        return Room.databaseBuilder(
            app,
            LevelDatabase::class.java,
            "levels"
        ).createFromAsset("database/animeNerd.db").build()
    }

    @Provides
    @Singleton
    fun provideLevelDbRepository (db : LevelDatabase) : LevelDbRepository{
        return LevelDbRepositoryImpl(db.dao())
    }

    @Provides
    @Singleton
    fun provideGameProgressStatusDatastore(@ApplicationContext context: Context) : GameProgressStatus{
        return GameProgressStatus(context)
    }

    @Provides
    @Singleton
    fun provideGameProgressStatusRepository(@ApplicationContext context: Context):GameProgressStatusRepository{
        return GameProgressStatusRepositoryImpl(
            provideGameProgressStatusDatastore(context)
        )
    }



}