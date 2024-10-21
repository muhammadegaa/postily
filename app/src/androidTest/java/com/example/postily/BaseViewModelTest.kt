package com.example.postily

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    // Use TestCoroutineDispatcher to ensure coroutines are properly controlled in tests
    @get:Rule
    val testDispatcherRule = MainDispatcherRule()

    @Before
    fun setUpBase() {   // Base setup method, not open or overridden
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun tearDownBase() {
        testDispatcherRule.cleanupTestCoroutines()
    }
}

