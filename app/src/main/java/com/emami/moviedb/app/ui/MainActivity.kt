package com.emami.moviedb.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emami.moviedb.R
import com.emami.moviedb.app.MovieDbApp
import com.emami.moviedb.app.di.factory.MovieDbFragmentFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MovieDbFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieDbApp).appComponent.movieComponent().create().also {
            it.inject(this)
        }
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

