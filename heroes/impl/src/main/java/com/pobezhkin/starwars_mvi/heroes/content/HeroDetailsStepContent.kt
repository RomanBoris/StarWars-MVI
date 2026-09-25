package com.pobezhkin.starwars_mvi.heroes.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.pobezhkin.starwars_mvi.core.android.theme.StarWarsMVITheme
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.R
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import kotlinx.collections.immutable.persistentListOf


@Composable
fun HeroDetailsStepContent(
    modifier: Modifier = Modifier,
    viewState: StepViewState.HeroDetails,
    effect: (HeroesEffect) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.fragment_screen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )

        Column(modifier = modifier.fillMaxSize()) {
            TextButton(
                onClick = { effect(HeroesEffect.Details.Back) },
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            ) {
                Text(
                    text = stringResource(R.string.hero_details_back),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }

            Text(
                text = viewState.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            viewState.offlineBanner?.let { banner ->
                Text(
                    text = banner,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewState.rows) { row ->
                    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                        Text(
                            text = row.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            text = row.value,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
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
