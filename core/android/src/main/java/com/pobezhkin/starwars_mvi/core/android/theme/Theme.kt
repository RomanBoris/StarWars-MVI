package com.pobezhkin.starwars_mvi.core.android.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val StarWarsColorScheme = darkColorScheme(
    primary = ImperialGold,
    onPrimary = SpaceBlack,
    background = SpaceBlack,
    onBackground = StarWhite,
    surface = SpaceSurface,
    onSurface = StarWhite,
    surfaceVariant = SpaceSurface,
    onSurfaceVariant = FadedGray,
)

@Composable
fun StarWarsMVITheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = StarWarsColorScheme) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}
