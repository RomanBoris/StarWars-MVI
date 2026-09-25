package com.pobezhkin.starwars_mvi.heroes

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pobezhkin.starwars_mvi.core.coroutine.map
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.content.ErrorContent
import com.pobezhkin.starwars_mvi.heroes.content.HeroDetailsStepContent
import com.pobezhkin.starwars_mvi.heroes.content.HeroListStepContent
import com.pobezhkin.starwars_mvi.heroes.content.LoadingContent
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.core.android.theme.StarWarsMVITheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HeroesScreenContent(
    modifier: Modifier = Modifier,
    viewState: HeroesViewState,
    effect: (HeroesEffect) -> Unit
) {
    when (val step = viewState.step) {
        is StepViewState.Loading -> LoadingContent(modifier = modifier)
        is StepViewState.Error -> ErrorContent(
            modifier = modifier,
            viewState = step,
            effect = effect
        )

        is StepViewState.HeroList -> HeroListStepContent(
            modifier = modifier,
            viewState = step,
            effect = effect
        )

        is StepViewState.HeroDetails -> HeroDetailsStepContent(
            modifier = modifier,
            viewState = step,
            effect = effect
        )
    }
}

@Composable
fun HeroesScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HeroesViewModel
    ) {

    val coroutineScope = rememberCoroutineScope ()

    val viewStateFlow = remember {
        viewModel.stateFlow.map(coroutineScope, viewModel.stateToViewStateMapper)
    }
    val viewState by viewStateFlow.collectAsState()

    val effect: (HeroesEffect) -> Unit = remember { {viewModel.effect(it)} }

    BackHandler(enabled = viewState.step is StepViewState.HeroDetails) {
        effect(HeroesEffect.Details.Back)
    }

    HeroesScreenContent(modifier = modifier, viewState = viewState, effect = effect)

}

private fun stubHeroesViewState() = HeroesViewState(
    step = StepViewState.HeroList(
        heroes = persistentListOf(
            StepViewState.HeroList.HeroCardViewState(
                url = "https://swapi.dev/api/people/1/",
                name = "Luke Skywalker",
                subtitle = "Tatooine"
            )
        ),
        offlineBanner = null,
        scrollSnapshot = null
    )
)

@Preview(showBackground = true)
@Composable
private fun HeroesScreenContentPreview() {
    StarWarsMVITheme {
        HeroesScreenContent(viewState = stubHeroesViewState(), effect = {})
    }
}