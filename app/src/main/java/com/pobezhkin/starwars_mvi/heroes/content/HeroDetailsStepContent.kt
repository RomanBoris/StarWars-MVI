package com.pobezhkin.starwars_mvi.heroes.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.ui.theme.StarWarsMVITheme
import kotlinx.collections.immutable.persistentListOf


@Composable
fun HeroDetailsStepContent(
    modifier: Modifier = Modifier,
    viewState: StepViewState.HeroDetails,
    effect: (HeroesEffect) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TextButton(
            onClick = { effect(HeroesEffect.Details.Back) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("← Назад")
        }

        Text(
            text = viewState.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        viewState.offlineBanner?.let { banner ->
            Text(text = banner, modifier = Modifier.padding(16.dp, 8.dp))
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewState.rows) { row ->
                Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Text(text = row.label)
                    Text(text = row.value, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}


private fun stubHeroDetailsViewState() = StepViewState.HeroDetails(
    title = "Luke Skywalker",
    rows = persistentListOf(
        StepViewState.HeroDetails.DetailRow("Год рождения", "19BBY"),
        StepViewState.HeroDetails.DetailRow("Пол", "male")
    ),
    offlineBanner = null
)

@Preview(showBackground = true)
@Composable
private fun HeroDetailsStepContentPreview() {
    StarWarsMVITheme {
        HeroDetailsStepContent(viewState = stubHeroDetailsViewState(), effect = {})
    }
}