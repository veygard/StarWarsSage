package com.veygard.starwarssage.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.veygard.starwarssage.presentation.screens.ErrorScreenFragment
import com.veygard.starwarssage.presentation.screens.MoviesScreenFragment
import com.veygard.starwarssage.presentation.screens.PeopleScreenFragment
import com.veygard.starwarssage.presentation.screens.PlanetScreenFragment
import javax.inject.Inject

object Screens {

    fun moviesScreen() = FragmentScreen {
        MoviesScreenFragment()
    }

    fun errorScreen() = FragmentScreen {
        ErrorScreenFragment()
    }

    fun peopleScreen(movieUrl: String) = FragmentScreen {
        PeopleScreenFragment.newInstance(movieUrl)
    }

    fun planetScreen() = FragmentScreen{
        PlanetScreenFragment()
    }
}