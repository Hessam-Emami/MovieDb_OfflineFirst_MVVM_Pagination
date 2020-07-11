package com.emami.moviedb.movie.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.emami.moviedb.app.di.key.FragmentKey
import com.emami.moviedb.app.di.key.ViewModelKey
import com.emami.moviedb.movie.data.local.LocalDataSource
import com.emami.moviedb.movie.data.local.LocalDataSourceImpl
import com.emami.moviedb.movie.data.network.RemoteDataSource
import com.emami.moviedb.movie.data.network.RemoteDataSourceImpl
import com.emami.moviedb.movie.ui.discover.MovieListFragment
import com.emami.moviedb.movie.ui.discover.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieModule {
    /**
     * Binds this viewModel into a map of viewModels to later be used by [ViewModelFactory]
     */
    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    /**
     * Binds this fragment into a map of fragments to later be used by [FragmentFactory]
     */
    @Binds
    @IntoMap
    @FragmentKey(MovieListFragment::class)
    abstract fun bindMovieFragment(movieListFragment: MovieListFragment): Fragment

    @Binds
    abstract fun bindLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}