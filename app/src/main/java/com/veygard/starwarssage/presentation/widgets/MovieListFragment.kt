package com.veygard.starwarssage.presentation.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentMovieListBinding
import com.veygard.starwarssage.presentation.adapters.MovieClickInterface
import com.veygard.starwarssage.presentation.adapters.MoviesListAdapter
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel

class MovieListFragment: Fragment(R.layout.fragment_movie_list) {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    private var adapter: MoviesListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerMovieList.also {
            adapter = MoviesListAdapter(moviesList = viewModel.moviesListToShow.value ?: emptyList(), viewModel.clickInterfaceHolder.value as MovieClickInterface, requireContext())
            it.adapter= adapter
            it.layoutManager= LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            it.addItemDecoration(decoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}