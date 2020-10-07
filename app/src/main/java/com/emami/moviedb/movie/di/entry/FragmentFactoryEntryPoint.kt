package com.emami.moviedb.movie.di.entry

import com.emami.moviedb.app.di.factory.MovieDbFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MovieDbFragmentFactoryEntryPoint {
    fun getFragmentFactory(): MovieDbFragmentFactory
}