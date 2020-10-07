package com.emami.moviedb.movie.ui.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.emami.moviedb.R
import com.emami.moviedb.common.base.BaseFragment
import com.emami.moviedb.common.util.DateTimeUtil
import com.emami.moviedb.common.util.loadImage
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment @Inject constructor() :
    BaseFragment(), DetailView {

    private val viewModel by viewModels<DetailViewModel>()

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            renderMovieDetail(it)
        })
        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            it?.let { msg -> showMessage(msg) }
        })
    }

    override fun renderMovieDetail(movieEntity: MovieEntity) {
        with(movieEntity) {
            detail_iv_poster.loadImage(posterLink)
            detail_tv_date.text = DateTimeUtil.getYearMonthFromDateString(releaseDate)
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