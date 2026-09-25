package com.pobezhkin.starwars_mvi.heroes

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pobezhkin.starwars_mvi.core.android.theme.StarWarsMVITheme
import com.pobezhkin.starwars_mvi.core.feature.getComponent
import com.pobezhkin.starwars_mvi.heroes.api.HeroesApi

class HeroesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply{
        setContent {
            StarWarsMVITheme {
                HeroesScreenContent(modifier = Modifier.systemBarsPadding(),
                    viewModel = viewModel)
            }
        }
    }

    private val viewModel: HeroesViewModel by lazy {
        ViewModelProvider(
            this,
            HeroesFactory(
                factory = (requireContext().applicationContext as Application)
                    .getComponent(HeroesApi::class.java)
                    .heroesFactory,
            )
        )[HeroesViewModel::class.java]
    }

    companion object{
        fun newInstance() = HeroesFragment()
    }
}
