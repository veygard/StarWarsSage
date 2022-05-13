package com.veygard.starwarssage.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.veygard.starwarssage.databinding.FragmentMoviesBinding
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class MoviesFragment : Fragment() {

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
            when(result){
                is SwViewModelState.GotMovies -> {
                    binding.textView.text = "movies server count: ${result.getMovies.getMoviesResponse.results?.size.toString()}"
                }
                is SwViewModelState.GotPerson -> {
                    binding.textView.text = "person ${result.person.name}"
                }
                is SwViewModelState.GotPlanet -> {
                    binding.textView.text = "planet ${result.planet.name}"
                }
            }
        }

        viewModel.localState.addObserver { result ->
            when(result){
                is SwViewModelState.GotMoviesLocal -> {
                    binding.textView.text = "movies count Local: ${result.list.size}"
                }
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

        binding.buttonMovies.setOnClickListener {
            viewModel.getMoviesFromServer()
        }
        binding.buttonPerson.setOnClickListener {
            viewModel.getPeople()
            Log.e("button_click", "person button was clicked")
        }
        binding.buttonPlanet.setOnClickListener {
            viewModel.getPlanets()
            Log.e("button_click", "planet button was clicked")
        }

        binding.buttonLocal.setOnClickListener {
            Log.e("button_click", "local button was clicked")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}