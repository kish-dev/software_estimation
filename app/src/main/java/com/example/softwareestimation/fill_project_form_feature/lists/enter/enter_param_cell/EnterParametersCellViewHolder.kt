package com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R

class EnterParametersCellViewHolder(
    itemView: View,
    listener: EnterParametersCellAdapter.EnterParametersListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    var countEditText: AppCompatEditText? = null

    init {
        itemView.apply {
            title = findViewById(R.id.enter_params_cell_title_tv)
            countEditText = findViewById(R.id.choose_value_spinner)
        }
    }

    fun bind(enterParameterCellVo: EnterParameterCellVo) {
        title?.text = enterParameterCellVo.title
    }

}