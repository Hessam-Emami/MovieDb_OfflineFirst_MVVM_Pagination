package com.emami.moviedb.movie.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

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
        Timber.d("Load states Mediator: ${state.mediator}")
        if (state.refresh is LoadState.Error) {
            movie_pb_loading.makeGone()
            showSnackbar(
                exceptionLocalizer.getExceptionMessage(
                    (state.refresh as LoadState.Error).error as Exception,
                    requireContext()
                )
            ) {
                moviePagingDataAdapter.retry()
            }
        } else if (state.refresh is LoadState.Loading || state.append is LoadState.Loading) {
            movie_pb_loading.makeVisible()
        } else {
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

    override fun onDestroyView() {
        super.onDestroyView()
        snack?.dismiss()
    }

    var snack: Snackbar? = null
    private fun showSnackbar(message: String, action: () -> Unit) {
        snack?.dismiss()
        snack =
            Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE).setAction("retry") {
                action.invoke()
            }
        snack?.show()
    }
}

