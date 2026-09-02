package com.pobezhkin.starwars_mvi.heroes.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.ui.theme.StarWarsMVITheme

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    viewState: HeroesViewState.StepViewState.Error,
    effect: (HeroesEffect) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = viewState.message)
        Button(onClick = {
            effect(
                if (viewState.showBack) HeroesEffect.Details.Retry
                else HeroesEffect.List.Load
            )
        }){
            Text(text = "Повторить")
        }
        if (viewState.showBack){
            TextButton( onClick = {effect(HeroesEffect.Details.Back)}) { Text("Назад") }
        }
    }

}

// stub — тестовый viewState для Preview, без Store и без сети
private fun stubErrorViewState() = StepViewState.Error(
    message = "Не удалось загрузить данные",
    showBack = false
)

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    StarWarsMVITheme {
        ErrorContent(viewState = stubErrorViewState(), effect = {})
    }
}