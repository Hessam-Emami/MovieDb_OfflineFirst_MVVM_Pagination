package com.emami.moviedb.app.di.module

import com.emami.moviedb.forecast.di.MovieComponent
import dagger.Module

@Module(subcomponents = [MovieComponent::class])
object SubComponentModule