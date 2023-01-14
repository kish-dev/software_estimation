package com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.softwareestimation.R
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParameterVo

class EnterParametersCellAdapter(
    private val listener: EnterParametersListener
) :
    ListAdapter<EnterParameterCellVo, EnterParametersCellViewHolder>(
        EnterParametersVoDiffUtil()
    ) {

    interface EnterParametersListener {
        fun onCellCountChange(position: Int, type: EnterParameterCellType, count: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnterParametersCellViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.enter_parameter_cell_item, parent, false)
        return EnterParametersCellViewHolder(itemView, object : EnterParametersListener {
            override fun onCellCountChange(
                position: Int,
                type: EnterParameterCellType,
                count: Int
            ) {
                currentList[position].count = count
                listener.onCellCountChange(position, type, count)
            }

        })
    }

    override fun onBindViewHolder(holder: EnterParametersCellViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private class EnterParametersVoDiffUtil : DiffUtil.ItemCallback<EnterParameterCellVo>() {
        override fun areItemsTheSame(
            oldItem: EnterParameterCellVo,
            newItem: EnterParameterCellVo
        ): Boolean =
            oldItem == newItem && oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: EnterParameterCellVo,
            newItem: EnterParameterCellVo
        ): Boolean =
            oldItem.title == newItem.title

    }

}