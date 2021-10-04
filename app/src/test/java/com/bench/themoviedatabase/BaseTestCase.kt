package com.bench.themoviedatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bench.themoviedatabase.util.DispatcherProvider
import org.junit.Rule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

abstract class BaseTestCase {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScopeRule = MainCoroutineScopeRule()

    protected val testDispatcherPovider = mock<DispatcherProvider>{
        whenever(it.getDefault()).thenReturn(mainCoroutineScopeRule.testDispatcher)
        whenever(it.getIO()).thenReturn(mainCoroutineScopeRule.testDispatcher)
    }
}