package com.emami.moviedb.app.di.module

import com.emami.moviedb.movie.di.MovieComponent
import dagger.Module

@Module(subcomponents = [MovieComponent::class])
object SubComponentModule