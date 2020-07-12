package com.emami.moviedb.common.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.emami.moviedb.util.observeOnce
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BaseViewModelTest {
    class TestBaseViewModel() : BaseViewModel() {
        fun testDispatch(msg: String) {
            dispatchMessage(msg)
        }
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should test error livedata`() {
        val viewModel = TestBaseViewModel()
        val testMessage = "testMessage"

        viewModel.testDispatch(testMessage)

        viewModel.getErrorLiveData().observeOnce {
            assertEquals(it, testMessage)
        }
    }
}