package com.emami.moviedb.app.di

import android.content.Context
import com.emami.moviedb.app.di.module.AppDataModule
import com.emami.moviedb.app.di.module.AppNetworkModule
import com.emami.moviedb.app.di.module.AppPersistenceModule
import com.emami.moviedb.app.di.module.SubComponentModule
import com.emami.moviedb.movie.di.MovieComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * graph containing mostly singleton and global shared across modules objects
 */
@Component(
    modules = [AppDataModule::class, AppNetworkModule::class, AppPersistenceModule::class, SubComponentModule::class]
)
@Singleton
interface AppComponent {
    fun movieComponent(): MovieComponent.Factory

    @Component.Builder
    interface Builder {
        //Binds the application context to the graph entry
        fun application(@BindsInstance appContext: Context): Builder
        fun build(): AppComponent
    }
}

