package com.example.xjpackcompose.presentation.ui.activity.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xjpackcompose.util.DefaultDispatchers
import com.example.xjpackcompose.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel
@Inject
constructor(
    private val defaultDispatchers: DispatcherProvider
) : ViewModel() {

    val countDownFlow = flow {
        val startValue = 5
        var currentValue = startValue
        emit(startValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(defaultDispatchers.main)

    init {
//        collectFlow()
//        collectFlowWithOperators()
//        onEachSample()

        //  terminal operators
//        countSample()
//        reduceSample()
//        foldSample()

        //  flattening operators
//        flatteningOperatorSample()

        //  buffer, conflate, collectLatest
//        restaurantSample()
//        bufferSample()
//        conflateSample()
//        collectLatestSample()

        //  shared flow
        //  squareNumber(8) - emitting value here is not collected. since sharedFlow is hot flow, it emits value, even when there is no collectors, and also stateFlow is also hot flow
//        collectorsSharedFlow()
//        squareNumber(8)
    }

//    don't know, this is crashing
//    private fun collectorsSharedFlow() {
//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(2000L)
//                println("yup: this is collector 1: value: $it")
//            }
//        }
//
//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(3000L)
//                println("yup: this is collector 2: value: $it")
//            }
//        }
//    }

    private fun collectFlow() {
        viewModelScope.launch(defaultDispatchers.main) {
            countDownFlow.collect {
                println("yup: emitted value: $it")
            }
            countDownFlow.collectLatest {
                println("yup: emitted latest value: $it")
            }
        }
    }

    //  operators
    private fun collectFlowWithOperators() {
        viewModelScope.launch(defaultDispatchers.main) {
            countDownFlow
                .filter {
                    it % 2 == 0
                }
                .map {
                    it * it
                }
                .onEach {
                    println("yup: it: $it")
                }
                .collect {
                    println("yup: final value: $it")
                }
        }
    }

    private fun onEachSample() {
        countDownFlow.onEach {
            println("yup: onEach: $it")
        }.launchIn(viewModelScope)
    }

    //  terminal operators
    private fun countSample() {
        viewModelScope.launch {
            /* o/p
            *   2022-02-20 23:16:42.988 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 10
                2022-02-20 23:16:44.997 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 8
                2022-02-20 23:16:47.002 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 6
                2022-02-20 23:16:49.008 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 4
                2022-02-20 23:16:51.013 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 2
                2022-02-20 23:16:53.020 9458-9458/com.example.xjpackcompose I/System.out: yup: value: 0
                2022-02-20 23:16:53.022 9458-9458/com.example.xjpackcompose I/System.out: yup: noOfItem div by 2 : 6
                * */
            val noOfItem = countDownFlow
                .filter {
                    it % 2 == 0
                }.onEach {
                    println("yup: value: $it")
                }.count {
                    it % 2 == 0
                }
            println("yup: noOfItem div by 2 : $noOfItem")

            val noOfItem2 = countDownFlow.count { it % 2 == 0 }
            println("yup: noOfItem2: $noOfItem2")   //  o/p - yup: noOfItem2: 6
        }
    }

    private fun reduceSample() {
        viewModelScope.launch {
            val reduceResult = countDownFlow.reduce { accumulator, value ->
                accumulator + value
            }
            println("yup: reduceResult: $reduceResult") //  o/p - yup: reduceResult: 55
        }
    }

    private fun foldSample() {
        viewModelScope.launch {
            val foldResult = countDownFlow.fold(100) { acc, value ->
                acc + value
            }
            println("yup: foldResult: $foldResult") //  o/p - yup: foldResult: 155
        }
    }

    //  flattening operators
    private fun flatteningOperatorSample() {
        val flow1 = flow {
            emit(1)
            delay(1000L)
            emit(2)
        }
        /*
        * o/p
        *
        *   2022-02-20 23:32:03.884 14511-14511/com.example.xjpackcompose I/System.out: yup: the value: 2
            2022-02-20 23:32:04.390 14511-14511/com.example.xjpackcompose I/System.out: yup: the value: 3
            2022-02-20 23:32:05.392 14511-14511/com.example.xjpackcompose I/System.out: yup: the value: 3
            2022-02-20 23:32:05.894 14511-14511/com.example.xjpackcompose I/System.out: yup: the value: 4
        * */
        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect {
                println("yup: the value: $it")
            }
        }
        //  real time sample
        fun getRecipeById(id: Int): Flow<String> {
            return flow { emit("Karuvaadu id: $id") }
        }

        val recipeIds = (1..5).asFlow()
        viewModelScope.launch {
            recipeIds.flatMapConcat { id ->
                getRecipeById(id)
            }.collect {
                println("yup: collected recipe: $it")
            }
        }
    }

    private fun restaurantSample() {
        val flow = flow {
            emit("soup!")
            emit("starter")
            emit("main dish")
            emit("ice cream")
            emit("juice")
            emit("mouth freshner")
        }.flowOn(defaultDispatchers.main)
        /*
        2022-02-20 23:46:17.888 18673-18673/com.example.xjpackcompose I/System.out: yup: soup! is delivered
        2022-02-20 23:46:17.888 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : soup!
        2022-02-20 23:46:19.391 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: soup!
        2022-02-20 23:46:19.392 18673-18673/com.example.xjpackcompose I/System.out: yup: starter is delivered
        2022-02-20 23:46:19.393 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : starter
        2022-02-20 23:46:20.896 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: starter
        2022-02-20 23:46:20.897 18673-18673/com.example.xjpackcompose I/System.out: yup: main dish is delivered
        2022-02-20 23:46:20.897 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : main dish
        2022-02-20 23:46:22.399 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: main dish
        2022-02-20 23:46:22.399 18673-18673/com.example.xjpackcompose I/System.out: yup: ice cream is delivered
        2022-02-20 23:46:22.400 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : ice cream
        2022-02-20 23:46:23.903 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: ice cream
        2022-02-20 23:46:23.904 18673-18673/com.example.xjpackcompose I/System.out: yup: juice is delivered
        2022-02-20 23:46:23.904 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : juice
        2022-02-20 23:46:25.407 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: juice
        2022-02-20 23:46:25.408 18673-18673/com.example.xjpackcompose I/System.out: yup: mouth freshner is delivered
        2022-02-20 23:46:25.409 18673-18673/com.example.xjpackcompose I/System.out: yup: now eating : mouth freshner
        2022-02-20 23:46:26.913 18673-18673/com.example.xjpackcompose I/System.out: yup: finished eating: mouth freshner
        */
        viewModelScope.launch {
            flow.onEach {
                println("yup: $it is delivered")
            }.collect {
                println("yup: now eating : $it")
                delay(1500L)
                println("yup: finished eating: $it")
            }
        }
    }

    private fun bufferSample() {
        val flow = flow {
            emit("starter")
            emit("main dish")
            emit("ice cream")
        }
        /*
        * o/p
        *
        * 2022-02-20 23:49:33.125 18745-18745/com.example.xjpackcompose I/System.out: yup: starter is delivered
            2022-02-20 23:49:33.126 18745-18745/com.example.xjpackcompose I/System.out: yup: main dish is delivered
            2022-02-20 23:49:33.126 18745-18745/com.example.xjpackcompose I/System.out: yup: ice cream is delivered
            2022-02-20 23:49:33.127 18745-18745/com.example.xjpackcompose I/System.out: yup: now eating : starter
            2022-02-20 23:49:34.629 18745-18745/com.example.xjpackcompose I/System.out: yup: finished eating: starter
            2022-02-20 23:49:34.630 18745-18745/com.example.xjpackcompose I/System.out: yup: now eating : main dish
            2022-02-20 23:49:36.133 18745-18745/com.example.xjpackcompose I/System.out: yup: finished eating: main dish
            2022-02-20 23:49:36.134 18745-18745/com.example.xjpackcompose I/System.out: yup: now eating : ice cream
            2022-02-20 23:49:37.637 18745-18745/com.example.xjpackcompose I/System.out: yup: finished eating: ice cream
        * */
        viewModelScope.launch {
            flow.onEach {
                println("yup: $it is delivered")
            }
                .buffer()  //  this makes flow to run in a coroutine, and "collect" in another coroutine
                .collect {
                    println("yup: now eating : $it")
                    delay(1500L)
                    println("yup: finished eating: $it")
                }
        }
    }

    private fun conflateSample() {
        val flow = flow {
            emit("starter")
            emit("main dish")
            emit("ice cream")
        }
        /*
        * o/p
        *
        2022-02-20 23:53:29.247 18950-18950/com.example.xjpackcompose I/System.out: yup: starter is delivered
        2022-02-20 23:53:29.248 18950-18950/com.example.xjpackcompose I/System.out: yup: main dish is delivered
        2022-02-20 23:53:29.248 18950-18950/com.example.xjpackcompose I/System.out: yup: ice cream is delivered
        2022-02-20 23:53:29.249 18950-18950/com.example.xjpackcompose I/System.out: yup: now eating : starter
        2022-02-20 23:53:30.751 18950-18950/com.example.xjpackcompose I/System.out: yup: finished eating: starter
        2022-02-20 23:53:30.752 18950-18950/com.example.xjpackcompose I/System.out: yup: now eating : ice cream - (note main dish is missed, it collects the latest emission i.e., ice cream)
        2022-02-20 23:53:32.255 18950-18950/com.example.xjpackcompose I/System.out: yup: finished eating: ice cream
        * */
        viewModelScope.launch {
            flow.onEach {
                println("yup: $it is delivered")
            }
                .conflate()  //  this makes flow to run in a coroutine, and "collect" in another coroutine
                .collect {
                    println("yup: now eating : $it")
                    delay(1500L)
                    println("yup: finished eating: $it")
                }
        }
    }

    private fun collectLatestSample() {
        val flow = flow {
            emit("starter")
            emit("main dish")
            emit("ice cream")
        }
        /*
        * o/p
        *
        2022-02-20 23:55:58.913 19051-19051/com.example.xjpackcompose I/System.out: yup: starter is delivered
        2022-02-20 23:55:58.914 19051-19051/com.example.xjpackcompose I/System.out: yup: now eating : starter
        2022-02-20 23:55:58.915 19051-19051/com.example.xjpackcompose I/System.out: yup: main dish is delivered
        2022-02-20 23:55:58.917 19051-19051/com.example.xjpackcompose I/System.out: yup: now eating : main dish
        2022-02-20 23:55:58.918 19051-19051/com.example.xjpackcompose I/System.out: yup: ice cream is delivered
        2022-02-20 23:55:58.918 19051-19051/com.example.xjpackcompose I/System.out: yup: now eating : ice cream
        2022-02-20 23:56:00.420 19051-19051/com.example.xjpackcompose I/System.out: yup: finished eating: ice cream
        *
        * note down, we are not finishing the collectLatest block, jumping to collect the next emission at the earliest
        * */
        viewModelScope.launch(defaultDispatchers.main) {
            flow.onEach {
                println("yup: $it is delivered")
            }.collectLatest {
                println("yup: now eating : $it")
                delay(1500L)
                println("yup: finished eating: $it")
            }
        }
    }

    //  stateFlow (holds single value, hot flow, similar to livedata without lifecycle awareness)
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun incrementCounter() {
        _stateFlow.value += 1
    }

    //  & sharedFlow (emits single value - one at a time, hot flow, doesn't retain state for orientation changes)
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun squareNumber(number: Int) {
        viewModelScope.launch(defaultDispatchers.main) {
            _sharedFlow.emit(number * number)
        }
    }
}