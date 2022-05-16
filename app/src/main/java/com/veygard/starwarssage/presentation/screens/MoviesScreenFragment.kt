package com.veygard.starwarssage.presentation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenMoviesBinding
import com.veygard.starwarssage.presentation.navigation.Screens
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import com.veygard.starwarssage.presentation.widgets.MovieListFragment
import com.veygard.starwarssage.presentation.widgets.NothingFoundFragment
import com.veygard.starwarssage.presentation.widgets.ShimmerFragment
import com.veygard.starwarssage.presentation.supports.toggleSearchViewIconColor
import com.veygard.starwarssage.presentation.supports.toggleVisibility
import com.veygard.starwarssage.presentation.widgets.getToastFields
import com.veygard.starwarssage.util.CustomToast
import com.veygard.starwarssage.util.ToastTypes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviesScreenFragment : Fragment() {

    private var _binding: FragmentScreenMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    @Inject
    lateinit var router: Router

    private var childFrManager: FragmentManager? = null

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
        childFrManager = childFragmentManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        searchViewListener()
        cancelButtonListener()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.movies_fragment_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    private fun setListFragment() {
        val nestedFragment: Fragment = MovieListFragment()
        val transaction = childFrManager?.beginTransaction()
        transaction?.replace(R.id.movie_list_container, nestedFragment)?.commitAllowingStateLoss()
    }

    private fun searchViewListener() {
        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterMoviesBySearch(text)
                toggleVisibility(text.isEmpty(), binding.cancelButton)
                toggleSearchViewIconColor(text.isNotEmpty(), requireContext(), binding.searchIcon)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterMoviesBySearch(query ?: "")
                toggleVisibility(query?.isEmpty() ?: true, binding.cancelButton)
                toggleSearchViewIconColor(
                    query?.isNotEmpty() ?: false,
                    requireContext(),
                    binding.searchIcon
                )
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
                    _binding?.searchBar?.visibility = View.VISIBLE
                    _binding?.searchIcon?.visibility = View.VISIBLE
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
        viewModel.showToast.addObserver {
            when (it) {
                ToastTypes.DownloadMovie -> {
                    getToastFields(it, requireContext())?.let {f->
                        CustomToast(
                            requireContext(),
                            f.text,
                            f.background,
                            f.textColor,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun setShimmerFragment() {
        val nestedFragment: Fragment = ShimmerFragment()
        val transaction = childFrManager?.beginTransaction()
        transaction?.replace(R.id.movie_list_container, nestedFragment)?.commitAllowingStateLoss()
    }

    private fun setNothingFoundFragment() {
        val nestedFragment: Fragment = NothingFoundFragment()
        val transaction = childFrManager?.beginTransaction()
        transaction?.replace(R.id.movie_list_container, nestedFragment)?.commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}