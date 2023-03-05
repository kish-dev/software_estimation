package com.example.softwareestimation.all_employees_feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.Employee

class AllEmployeesAdapter(
    private val listener: AllEmployeesViewHolderListener
) : ListAdapter<Employee, AllEmployeesViewHolder>(
    EmployeeProjectDiffUtil()
) {

    interface AllEmployeesViewHolderListener {
        fun onEmployeeClick(position: Int, holder: AllEmployeesViewHolder)
    }

    private class EmployeeProjectDiffUtil : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(
            oldItem: Employee,
            newItem: Employee
        ): Boolean =
            oldItem.guid == newItem.guid && oldItem.hashCode() == newItem.hashCode()


        override fun areContentsTheSame(
            oldItem: Employee,
            newItem: Employee
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllEmployeesViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.all_employees_cell_item, parent, false)
        return AllEmployeesViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: AllEmployeesViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}