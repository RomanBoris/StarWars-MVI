package com.pobezhkin.starwars_mvi.heroes.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.ui.theme.StarWarsMVITheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HeroListStepContent(
    modifier: Modifier = Modifier,
    viewState: StepViewState.HeroList,
    effect: (HeroesEffect) -> Unit,
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewState.scrollSnapshot?.firstVisibleItemIndex ?: 0,
        initialFirstVisibleItemScrollOffset = viewState.scrollSnapshot?.firstVisibleItemScrollOffset ?: 0
    )

    Column(modifier = modifier.fillMaxSize()) {
        viewState.offlineBanner?.let { banner ->
            Text(
                text = banner,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(viewState.heroes, key = { it.url }) { hero ->
                Card(
                    onClick = {
                        effect(
                            HeroesEffect.List.HeroClicked(
                                heroUrl = hero.url,
                                firstVisibleItemIndex = listState.firstVisibleItemIndex,
                                firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = hero.name)
                        Text(text = hero.subtitle)
                    }
                }
            }
        }
    }
}


private fun stubHeroListViewState() =  StepViewState.HeroList(
    heroes = persistentListOf(
        StepViewState.HeroList.HeroCardViewState(
            url = "https://swapi.dev/api/people/1/",
            name = "Luke Skywalker",
            subtitle = "Tatooine"
        ),
        StepViewState.HeroList.HeroCardViewState(
            url = "https://swapi.dev/api/people/2/",
            name = "C-3PO",
            subtitle = "Tatooine"
        )
    ),
    offlineBanner = null,
    scrollSnapshot = null
)

@Preview(showBackground = true)
@Composable
private fun HeroListStepContentPreview() {
    StarWarsMVITheme {
        HeroListStepContent(viewState = stubHeroListViewState(), effect = {})
    }
}