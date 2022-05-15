package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenMoviesBinding
import com.veygard.starwarssage.databinding.FragmentScreenPeopleBinding
import com.veygard.starwarssage.presentation.navigation.Screens
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val MOVIE_URL = "movie_url"

@AndroidEntryPoint
class PeopleScreenFragment : Fragment() {
    private var movieUrl: String? = null

    private var _binding: FragmentScreenPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    @Inject
    lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieUrl = it.getString(MOVIE_URL)
        }

        movieUrl?.let {
            viewModel.getMovie(it)
        } ?: kotlin.run {
            router.navigateTo(Screens.errorScreen())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("PeopleScreenFragment", "movie url $movieUrl")
        observeData()
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result->
            when(result){
                is SwViewModelState.GotMovie -> {
                    activity?.title = result.movie.title
                    /* вызываем загрузку персонажей у этого фильма */

                }
                SwViewModelState.GotMovies -> {}
                SwViewModelState.GotMoviesLocal -> {

                }
                is SwViewModelState.GotPerson -> {}
                is SwViewModelState.GotPlanet -> {}
                SwViewModelState.Loading -> {}
                SwViewModelState.NotFound -> {}
                is SwViewModelState.ServerError -> {}
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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