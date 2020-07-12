package com.emami.moviedb.movie.di.module

import androidx.paging.PagingConfig
import com.emami.moviedb.app.di.scope.ActivityScope
import com.emami.moviedb.common.Constants
import com.emami.moviedb.movie.data.network.MovieApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MovieNonAbstractModule {

    @Provides
    @ActivityScope
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(
        MovieApi::class.java
    )

    @Provides
    fun providePageConfig(): PagingConfig =
        PagingConfig(Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false)

}