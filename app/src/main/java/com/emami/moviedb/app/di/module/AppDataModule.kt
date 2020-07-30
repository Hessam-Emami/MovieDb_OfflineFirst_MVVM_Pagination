package com.emami.moviedb.app.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Provides persistence related objects such as database, shared pref, etc.
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class AppDataModule {
}