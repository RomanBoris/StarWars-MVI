package com.pobezhkin.starwars_mvi.heroes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.ui.theme.StarWarsMVITheme
import kotlinx.collections.immutable.persistentListOf
import androidx.compose.ui.Modifier
import com.pobezhkin.starwars_mvi.heroes.content.ErrorContent
import com.pobezhkin.starwars_mvi.heroes.content.HeroDetailsStepContent
import com.pobezhkin.starwars_mvi.heroes.content.HeroListStepContent
import com.pobezhkin.starwars_mvi.heroes.content.LoadingContent

@Composable
fun HeroesScreenContent(
    modifier: Modifier =  Modifier,
    viewState: HeroesViewState,
    effect: (HeroesEffect) -> Unit
){
    when(val step = viewState.step){
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

private fun stubHeroesViewState() = HeroesViewState(
    step =  StepViewState.HeroList(
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