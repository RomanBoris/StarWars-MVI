package com.pobezhkin.starwars_mvi.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class HeroesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply{
        setContent {
            HeroesScreenContent(modifier = Modifier.systemBarsPadding(),
                viewModel = viewModel)
        }
    }

    private val viewModel: HeroesViewModel by lazy{
        ViewModelProvider(
            this,
            HeroesFactory(context = requireContext().applicationContext)
        )[HeroesViewModel::class.java]
    }

    companion object{
        fun newInstance() = HeroesFragment()
    }
}