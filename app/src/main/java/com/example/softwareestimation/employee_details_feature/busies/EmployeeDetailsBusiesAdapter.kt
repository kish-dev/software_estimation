package com.example.softwareestimation.employee_details_feature.busies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeBusiness

class EmployeeDetailsBusiesAdapter() :
    ListAdapter<EmployeeBusiness, EmployeeDetailsBusiesViewHolder>(EmployeeBusinessDiffUtil()) {

    private class EmployeeBusinessDiffUtil : DiffUtil.ItemCallback<EmployeeBusiness>() {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeDetailsBusiesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.employee_details_busy_cell_item, parent, false)
        return EmployeeDetailsBusiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeDetailsBusiesViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
