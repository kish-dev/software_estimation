package com.example.softwareestimation.fill_project_form_feature.lists.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.softwareestimation.R

class MainCharacteristicsAdapter(
    private val listener: MainCharacteristicsViewHolderListener,
) :
    ListAdapter<MainCharacteristicsVo, MainCharacteristicsViewHolder>(
        MainCharacteristicsVoDiffUtil()
    ) {

    interface MainCharacteristicsViewHolderListener {
        fun onSpinnerChange(
            position: Int,
            holder: MainCharacteristicsViewHolder,
            spinnerItemPosition: Int
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCharacteristicsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.enter_main_parameter_cell_item, parent, false)
        return MainCharacteristicsViewHolder(itemView, object : MainCharacteristicsViewHolderListener {
            override fun onSpinnerChange(
                position: Int,
                holder: MainCharacteristicsViewHolder,
                spinnerItemPosition: Int
            ) {
                currentList[position].count = spinnerItemPosition
                listener.onSpinnerChange(position, holder, spinnerItemPosition)
            }

        })
    }

    override fun onBindViewHolder(holder: MainCharacteristicsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private class MainCharacteristicsVoDiffUtil : DiffUtil.ItemCallback<MainCharacteristicsVo>() {
        override fun areItemsTheSame(
            oldItem: MainCharacteristicsVo,
            newItem: MainCharacteristicsVo
        ): Boolean =
            oldItem == newItem && oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: MainCharacteristicsVo,
            newItem: MainCharacteristicsVo
        ): Boolean =
            oldItem.title == newItem.title &&
                    oldItem.count == newItem.count

    }

}