package com.example.softwareestimation.fill_project_form_feature.lists.enter

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R

class EnterParametersViewHolder(
    itemView: View,
    listener: EnterParametersAdapter.EnterParametersListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null

    init {
        itemView.apply {
            title = findViewById(R.id.enter_params_title_tv)
        }
    }

    fun bind(enterParameterVo: EnterParameterVo) {
        title?.text = enterParameterVo.title
    }

}