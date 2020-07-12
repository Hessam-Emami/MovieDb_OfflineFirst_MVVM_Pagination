package com.emami.moviedb.movie.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.emami.moviedb.R
import com.emami.moviedb.common.base.BaseFragment
import com.emami.moviedb.common.util.makeGone
import com.emami.moviedb.common.util.makeVisible
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.util.ExceptionLocalizer
import com.emami.moviedb.movie.util.MovieFilter
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface MovieView {
    fun initRecyclerView()
    fun renderMovieList(list: PagingData<MovieEntity>)
    fun renderMovieLoadState(state: CombinedLoadStates)
}

class MovieListFragment @Inject constructor(
    private val moviePagingDataAdapter: MoviePagingDataAdapter,
    private val exceptionLocalizer: ExceptionLocalizer,
    vf: ViewModelProvider.Factory
) :
    BaseFragment<MovieViewModel>(MovieViewModel::class.java, vf), MovieView {

    private var initialSortState = MovieFilter.SORT.DESCENDING

    override fun getLayoutId(): Int = R.layout.movie_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestMovieData(initialSortState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_movie_sort_asc -> requestMovieData(MovieFilter.SORT.ASCENDING)
            R.id.menu_movie_sort_desc -> requestMovieData(MovieFilter.SORT.DESCENDING)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupListeners() {
        super.setupListeners()
        movie_btn_retry.setOnClickListener {
            if (moviePagingDataAdapter.itemCount > 0) {
                moviePagingDataAdapter.retry()
            } else {
                requestMovieData(initialSortState)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_movie_sort, menu)
    }

    var job: Job? = null
    private fun requestMovieData(sortBy: MovieFilter.SORT) {
        initialSortState = sortBy
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getMoviesBySort(sortBy)
                .observe(viewLifecycleOwner, Observer {
                    renderMovieList(it)
                }
                )
        }
    }

    override fun renderMovieList(list: PagingData<MovieEntity>) {
        //should be withing a coroutine's scope
        moviePagingDataAdapter.submitData(this.lifecycle, list)
    }

    override fun renderMovieLoadState(state: CombinedLoadStates) {
        Timber.d("Load States: $state")
        when (val refreshState = state.refresh) {
            is LoadState.NotLoading -> {
                movie_pb_loading.makeGone()
                movie_btn_retry.makeGone()
            }
            is LoadState.Error -> {
                movie_pb_loading.makeGone()
                movie_btn_retry.makeVisible()
                showMessage(exceptionLocalizer.getExceptionMessage(refreshState.error as Exception,requireContext()))
            }
            is LoadState.Loading -> {
                movie_pb_loading.makeVisible()
                movie_btn_retry.makeGone()
            }
        }
        when (val appendState = state.append) {
            is LoadState.Error -> {
                movie_pb_loading.makeGone()
                movie_btn_retry.makeVisible()
                showMessage(exceptionLocalizer.getExceptionMessage(appendState.error as Exception, requireContext()))
            }
            is LoadState.Loading -> {
                movie_pb_loading.makeVisible()
                movie_btn_retry.makeGone()
            }
            is LoadState.NotLoading -> {

            }
        }
    }

    override fun initRecyclerView() {
        movie_recycler_view_discover.adapter = moviePagingDataAdapter
        moviePagingDataAdapter.addLoadStateListener(this::renderMovieLoadState)
    }

}

