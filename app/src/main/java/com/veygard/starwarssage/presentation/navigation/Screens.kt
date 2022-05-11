package com.veygard.starwarssage.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.veygard.starwarssage.presentation.fragments.ErrorFragment
import com.veygard.starwarssage.presentation.fragments.MoviesFragment
import com.veygard.starwarssage.presentation.fragments.PersonFragment
import com.veygard.starwarssage.presentation.fragments.PlanetFragment

object Screens {

    fun moviesFragment() = FragmentScreen {
        MoviesFragment()
    }

    fun errorScreen() = FragmentScreen {
        ErrorFragment()
    }

    fun personScreen() = FragmentScreen {
        PersonFragment()
    }

    fun planetScreen() = FragmentScreen{
        PlanetFragment()
    }
}