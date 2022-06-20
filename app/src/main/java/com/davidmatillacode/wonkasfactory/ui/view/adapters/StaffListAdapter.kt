package com.davidmatillacode.wonkasfactory.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davidmatillacode.wonkasfactory.R
import com.davidmatillacode.wonkasfactory.data.model.entities.StaffWorker
import com.davidmatillacode.wonkasfactory.databinding.ViewStaffListCellBinding
import com.davidmatillacode.wonkasfactory.intefaces.OnWorkerClickListener

class StaffListAdapter(val listener: OnWorkerClickListener) :
    RecyclerView.Adapter<StaffListAdapter.StaffListViewHolder>() {

    var staffList: List<StaffWorker> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return StaffListViewHolder(
            layoutInflater.inflate(
                R.layout.view_staff_list_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaffListViewHolder, position: Int) {
        val worker = staffList[position]
        holder.binding.genderTV.text = if (worker.gender != null) {
            if (worker.gender.equals("F")) {
                holder.itemView.context.getString(R.string.gender_f)
            } else {
                holder.itemView.context.getString(R.string.gender_m)
            }
        } else {
            ""
        }

        holder.binding.nameTV.text = "${worker.firstName} ${worker.lastName}"
        holder.binding.proffesionTV.text = worker.profession ?: ""
        holder.binding.idTV.text = "#${worker.id}"

        holder.binding.root.setOnClickListener {
            listener.workerClick(worker.id)
        }

        Glide.with(holder.binding.profileIV.context).load(worker.image)
            .placeholder(R.drawable.ic_no_wifi)
            .diskCacheStrategy(
                DiskCacheStrategy.ALL
            )
            .centerCrop().into(holder.binding.profileIV)
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    class StaffListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ViewStaffListCellBinding.bind(itemView)
    }
}