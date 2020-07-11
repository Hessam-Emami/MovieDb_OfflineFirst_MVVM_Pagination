package com.emami.moviedb.app.di.module

import android.content.Context
import androidx.room.Room
import com.emami.moviedb.common.Constants
import com.emami.moviedb.common.db.MovieDatabase
import com.emami.moviedb.movie.data.local.dao.MovieDao
import com.emami.moviedb.movie.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppPersistenceModule {


    @Singleton
    @Provides
    fun provideDatabase(context: Context): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        Constants.DB_NAME
    )
        .build()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(movieDb: MovieDatabase): RemoteKeysDao = movieDb.remoteKeysDao()

    @Singleton
    @Provides
    fun provideMovieDao(movieDb: MovieDatabase): MovieDao = movieDb.movieDao()
}