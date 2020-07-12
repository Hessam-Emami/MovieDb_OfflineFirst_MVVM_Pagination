package com.emami.moviedb.movie.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emami.moviedb.R
import com.emami.moviedb.common.base.BaseFragment
import com.emami.moviedb.movie.ui.discover.MovieViewModel
import javax.inject.Inject

class DetailFragment @Inject constructor(vf: ViewModelProvider.Factory) :
    BaseFragment<DetailViewModel>(DetailViewModel::class.java, vf) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun getLayoutId(): Int = R.layout.fragment_detail
}