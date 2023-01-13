package com.example.softwareestimation.fill_project_form_feature.lists.main

import android.view.View
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R

class MainCharacteristicsViewHolder(
    itemView: View,
    listener: MainCharacteristicsAdapter.MainCharacteristicsListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    var spinner: Spinner? = null

    init {
        itemView.apply {
            title = findViewById(R.id.main_title_tv)
            spinner = findViewById(R.id.choose_value_spinner)
        }
    }

    fun bind(mainCharacteristicsVo: MainCharacteristicsVo) {
        title?.text = mainCharacteristicsVo.title
        spinner?.setSelection(mainCharacteristicsVo.count)
    }

}