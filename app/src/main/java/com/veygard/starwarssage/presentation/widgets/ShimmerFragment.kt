package com.veygard.starwarssage.presentation.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentMovieShimmerBinding

class ShimmerFragment: Fragment(R.layout.fragment_movie_shimmer) {
    private var _binding: FragmentMovieShimmerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentMovieShimmerBinding.inflate(inflater, container, false)
        _binding?.shimmerLayout?.startShimmer()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.shimmerLayout?.stopShimmer()
        _binding = null
    }

}