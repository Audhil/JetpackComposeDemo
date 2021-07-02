package com.example.xjpackcompose.ui.screens.supportallscreensizes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun MediaQuery(
    comparator: Dimensions.DimensionComparator,
    content: @Composable () -> Unit
) {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density

    val screenHeight =
        LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density

    if (comparator.compare(screenWidth, screenHeight))
        content()
}