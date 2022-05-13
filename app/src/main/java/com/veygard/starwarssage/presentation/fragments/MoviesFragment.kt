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
                }
                is SwViewModelState.GotPerson -> {
                }
                is SwViewModelState.GotPlanet -> {
                }
            }
        }

        viewModel.localState.addObserver { result ->
            when(result){
                is SwViewModelState.GotMoviesLocal -> {
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}