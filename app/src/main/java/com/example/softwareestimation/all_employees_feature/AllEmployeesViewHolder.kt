package com.example.softwareestimation.all_employees_feature

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeSpheres

class AllEmployeesViewHolder(
    private val itemView: View,
    private val listener: AllEmployeesAdapter.AllEmployeesViewHolderListener
) : RecyclerView.ViewHolder(itemView) {

    var name: AppCompatTextView? = null
    var surname: AppCompatTextView? = null
    var image: AppCompatImageView? = null
    var layout: ConstraintLayout? = null
    var employee: Employee? = null

    init {
        itemView.apply {
            name = findViewById(R.id.all_employees__employee_name)
            surname = findViewById(R.id.all_employees__employee_surname)
            image = findViewById(R.id.all_employees__employee_image)
            layout = findViewById(R.id.all_employees__layout)
        }
    }

    fun bind(bindEmployee: Employee, position: Int) {

        layout?.setOnClickListener {
            listener.onEmployeeClick(position, this)
        }

        employee = bindEmployee
        name?.text = bindEmployee.name
        surname?.text = bindEmployee.surname

        when (bindEmployee.specializations.firstOrNull()?.sphere) {
            null -> {}
            EmployeeSpheres.ANALYTIC -> {}
            EmployeeSpheres.ANDROID_DEVELOPER -> {}
            EmployeeSpheres.BACKEND_DEVELOPER -> {}
            EmployeeSpheres.IOS_DEVELOPER -> {}
            EmployeeSpheres.PROJECT_MANAGER -> {}
            EmployeeSpheres.TESTER -> {}
            EmployeeSpheres.WEB_DEVELOPER -> {}
        }
    }
}