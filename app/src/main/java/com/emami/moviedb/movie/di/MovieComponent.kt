package com.emami.moviedb.movie.di

import com.emami.moviedb.app.di.scope.ActivityScope
import com.emami.moviedb.app.ui.MainActivity
import com.emami.moviedb.movie.di.module.MovieModule
import com.emami.moviedb.movie.di.module.MovieNonAbstractModule
import dagger.Subcomponent

@Subcomponent(modules = [MovieModule::class, MovieNonAbstractModule::class])
@ActivityScope
interface MovieComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieComponent
    }

    fun inject(activity: MainActivity)
}

