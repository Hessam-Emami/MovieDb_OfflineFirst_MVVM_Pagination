package com.emami.moviedb.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emami.moviedb.R
import com.emami.moviedb.app.di.factory.MovieDbFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MovieDbFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

