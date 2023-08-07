package com.stopwatch.harimoradiya.presentation

import android.text.BoringLayout
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class StopWatchViewModel : ViewModel() {

    private val elaspedTime = MutableStateFlow(0L)
    private val _timerState = MutableStateFlow(TimeState.RESET)
    val timerState = _timerState.asStateFlow()


    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val stopWatchText = elaspedTime
        .map { millis ->
            LocalTime.of(
                ((((millis / 1000) / 60) / 60).toInt()),
                ((((millis / 1000) / 60) % 60).toInt()),
                (((millis / 1000) % 60).toInt())
            ).format(formatter)

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(1000),
            "00:00:00"
        )

    init {
        _timerState
            .flatMapLatest { timerState ->
                getTimerFlow(
                    isRunning = timerState == TimeState.RUNNING
                )
            }
            .onEach { timerDiff ->
                elaspedTime.update { it + timerDiff }
            }

            .launchIn(viewModelScope)
    }


    fun toggleIsRunning() {
        when (timerState.value) {
            TimeState.RUNNING -> _timerState.update { TimeState.PAUSED }
            TimeState.PAUSED,
            TimeState.RESET -> _timerState.update { TimeState.RUNNING }
        }
    }


    fun resetTimer() {
        _timerState.update { TimeState.RESET }
        elaspedTime.update { 0L }
    }


    private fun getTimerFlow(isRunning: Boolean): Flow<Long> {
        return flow {
            var startMillis = System.currentTimeMillis()
            while (isRunning) {
                var currentMillis = System.currentTimeMillis()
                val timeDiff = if (currentMillis > startMillis) {
                    currentMillis - startMillis
                } else {
                    0L
                }
                emit(timeDiff)
                startMillis = System.currentTimeMillis()
                delay(10L)
            }
        }
    }
}
