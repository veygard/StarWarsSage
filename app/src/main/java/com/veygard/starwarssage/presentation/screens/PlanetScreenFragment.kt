package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenPeopleBinding
import com.veygard.starwarssage.databinding.FragmentScreenPlanetBinding
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import javax.inject.Inject

private const val PLANET_URL = "planetUrl"

class PlanetScreenFragment : Fragment() {

    private var planetUrl: String? = null

    private var _binding: FragmentScreenPlanetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    private var childFrManager: FragmentManager? = null

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planetUrl = it.getString(PLANET_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenPlanetBinding.inflate(inflater, container, false)
        childFrManager= childFragmentManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.text = "Planet: $planetUrl"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.cancelGetPeopleByMovieJob()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PlanetScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(PLANET_URL, param1)
                }
            }
    }
}