package com.pobezhkin.starwars_mvi.heroes.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pobezhkin.starwars_mvi.core.android.theme.StarWarsMVITheme
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.R
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.main_screen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.heroes_list_title),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                letterSpacing = 6.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
            )

            viewState.offlineBanner?.let { banner ->
                Text(
                    text = banner,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
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
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = hero.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = hero.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp),
                            )
                        }
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
