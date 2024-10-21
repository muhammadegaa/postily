package com.example.postily

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun tearDownBase() {
        testDispatcherRule.cleanupTestCoroutines()
    }
}
