package com.example.softwareestimation.add_employee_feature.busies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeBusiness

class AddEmployeeBusiesAdapter(
    private val listener: AddEmployeeViewHolderListener,
): ListAdapter<EmployeeBusiness, AddEmployeeBusiesViewHolder>(EmployeeBusinessDiffUtil()) {

    interface AddEmployeeViewHolderListener {
        fun onDeleteButtonClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddEmployeeBusiesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.add_employee__busy_cell_item, parent, false)
        return AddEmployeeBusiesViewHolder(itemView, listener)
    }

    private class EmployeeBusinessDiffUtil: DiffUtil.ItemCallback<EmployeeBusiness>() {
        override fun areItemsTheSame(
            oldItem: EmployeeBusiness,
            newItem: EmployeeBusiness
        ): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(
            oldItem: EmployeeBusiness,
            newItem: EmployeeBusiness
        ): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: AddEmployeeBusiesViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}