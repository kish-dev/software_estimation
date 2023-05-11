package com.example.softwareestimation.add_employee_feature.specs

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.data.db.employees.EmployeesLevels

class AddEmployeeSpecializationViewHolder(
    private val itemView: View,
    private val listener: AddEmployeeSpecializationAdapter.AddEmployeeViewHolderListener
) : RecyclerView.ViewHolder(itemView) {

    var image: AppCompatImageView? = null
    var level: AppCompatTextView? = null
    var specDelete: AppCompatImageView? = null
    var employeeSpecialization: EmployeeSpecialization? = null

    init {
        itemView.apply {
            image = findViewById(R.id.add_employee__spec_image)
            level = findViewById(R.id.add_employee__level)
            specDelete = findViewById(R.id.add_employee__spec_delete)
        }
    }

    fun bind(bindSpec: EmployeeSpecialization, position: Int) {

        employeeSpecialization = bindSpec

        level?.text =

            itemView.context.getString(
                when (bindSpec.level) {
                    EmployeesLevels.INTERN -> {
                        R.string.intern_level
                    }
                    EmployeesLevels.JUNIOR -> {
                        R.string.junior_level
                    }
                    EmployeesLevels.MIDDLE -> {
                        R.string.middle_level
                    }
                    EmployeesLevels.SENIOR -> {
                        R.string.senior_level
                    }
                    EmployeesLevels.LEAD -> {
                        R.string.lead_level
                    }
                }
            )


        when (bindSpec.sphere) {
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

        specDelete?.setOnClickListener {
            listener.onDeleteButtonClick(position)
        }
    }
}