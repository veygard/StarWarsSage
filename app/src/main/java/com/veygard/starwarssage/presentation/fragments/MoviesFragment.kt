package com.veygard.starwarssage.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentMoviesBinding
import com.veygard.starwarssage.domain.model.Movie
import com.veygard.starwarssage.presentation.adapters.MoviesListAdapter
import com.veygard.starwarssage.presentation.adapters.MovieClickInterface
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState

class MoviesFragment : Fragment(), MovieClickInterface {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    private var searchCountDownTimer: CountDownTimer? = null
    private val searchWaitingTime = 1000L
    private var searchTextValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewListener()
        observeData()
    }

    private fun setupMovieRecycler(results: List<Movie>?) {
        binding.movieRecyclerHolder.also {
            val adapter = MoviesListAdapter(moviesList = results ?: emptyList(), this, requireContext())
            it.adapter= adapter
            it.layoutManager= LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            it.addItemDecoration(decoration)
        }

    }

    private fun searchViewListener() {
        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                searchTextValue = text ?: ""
                when {
                    text.isNotEmpty() -> {
                        searchCountDownTimer?.cancel()
                        searchCountDownTimer = object : CountDownTimer(searchWaitingTime, 500) {
                            override fun onTick(p0: Long) {}
                            override fun onFinish() {
                                Log.d("search_test", "countDownTimer: onFinish, text: $text")
                            }
                        }
                        searchCountDownTimer?.start()
                    }
                    else -> {

                    }
                }
                toggleVisibility( searchTextValue.isEmpty(), binding?.cancelButton)
                toggleSearchViewIconColor(searchTextValue.isNotEmpty())
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search_test", "search enter event, text: $searchTextValue, query: $query")
                return false
            }
        })
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result ->
            when (result) {
                is SwViewModelState.GotMovies -> {
                    Log.e("get_movies", "observer got movies")
                    setupMovieRecycler(result.list)
                }
                is SwViewModelState.GotMoviesLocal -> {
                    Log.e("get_movies", "observer local got movies")
                    setupMovieRecycler(result.list)
                }
                is SwViewModelState.Error -> {}
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMovieClick() {
        Toast.makeText(requireContext(), "movie click", Toast.LENGTH_SHORT).show()
    }

    private fun toggleVisibility(gone: Boolean, view: View?) {
        if (gone) view?.visibility = View.GONE
        else view?.visibility = View.VISIBLE
    }

    private fun toggleSearchViewIconColor(isNotEmpty: Boolean) {
        val icon = binding?.searchIcon

        if (isNotEmpty) icon.setColorFilter(
            context?.getColor(R.color.blue) ?: Color.BLACK
        )
        else icon.setColorFilter(
            context?.getColor(R.color.grey) ?: Color.LTGRAY
        )
    }

}