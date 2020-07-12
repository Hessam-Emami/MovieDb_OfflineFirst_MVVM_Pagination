package com.emami.moviedb.movie.ui.discover

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import com.emami.moviedb.movie.data.local.entity.MovieEntity

interface MovieView {
    fun initRecyclerView()
    fun renderMovieList(list: PagingData<MovieEntity>)
    fun renderMovieLoadState(state: CombinedLoadStates)
    fun onMovieItemClicked(movieId: Long)
}