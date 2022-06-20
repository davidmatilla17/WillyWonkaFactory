package com.davidmatillacode.wonkasfactory.ui.view.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidmatillacode.wonkasfactory.R
import com.davidmatillacode.wonkasfactory.databinding.FragmentStaffListBinding
import com.davidmatillacode.wonkasfactory.intefaces.OnWorkerClickListener
import com.davidmatillacode.wonkasfactory.ui.view.adapters.ProfessionsListAdapter
import com.davidmatillacode.wonkasfactory.ui.view.adapters.StaffListAdapter
import com.davidmatillacode.wonkasfactory.ui.viewmodel.StaffListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class StaffListFragment : Fragment() {

    private var _binding: FragmentStaffListBinding? = null

    private val binding get() = _binding!!
    private var filtersSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
    private var professionsSheetBehavior: BottomSheetBehavior<FrameLayout>? = null


    private val viewModel: StaffListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStaffListBinding.inflate(inflater, container, false)
        prepareView()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(viewModel.staffListData.value == null) {
            viewModel.getStaffList()
        }else{
            viewModel.reloadStaffList()
        }
    }


    fun prepareView() {
        binding.toolbar.title = getString(R.string.staff_list_title)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.staffRV.layoutManager = layoutManager
        binding.staffRV.adapter = StaffListAdapter(object : OnWorkerClickListener {
            override fun workerClick(id: Int?) {
                if(id != null) {
                    val args = Bundle()
                    args.putInt("workerId", id)
                    findNavController().navigate(R.id.action_StaffListFragment_to_StaffDetailFragment,args)
                }
            }
        })

        viewModel.staffListData.observe(viewLifecycleOwner) {
            (binding.staffRV.adapter as StaffListAdapter).staffList = it
            binding.staffRV.adapter?.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener { view ->
            filtersSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }

        prepareFiltersBottomSheet()
        prepareProfessionsBottomSheet()
    }

    fun prepareFiltersBottomSheet() {
        filtersSheetBehavior = BottomSheetBehavior.from(binding.filtersSheet)
        filtersSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        filtersSheetBehavior!!.isHideable = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.filtersView.filterProfessionTIET.focusable = View.NOT_FOCUSABLE
        } else {
            binding.filtersView.filterProfessionTIET.isFocusable = false
        }

        binding.filtersView.filterProfessionTIET.setOnClickListener {
            professionsSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.filtersView.maleRB.setOnClickListener {
            viewModel.applyGenderFilter("M")
        }
        binding.filtersView.femaleRB.setOnClickListener {
            viewModel.applyGenderFilter("F")
        }

        binding.filtersView.clearEFAB.setOnClickListener {
            binding.filtersView.genderRG.clearCheck()
            binding.filtersView.filterProfessionTIET.text = Editable.Factory.getInstance().newEditable("")
            viewModel.getStaffList()
        }

        viewModel.genderFilterData.observe(viewLifecycleOwner){
            if(it.equals("M")){
                binding.filtersView.maleRB.isChecked = true
            }else if(it.equals("F")){
                binding.filtersView.femaleRB.isChecked = true
            }else{
                binding.filtersView.genderRG.clearCheck()
            }
        }
    }

    fun prepareProfessionsBottomSheet() {
        binding.proffesionView.closeIV.setOnClickListener {
          closeProfessionBottomSheet()
        }

        professionsSheetBehavior = BottomSheetBehavior.from(binding.proffesionSheet)
        professionsSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        professionsSheetBehavior!!.isDraggable = false

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.proffesionView.professionList.layoutManager = layoutManager
        binding.proffesionView.professionList.adapter = ProfessionsListAdapter(viewModel)
        val adapter: ProfessionsListAdapter = binding.proffesionView.professionList.adapter as ProfessionsListAdapter
        viewModel.professionListFilterData.observe(viewLifecycleOwner) {
            adapter.proffesionsList = it
            adapter.notifyDataSetChanged()
        }
        binding.proffesionView.professionTIET.doOnTextChanged { text, start, before, count -> viewModel.seachProfession(
            text.toString()
        )  }

        viewModel.seachProfession()

        viewModel.professionFilterData.observe(viewLifecycleOwner){
            binding.filtersView.filterProfessionTIET.text = Editable.Factory.getInstance().newEditable(it)
            closeProfessionBottomSheet()
        }
    }

    fun closeProfessionBottomSheet(){
        hideKeyboard()
        //wait util keyboard is hidden to avoid graphical bugs with bottonsheet
        Handler(Looper.getMainLooper()).postDelayed({
            binding.proffesionView.professionTIET.text = null
            professionsSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }, 500)
    }

    fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}