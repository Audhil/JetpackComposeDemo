package com.example.xjpackcompose.presentation.ui.activity.flow

import app.cash.turbine.test
import com.example.xjpackcompose.TestDefaultDispatchers
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FlowViewModelTest {

    private lateinit var testDefaultDispatchers: TestDefaultDispatchers
    private lateinit var flowViewModel: FlowViewModel

    @Before
    fun setUp() {
        testDefaultDispatchers = TestDefaultDispatchers()
        flowViewModel = FlowViewModel(testDefaultDispatchers)
    }

    @Test
    fun `countDown, properly counts down from 5 to 0`() = runBlocking {
        flowViewModel.countDownFlow.test {
            for (i in 5 downTo 0) {
                testDefaultDispatchers.testDispatcher.advanceTimeBy(1000L)
                val emission = awaitItem()
                assertThat(emission).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `squareNumber, number properly squared`() = runBlocking {
        val job = launch {
            flowViewModel.sharedFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        flowViewModel.squareNumber(3)
        job.join()
        job.cancel()
    }
}