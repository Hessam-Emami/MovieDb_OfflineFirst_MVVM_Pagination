package com.emami.moviedb.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment() :
    Fragment(), BaseView {

    /**
     * Provides a construct to put all the live data observations of a viewModel in one place
     */
    open fun observeLiveData() {}

    /**
     * Provides a construct to put all the listeners in one place
     */
    open fun setupListeners() {}

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

    private var snack: Snackbar? = null
    override fun showSnack(message: String, actionTitle: String, action: () -> Unit) {
        snack?.dismiss()
        snack =
            Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionTitle) {
                    action.invoke()
                }
        snack?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snack?.dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }
}