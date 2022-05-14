package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veygard.starwarssage.R

private const val MOVIE_URL = "movie_url"

class PeopleScreenFragment : Fragment() {
    private var movieUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieUrl = it.getString(MOVIE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("PeopleScreenFragment", "movie url $movieUrl")
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            PeopleScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_URL, param)
                }
            }
    }
}