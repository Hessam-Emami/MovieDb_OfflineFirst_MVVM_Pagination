package com.emami.moviedb.app.di.scope

import javax.inject.Scope

/**
 * Every feature Component uses this to provide objects that live throughout the Component lifecycle
 */
@Scope
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
