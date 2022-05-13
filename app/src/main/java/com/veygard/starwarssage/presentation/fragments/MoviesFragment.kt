package com.veygard.starwarssage.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMovies()
        observeData()
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMovieClick() {
        Toast.makeText(requireContext(), "movie click", Toast.LENGTH_SHORT).show()
    }

}