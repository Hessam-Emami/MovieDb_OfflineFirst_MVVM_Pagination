package com.emami.moviedb.movie.di.module

import androidx.fragment.app.Fragment
import com.emami.moviedb.app.di.key.FragmentKey
import com.emami.moviedb.movie.data.local.LocalDataSource
import com.emami.moviedb.movie.data.local.LocalDataSourceImpl
import com.emami.moviedb.movie.data.network.RemoteDataSource
import com.emami.moviedb.movie.data.network.RemoteDataSourceImpl
import com.emami.moviedb.movie.ui.detail.DetailFragment
import com.emami.moviedb.movie.ui.discover.MovieListFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieModule {
    /**
     * Binds this fragment into a map of fragments to later be used by [FragmentFactory]
     */
    @Binds
    @IntoMap
    @FragmentKey(MovieListFragment::class)
    abstract fun bindMovieFragment(movieListFragment: MovieListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DetailFragment::class)
    abstract fun bindDetailFragment(detailFragment: DetailFragment): Fragment

    @Binds
    @ActivityScoped
    abstract fun bindLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    @ActivityScoped
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}