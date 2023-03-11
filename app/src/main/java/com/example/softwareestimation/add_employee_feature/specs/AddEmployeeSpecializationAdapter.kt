package com.example.softwareestimation.add_employee_feature.specs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization

class AddEmployeeSpecializationAdapter(
    private val listener: AddEmployeeViewHolderListener
) : ListAdapter<EmployeeSpecialization, AddEmployeeSpecializationViewHolder>(
    EmployeeSpecializationDiffUtil()
) {

    interface AddEmployeeViewHolderListener {
        fun onDeleteButtonClick(position: Int)
    }

    private class EmployeeSpecializationDiffUtil : DiffUtil.ItemCallback<EmployeeSpecialization>() {
        override fun areItemsTheSame(
            oldItem: EmployeeSpecialization,
            newItem: EmployeeSpecialization
        ): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(
            oldItem: EmployeeSpecialization,
            newItem: EmployeeSpecialization
        ): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddEmployeeSpecializationViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.add_employee_spec_cell_item, parent, false)
        return AddEmployeeSpecializationViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: AddEmployeeSpecializationViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}