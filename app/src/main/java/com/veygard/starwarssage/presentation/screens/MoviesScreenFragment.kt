package com.veygard.starwarssage.presentation.screens

import android.graphics.Color
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
import com.veygard.starwarssage.presentation.adapters.MovieClickInterface
import com.veygard.starwarssage.presentation.navigation.Screens
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import com.veygard.starwarssage.presentation.widgets.MovieListFragment
import com.veygard.starwarssage.presentation.widgets.NothingFoundFragment
import com.veygard.starwarssage.presentation.widgets.ShimmerFragment
import com.veygard.starwarssage.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviesScreenFragment: Fragment() {

    private var _binding: FragmentScreenMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    @Inject
    lateinit var router:Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setStateNull()
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        searchViewListener()
        cancelButtonListener()
    }


    private fun setListFragment() {
        val nestedFragment: Fragment = MovieListFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.movie_list_container, nestedFragment).commitAllowingStateLoss()
    }

    private fun searchViewListener() {
        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterMoviesBySearch(text)
                toggleVisibility( text.isEmpty(), binding.cancelButton)
                toggleSearchViewIconColor(text.isNotEmpty())
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterMoviesBySearch(query ?: "")
                toggleVisibility( query?.isEmpty() ?:true, binding.cancelButton)
                toggleSearchViewIconColor(query?.isNotEmpty() ?: false)
                return false
            }
        })
    }

    private fun cancelButtonListener() {
        binding.cancelButton.setOnClickListener {
            binding.searchBar.setQuery("", false)
            binding.searchBar.clearFocus()
        }
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result ->
            when (result) {
                is SwViewModelState.GotMovies -> {
                    Log.e("get_movies", "observer got movies")
                    setListFragment()
                }
                is SwViewModelState.GotMoviesLocal -> {
                    Log.e("get_movies", "observer local got movies")
                    setListFragment()
                }
                is SwViewModelState.ServerError -> {
                    router.navigateTo(Screens.errorScreen())
                }
                is SwViewModelState.GotPerson -> {}
                is SwViewModelState.GotPlanet -> {}
                SwViewModelState.NotFound -> setNothingFoundFragment()
                SwViewModelState.Loading -> setShimmerFragment()
            }
        }
    }




    private fun setShimmerFragment() {
        val nestedFragment: Fragment = ShimmerFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.movie_list_container, nestedFragment).commitAllowingStateLoss()
    }

    private fun setNothingFoundFragment() {
        val nestedFragment: Fragment = NothingFoundFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.movie_list_container, nestedFragment).commitAllowingStateLoss()
    }
    private fun toggleSearchViewIconColor(isNotEmpty: Boolean) {
        val icon = binding.searchIcon

        if (isNotEmpty) icon.setColorFilter(
            context?.getColor(R.color.blue) ?: Color.BLACK
        )
        else icon.setColorFilter(
            context?.getColor(R.color.grey) ?: Color.LTGRAY
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}