package com.emami.moviedb.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<T : BaseViewModel>(
    private val viewModelClass: Class<out T>,
    private val viewModelFactory: ViewModelProvider.Factory
) :
    Fragment(), BaseView {
    lateinit var viewModel: T

    /**
     * Provides a construct to put all the live data observations of a viewModel in one place
     */
    open fun observeLiveData() {
        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            it?.let { msg -> showMessage(msg) }
        })
    }

    /**
     * Provides a construct to put all the listeners in one place
     */
    open fun setupListeners() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    private var toast: Toast? = null
    override fun showMessage(message: String, length: Int) {
        //if a toast is already visible, cancel it
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, length).also {
            it.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setupListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }
}