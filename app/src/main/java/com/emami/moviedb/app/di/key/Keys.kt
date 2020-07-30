package com.emami.moviedb.app.di.key

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass


/**
 * Custom key scope to handle android viewModel multi binding injection
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
