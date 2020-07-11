package com.emami.moviedb.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emami.moviedb.R
import com.emami.moviedb.app.MovieDbApp
import com.emami.moviedb.app.di.factory.MovieDbFragmentFactory
import com.emami.moviedb.forecast.ui.movie.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainNavigator {

    @Inject
    lateinit var fragmentFactory: MovieDbFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieDbApp).appComponent.movieComponent().create().also {
            it.inject(this)
        }
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToMovieListScreen()
        }
    }

    override fun navigateToMovieListScreen() {
        supportFragmentManager.beginTransaction()
            .replace(
                frameContainer.id,
                MovieListFragment::class.java, null
            )
            .commit()
    }
}
