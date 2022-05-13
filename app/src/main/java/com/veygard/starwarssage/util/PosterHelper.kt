package com.veygard.starwarssage.util

import androidx.annotation.DrawableRes
import com.veygard.starwarssage.R

fun getPoster(episodeId: Int?): Int?{
    return when(episodeId){
        1 -> R.drawable.phantom_menace
        2 -> R.drawable.attack_of_the_clones
        3 -> R.drawable.revenge
        4 -> R.drawable.new_hope
        5 -> R.drawable.strike_back
        6 -> R.drawable.return_jedi
        else -> null
    }
}