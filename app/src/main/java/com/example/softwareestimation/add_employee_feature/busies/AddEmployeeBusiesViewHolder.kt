package com.example.softwareestimation.add_employee_feature.busies

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import java.text.SimpleDateFormat
import java.util.*

class AddEmployeeBusiesViewHolder(
    private val itemView: View,
    private val listener: AddEmployeeBusiesAdapter.AddEmployeeViewHolderListener
) : RecyclerView.ViewHolder(itemView) {

    var text: AppCompatTextView? = null
    var businessDelete: AppCompatImageView? = null
    var employeeBusiness: EmployeeBusiness? = null

    val datePattern = "dd.MM.yy"
    val simpleDateFormatter = SimpleDateFormat(datePattern)

    init {
        itemView.apply {
            text = findViewById(R.id.add_employee__business_text)
            businessDelete = findViewById(R.id.add_employee__business_delete)
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

        businessDelete?.setOnClickListener {
            listener.onDeleteButtonClick(position)
        }
    }

}