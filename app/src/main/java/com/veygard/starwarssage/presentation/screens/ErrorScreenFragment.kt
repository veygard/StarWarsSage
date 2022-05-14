package com.veygard.starwarssage.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.veygard.starwarssage.databinding.FragmentScreenErrorBinding
import com.veygard.starwarssage.presentation.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ErrorScreenFragment : Fragment() {

    private var _binding: FragmentScreenErrorBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReconnectListener()
    }

    private fun setReconnectListener() {
        binding.reconnectButton.setOnClickListener {
            router.navigateTo(Screens.moviesScreen())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}