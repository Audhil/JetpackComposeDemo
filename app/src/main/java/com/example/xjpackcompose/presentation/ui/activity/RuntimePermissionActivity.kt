package com.example.xjpackcompose.presentation.ui.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalPermissionsApi
class RuntimePermissionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val permissionsStates = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                    )
                )

                val lifeCycleOwner = LocalLifecycleOwner.current
                //  to avoid side effects of recomposing
                DisposableEffect(key1 = lifeCycleOwner, effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START)
                            permissionsStates.launchMultiplePermissionRequest()
                    }
                    lifeCycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifeCycleOwner.lifecycle.removeObserver(observer)
                    }
                })

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    permissionsStates.permissions.forEach { perm ->
                        when (perm.permission) {
                            Manifest.permission.CAMERA -> {
                                when {
                                    perm.hasPermission ->
                                        Text(text = "Camera permission accepted!")

                                    perm.shouldShowRationale ->
                                        Text(text = "Camera permission needed to access camera!")

                                    perm.isPermenantlyDenied() ->
                                        Text(text = "Camera permission permanently denied, you can enable it in app settings, anytime!")
                                }
                            }

                            Manifest.permission.RECORD_AUDIO -> {
                                when {
                                    perm.hasPermission ->
                                        Text(text = "Record audio permission accepted!")

                                    perm.shouldShowRationale ->
                                        Text(text = "Record audio permission needed to record audio!")

                                    perm.isPermenantlyDenied() ->
                                        Text(text = "Record audio permission permanently denied, you can enable it in app settings, anytime!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //  don't have permission & don't need to show rationale, then it is permanently denied
    fun PermissionState.isPermenantlyDenied(): Boolean = !hasPermission && !shouldShowRationale
}