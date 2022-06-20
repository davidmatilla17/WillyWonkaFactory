package com.davidmatillacode.wonkasfactory.ui.view.fragments


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.davidmatillacode.wonkasfactory.R
import com.davidmatillacode.wonkasfactory.ui.viewmodel.SplashViewModel
import com.davidmatillacode.wonkasfactory.ui.viewmodel.StaffDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllData()
        viewModel.loadEndEvent.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_SplashFragment_to_FirstFragment)
            }
        }
    }

}