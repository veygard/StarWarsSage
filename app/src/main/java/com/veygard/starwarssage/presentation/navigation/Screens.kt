package com.veygard.starwarssage.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.veygard.starwarssage.presentation.screens.ErrorScreenFragment
import com.veygard.starwarssage.presentation.screens.MoviesScreenFragment
import com.veygard.starwarssage.presentation.screens.PeopleScreenFragment
import com.veygard.starwarssage.presentation.screens.PlanetScreenFragment

object Screens {

    fun moviesFragment() = FragmentScreen {
        MoviesScreenFragment()
    }

    fun errorScreen() = FragmentScreen {
        ErrorScreenFragment()
    }

    fun personScreen() = FragmentScreen {
        PeopleScreenFragment()
    }

    fun planetScreen() = FragmentScreen{
        PlanetScreenFragment()
    }
}