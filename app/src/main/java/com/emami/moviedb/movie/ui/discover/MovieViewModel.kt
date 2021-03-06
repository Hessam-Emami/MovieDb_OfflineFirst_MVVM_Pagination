package com.emami.moviedb.movie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emami.moviedb.common.base.BaseViewModel
import com.emami.moviedb.movie.data.MovieRepository
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.util.MovieFilter
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(
    private val repo: MovieRepository
) :
    BaseViewModel() {

    private var data: Flow<PagingData<MovieEntity>>? = null
    private var latestSort: MovieFilter.SORT? = null


    fun getMoviesBySort(
        sortBy: MovieFilter.SORT
    ): Flow<PagingData<MovieEntity>> {
        if (checkIfRequestIsRepeated(sortBy)) {
            return data!!
        }
        val result =
            repo.fetchMovies(sortBy).cachedIn(viewModelScope)
        data = result
        latestSort = sortBy
        return result
    }

    private fun checkIfRequestIsRepeated(sortBy: MovieFilter.SORT) =
        latestSort == sortBy && data != null
}

