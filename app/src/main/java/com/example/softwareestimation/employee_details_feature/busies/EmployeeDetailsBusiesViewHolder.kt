package com.example.softwareestimation.employee_details_feature.busies

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import java.text.SimpleDateFormat
import java.util.*

class EmployeeDetailsBusiesViewHolder(
    private val itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    var text: AppCompatTextView? = null
    var employeeBusiness: EmployeeBusiness? = null

    val datePattern = "dd.MM.yy"
    val simpleDateFormatter = SimpleDateFormat(datePattern)

    init {
        itemView.apply {
            text = findViewById(R.id.employee_details__business_text)
        }
    }

    fun bind(bindBusy: EmployeeBusiness, position: Int) {
        employeeBusiness = bindBusy

        val startDate = Date(bindBusy.startDate)
        val startFormattedDate = simpleDateFormatter.format(startDate)

        val endDate = Date(bindBusy.endDate)
        val endFormattedDate = simpleDateFormatter.format(endDate)

        text?.text = "${itemView.context.getString(R.string.from_date)}" +
                " $startFormattedDate " +
                "${itemView.context.getString(R.string.to_date)}" +
                " $endFormattedDate"

    }
}