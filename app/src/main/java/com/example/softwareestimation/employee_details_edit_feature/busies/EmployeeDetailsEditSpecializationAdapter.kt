//package com.example.softwareestimation.employee_details_edit_feature.busies
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import com.example.softwareestimation.R
//import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
//import com.example.softwareestimation.employee_details_edit_feature.specs.EmployeeDetailsEditSpecializationViewHolder
//
//class EmployeeDetailsEditSpecializationAdapter(
//) : ListAdapter<EmployeeSpecialization, EmployeeDetailsEditSpecializationViewHolder>(
//    EmployeeSpecializationDiffUtil()
//) {
//
//    private class EmployeeSpecializationDiffUtil : DiffUtil.ItemCallback<EmployeeSpecialization>() {
//        override fun areItemsTheSame(
//            oldItem: EmployeeSpecialization,
//            newItem: EmployeeSpecialization
//        ): Boolean =
//            oldItem == newItem
//
//
//        override fun areContentsTheSame(
//            oldItem: EmployeeSpecialization,
//            newItem: EmployeeSpecialization
//        ): Boolean =
//            oldItem == newItem
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): EmployeeDetailsEditSpecializationViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        val itemView =
//            inflater.inflate(R.layout.employee_details_spec_cell_item, parent, false)
//        return EmployeeDetailsEditSpecializationViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: EmployeeDetailsEditSpecializationViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//}