package com.pobezhkin.starwars_mvi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.pobezhkin.starwars_mvi.heroes.HeroesFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       if (savedInstanceState == null){
           supportFragmentManager.commit {
               replace(R.id.fragment_container, HeroesFragment.newInstance())
           }
       }
    }
}

