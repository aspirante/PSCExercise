package com.platform_science.routing_shipments.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.platform_science.routing_shipments.R
import com.platform_science.routing_shipments.common.Constants.SPLASH_SCREEN_DELAY
import com.platform_science.routing_shipments.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen class
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val mDelayJob: CompletableJob = Job()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater)
        splash()
        return binding.root
    }

    /**
     * Use to display splash screen with a delay
     */
    private fun splash() {
        CoroutineScope(Dispatchers.Main).launch(mDelayJob) {
            delay(SPLASH_SCREEN_DELAY)
            findNavController().navigate(R.id.action_splashFragment_to_shipmentsListFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}