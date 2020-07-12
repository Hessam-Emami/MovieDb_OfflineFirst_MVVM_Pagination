package com.emami.moviedb.movie.ui.detail

import com.emami.moviedb.movie.data.local.entity.MovieEntity

interface DetailView {
    fun renderMovieDetail(movieEntity: MovieEntity)
}