package com.example.softwareestimation.fill_project_form_feature.lists.main

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R

class MainCharacteristicsViewHolder(
    private val itemView: View,
    private val listener: MainCharacteristicsAdapter.MainCharacteristicsViewHolderListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    var spinner: Spinner? = null

    init {
        itemView.apply {
            title = findViewById(R.id.main_title_tv)
            spinner = findViewById(R.id.choose_value_spinner)
        }
    }

    fun bind(mainCharacteristicsVo: MainCharacteristicsVo, position: Int) {
        title?.text = itemView.context.getText(mainCharacteristicsVo.title)

        ArrayAdapter.createFromResource(
            itemView.context,
            R.array.numeric_values,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter
        }

        spinner?.setSelection(mainCharacteristicsVo.count)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                positionSpinner: Int,
                id: Long
            ) {
                listener.onSpinnerChange(
                    position,
                    this@MainCharacteristicsViewHolder,
                    positionSpinner
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

}