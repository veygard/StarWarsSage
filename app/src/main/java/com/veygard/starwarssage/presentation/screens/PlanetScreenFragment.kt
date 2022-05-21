package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieAnimationView
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.MainActivity
import com.veygard.starwarssage.R
import com.veygard.starwarssage.databinding.FragmentScreenPeopleBinding
import com.veygard.starwarssage.databinding.FragmentScreenPlanetBinding
import com.veygard.starwarssage.domain.model.Planet
import com.veygard.starwarssage.presentation.supports.toggleVisibility
import com.veygard.starwarssage.presentation.viewmodels.SwViewModel
import com.veygard.starwarssage.presentation.viewmodels.SwViewModelState
import com.veygard.starwarssage.presentation.widgets.NothingFoundFragment
import com.veygard.starwarssage.presentation.widgets.ShimmerFragment
import javax.inject.Inject

private const val PLANET_URL = "planetUrl"

class PlanetScreenFragment : Fragment() {

    private var planetUrl: String? = null

    private var _binding: FragmentScreenPlanetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SwViewModel by activityViewModels()

    @Inject
    lateinit var router: Router

    private var planet: Planet? = null

    private  var lottie: LottieAnimationView? = null

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planetUrl?.let { viewModel.getPlanet(it)}
        observeData()
        initLottie()

    }

    private fun initLottie() {
        lottie = _binding?.lottie
        lottie?.apply {
            speed= 4f
            repeatCount= 99
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        binding.planetTable.visibility = View.INVISIBLE
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.planetTable.visibility = View.INVISIBLE
        _binding = null
        viewModel.clear()
    }

    private fun observeData() {
        viewModel.viewModelState.addObserver { result ->
            when (result) {
                is SwViewModelState.GotPlanet -> {
                    planet = result.planet
                    activity?.title = result.planet.name
                    setFields()
                }
            }
        }

        viewModel.loadingState.addObserver { result ->
            when(result){
                true ->  _binding?.let { lottie?.playAnimation()}
                false -> _binding?.let {
                    lottie?.cancelAnimation()
                    lottie?.visibility = View.GONE
                }
            }
        }
    }

    private fun setFields() {
        _binding?.apply {
            planetRotationPeriod.text = planet?.rotation_period
            planetRotationPeriod.text = planet?.rotation_period
            planetOrbitalPeriod.text = planet?.orbital_period
            planetDiameter.text = planet?.diameter
            planetClimate.text = planet?.climate
            planetGravity.text = planet?.gravity
            planetTerrain.text = planet?.terrain
            planetPopulation.text = planet?.population
            planetTable.visibility= View.VISIBLE
        }
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