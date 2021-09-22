package com.example.xjpackcompose.util

import android.util.Log

inline fun Any.showAPIVLog(log: () -> String) =
    SLog.v(this::class.java.simpleName, log())

inline fun Any.showVLog(log: () -> String) =
    SLog.v(this::class.java.name, log())

inline fun Any.showELog(log: () -> String) =
    SLog.e(this::class.java.name, log())

inline fun Any.showDLog(log: () -> String) =
    SLog.d(this::class.java.name, log())

inline fun Any.showILog(log: () -> String) =
    SLog.i(this::class.java.name, log())

inline fun Any.showWLog(log: () -> String) =
    SLog.w(this::class.java.name, log())

object SLog {
    val DEBUG_BOOL = true

    fun v(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.v(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.e(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.i(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (DEBUG_BOOL)
            Log.w(tag, msg)
    }
}