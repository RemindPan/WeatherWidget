package com.tinyfight.weather.widget.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun PaddingValues.withMinimum(minimumPadding: Dp): PaddingValues {
    val layoutDirection: LayoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(layoutDirection).coerceAtLeast(minimumPadding),
        top = calculateTopPadding().coerceAtLeast(minimumPadding),
        end = calculateStartPadding(layoutDirection).coerceAtLeast(minimumPadding),
        bottom = calculateBottomPadding().coerceAtLeast(minimumPadding)
    )
}