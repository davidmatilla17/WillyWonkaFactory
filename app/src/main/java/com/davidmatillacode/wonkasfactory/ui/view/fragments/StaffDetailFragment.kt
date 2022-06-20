package com.davidmatillacode.wonkasfactory.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davidmatillacode.wonkasfactory.R
import com.davidmatillacode.wonkasfactory.databinding.FragmentStaffDetailBinding
import com.davidmatillacode.wonkasfactory.ui.viewmodel.StaffDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class StaffDetailFragment : Fragment() {

    private var _binding: FragmentStaffDetailBinding? = null
    private val args: StaffDetailFragmentArgs by navArgs()
    private val viewModel: StaffDetailViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStaffDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(view.context, R.drawable.ic_arrow_back)
        binding.toolbar.title = getString(R.string.staff_detail_title)

        viewModel.loadWorkerInfo(args.workerId)

        viewModel.workerDetailData.observe(viewLifecycleOwner) {
            binding.nameTV.text = it.firstName
            binding.lastnameTV.text = it.lastName
            binding.countryTV.text = it.country
            binding.proffesionTV.text = it.profession
            binding.emailTV.text = it.email
            binding.ageTV.text = it.age.toString()
            binding.idTV.text = "#${it.id}"

            binding.genderTV.text = if (it.gender != null) {
                if (it.gender.equals("F")) {
                    getString(R.string.gender_f)
                } else {
                    getString(R.string.gender_m)
                }
            } else {
                ""
            }

            Glide.with(requireContext()).load(it.image)
                .placeholder(R.drawable.ic_no_wifi)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
                .centerCrop().into(binding.profileIV)

        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}