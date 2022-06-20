package com.davidmatillacode.wonkasfactory.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidmatillacode.wonkasfactory.R
import com.davidmatillacode.wonkasfactory.databinding.ViewProfessionsListCellBinding
import com.davidmatillacode.wonkasfactory.ui.viewmodel.StaffListViewModel

class ProfessionsListAdapter(private val staffViewModel: StaffListViewModel) :
    RecyclerView.Adapter<ProfessionsListAdapter.ViewHolder>() {
    var proffesionsList: List<String?> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ViewProfessionsListCellBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.view_professions_list_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profession = proffesionsList.get(position)
        holder.binding.professionTV.text = profession ?: ""
        holder.binding.root.setOnClickListener {
            staffViewModel.applyProfesionFilter(profession ?: "")
        }
    }

    override fun getItemCount(): Int {
        return proffesionsList.size
    }
}