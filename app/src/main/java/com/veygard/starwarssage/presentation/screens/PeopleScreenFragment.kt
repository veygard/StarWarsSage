package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.MainActivity
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenPeopleBinding
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.presentation.navigation.Screens
import com.veygard.starwarssage.presentation.supports.toggleSearchViewIconColor
import com.veygard.starwarssage.presentation.supports.toggleVisibility
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import com.veygard.starwarssage.presentation.widgets.NothingFoundFragment
import com.veygard.starwarssage.presentation.widgets.PersonListFragment
import com.veygard.starwarssage.presentation.widgets.ShimmerFragment
import com.veygard.starwarssage.presentation.widgets.getToastFields
import com.veygard.starwarssage.util.CustomToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val MOVIE_URL = "movie_url"

@AndroidEntryPoint
class PeopleScreenFragment : Fragment() {
    private var movieUrl: String? = null
    private var toast: CustomToast? = null
    private var _binding: FragmentScreenPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    private var childFrManager: FragmentManager? = null

    @Inject
    lateinit var router: Router

    private var movie: Movie? = null


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
        childFrManager = childFragmentManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("PeopleScreenFragment", "movie url $movieUrl")

        observeData()
        searchViewListener()
        cancelButtonListener()
    }

    override fun onResume() {
        super.onResume()
        movie?.let { activity?.title = it.title }
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result ->
            when (result) {
                is SwViewModelState.GotMovie -> {
                    movie = result.movie
                    activity?.title = result.movie.title
                    Log.e("PeopleScreenFragment", "SwViewModelState.GotMovie")

                    /* вызываем загрузку персонажей у этого фильма */
                    viewModel.getPeopleByMovie(result.movie)
                }
                SwViewModelState.Loading -> setShimmerFragment()
                SwViewModelState.NotFound -> setNothingFoundFragment()
                SwViewModelState.GotPeopleByMovie -> {
                    setListFragment()
                    toast?.cancel()
                }
            }
        }

        viewModel.showToast.addObserver { result ->
            result?.let {
                val fields = getToastFields(it, activity?.applicationContext)
                fields?.let {
                    toast?.let {t ->
                        toast!!.cancel()
                        toast = CustomToast(requireContext(),
                            message = fields.text,
                            bgColor = fields.background,
                            textColor = fields.textColor,
                            dur = 500
                        )
                        toast!!.show()
                    }?: kotlin.run {
                        toast = CustomToast(requireContext(),
                            message = fields.text,
                            bgColor = fields.background,
                            textColor = fields.textColor,
                            dur = 500
                        )
                        toast!!.show()
                    }
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

    private fun searchViewListener() {
        binding.peopleSearchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterPeopleBySearch(text)
                toggleVisibility(text.isEmpty(), binding.peopleCancelButton)
                toggleSearchViewIconColor(
                    text.isNotEmpty(),
                    requireContext(),
                    binding.peopleSearchIcon
                )
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterPeopleBySearch(query ?: "")
                toggleVisibility(query?.isEmpty() ?: true, binding.peopleCancelButton)
                toggleSearchViewIconColor(
                    query?.isNotEmpty() ?: false,
                    requireContext(),
                    binding.peopleSearchIcon
                )
                return false
            }
        })
    }

    private fun cancelButtonListener() {
        binding.peopleCancelButton.setOnClickListener {
            binding.peopleSearchBar.setQuery("", false)
            binding.peopleSearchBar.clearFocus()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelGetPeopleByMovieJob("People fragment onDestroyView")
        activity?.title = ""
        toast?.cancel()
        toast=null
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                (activity as? MainActivity)?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.clear()
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