package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenPeopleBinding
import com.veygard.starwarssage.presentation.adapters.PersonClickInterface
import com.veygard.starwarssage.presentation.navigation.Screens
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import com.veygard.starwarssage.presentation.widgets.NothingFoundFragment
import com.veygard.starwarssage.presentation.widgets.PersonListFragment
import com.veygard.starwarssage.presentation.widgets.ShimmerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val MOVIE_URL = "movie_url"

@AndroidEntryPoint
class PeopleScreenFragment : Fragment(), PersonClickInterface {
    private var movieUrl: String? = null

    private var _binding: FragmentScreenPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    private var childFrManager: FragmentManager? = null

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
        childFrManager= childFragmentManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("PeopleScreenFragment", "movie url $movieUrl")
        observeData()
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.clickInterfaceHolder.value == null) viewModel.setClickInterface(this)
        activity?.title = getString(R.string.movies_fragment_title)
    }

    override fun onPause() {
        super.onPause()
        if(viewModel.clickInterfaceHolder.value != null) viewModel.setClickInterface(null)
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result->
            when(result){
                is SwViewModelState.GotMovie -> {
                    activity?.title = result.movie.title
                    /* вызываем загрузку персонажей у этого фильма */
                    viewModel.getPeopleByMovie(result.movie)
                }
                SwViewModelState.GotMovies -> {}
                SwViewModelState.GotMoviesLocal -> {

                }
                is SwViewModelState.GotPerson -> {}
                is SwViewModelState.GotPlanet -> {}
                SwViewModelState.Loading -> setShimmerFragment()
                SwViewModelState.NotFound -> {}
                is SwViewModelState.ServerError -> {}
                SwViewModelState.GotPeopleByMovie -> {
                    setListFragment()
                }
            }

        }
    }

    private fun setShimmerFragment() {
        val nestedFragment: Fragment = ShimmerFragment()
        val transaction = childFrManager?.beginTransaction()
        transaction?.replace(R.id.people_list_container, nestedFragment)?.commitAllowingStateLoss()
    }

    private fun setNothingFoundFragment() {
        val nestedFragment: Fragment = NothingFoundFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.people_list_container, nestedFragment).commitAllowingStateLoss()
    }

    private fun setListFragment() {
        val nestedFragment: Fragment = PersonListFragment()
        val transaction = childFrManager?.beginTransaction()
        transaction?.replace(R.id.people_list_container, nestedFragment)?.commitAllowingStateLoss()
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

    override fun onPersonClick(planetUrl: String) {
    }
}