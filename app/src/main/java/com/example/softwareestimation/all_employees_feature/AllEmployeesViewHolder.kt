package com.example.softwareestimation.all_employees_feature

import android.annotation.SuppressLint
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

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(bindEmployee: Employee, position: Int) {

        layout?.setOnClickListener {
            listener.onEmployeeClick(position, this, bindEmployee.guid)
        }

        employee = bindEmployee
        name?.text = bindEmployee.name
        surname?.text = bindEmployee.surname

        when (bindEmployee.specializations.firstOrNull()?.sphere) {
            null -> {}
            EmployeeSpheres.ANALYTIC -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_analytic))
            }
            EmployeeSpheres.ANDROID_DEVELOPER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_phone))
            }
            EmployeeSpheres.BACKEND_DEVELOPER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_backend_dev))
            }
            EmployeeSpheres.IOS_DEVELOPER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_phone))
            }
            EmployeeSpheres.PROJECT_MANAGER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_manager))
            }
            EmployeeSpheres.TESTER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_tester))
            }
            EmployeeSpheres.WEB_DEVELOPER -> {
                image?.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_web))
            }
        }
    }
}