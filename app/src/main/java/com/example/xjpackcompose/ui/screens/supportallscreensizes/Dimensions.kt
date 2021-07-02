package com.example.xjpackcompose.ui.screens.supportallscreensizes

import androidx.compose.ui.unit.Dp

sealed class Dimensions {
    object Width : Dimensions()
    object Height : Dimensions()

    sealed class DimensionOperator {
        object LessThan : DimensionOperator()
        object GreaterThan : DimensionOperator()
    }

    class DimensionComparator(
        val operator: DimensionOperator,
        private val dimensions: Dimensions,
        val value: Dp
    ) {
        fun compare(screenWidth: Dp, screenHeight: Dp): Boolean {
            return if (dimensions is Width) {
                when (operator) {
                    is DimensionOperator.LessThan ->
                        screenWidth < value

                    is DimensionOperator.GreaterThan ->
                        screenWidth > value
                }
            } else
                when (operator) {
                    is DimensionOperator.LessThan ->
                        screenHeight < value

                    is DimensionOperator.GreaterThan ->
                        screenHeight > value
                }
        }
    }
}

infix fun Dimensions.lessThan(
    value: Dp
): Dimensions.DimensionComparator =
    Dimensions.DimensionComparator(
        operator = Dimensions.DimensionOperator.LessThan,
        dimensions = this,
        value = value
    )

infix fun Dimensions.greaterThan(
    value: Dp
): Dimensions.DimensionComparator =
    Dimensions.DimensionComparator(
        operator = Dimensions.DimensionOperator.GreaterThan,
        dimensions = this,
        value = value
    )