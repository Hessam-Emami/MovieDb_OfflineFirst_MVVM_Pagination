package com.emami.moviedb.movie.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MovieListFragment @Inject constructor(
    private val moviePagingDataAdapter: MoviePagingDataAdapter,
    private val exceptionLocalizer: ExceptionLocalizer,
    vf: ViewModelProvider.Factory
) : BaseFragment(), MovieView {

    private val viewModel by viewModels<MovieViewModel> { vf }

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
                .collect {
                    renderMovieList(it)
                }
        }
    }

    private fun processErrorState(state: LoadState) {
        showSnack(
            exceptionLocalizer.getExceptionMessage(
                (state as LoadState.Error).error as Exception,
                requireContext()
            )
            , getString(R.string.error_retry)
        ) {
            moviePagingDataAdapter.retry()
        }
    }

    override fun renderMovieList(list: PagingData<MovieEntity>) {
        //should be withing a coroutine's scope
        moviePagingDataAdapter.submitData(this.lifecycle, list)
    }

    /**
     * If it's loading, show progress bar otherwise hide it and show retry button on error
     */
    override fun renderMovieLoadState(state: CombinedLoadStates) {
        Timber.d("Load states Mediator: ${state.mediator}")
        if (state.refresh is LoadState.Error) {
            hideProgressBar()
            processErrorState(state.refresh)
        }
        if (state.append is LoadState.Error) {
            hideProgressBar()
            processErrorState(state.append)
        } else if (state.refresh is LoadState.Loading || state.append is LoadState.Loading) {
            movie_pb_loading.makeVisible()
        } else {
            hideProgressBar()
        }
    }

    private fun hideProgressBar() {
        if (movie_pb_loading.isVisible) {
            movie_pb_loading.makeGone()
        }
    }

    override fun onMovieItemClicked(movieId: Long) {
        findNavController().navigate(
            MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(
                movieId
            )
        )
    }

    override fun initRecyclerView() {
        movie_recycler_view_discover.adapter = moviePagingDataAdapter.apply {
            addLoadStateListener(this@MovieListFragment::renderMovieLoadState)
            onMovieClickedCallback = this@MovieListFragment::onMovieItemClicked
        }
    }
}

