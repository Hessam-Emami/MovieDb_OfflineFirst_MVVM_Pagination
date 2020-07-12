package com.emami.moviedb.movie.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import com.emami.moviedb.R
import com.emami.moviedb.common.base.BaseFragment
import com.emami.moviedb.common.util.loadImage
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.ui.discover.MovieViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

interface DetailView {
    fun renderMovieDetail(movieEntity: MovieEntity)
}

class DetailFragment @Inject constructor(vf: ViewModelProvider.Factory) :
    BaseFragment<DetailViewModel>(DetailViewModel::class.java, vf), DetailView {


    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            renderMovieDetail(it)
        })
    }

    override fun renderMovieDetail(movieEntity: MovieEntity) {
        with(movieEntity) {
            detail_iv_poster.loadImage(posterLink)
            detail_tv_date.text = releaseDate
            detail_tv_title.text = title
            detail_tv_overview.text = overview
            detail_tv_popularity.text = popularity.toString()
            detail_tv_vote_count.text = voteCount.toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let { DetailFragmentArgs.fromBundle(it).movieId }?.let { movieId ->
            viewModel.getMovieDetail(movieId)
        }
    }

    override fun getLayoutId(): Int = R.layout.detail_fragment
}