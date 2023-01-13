package com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.softwareestimation.R

class EnterParametersCellAdapter :
    ListAdapter<EnterParameterCellVo, EnterParametersCellViewHolder>(
        EnterParametersVoDiffUtil()
    ) {

    interface EnterParametersListener {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnterParametersCellViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.enter_main_parameter_cell_item, parent, false)
        return EnterParametersCellViewHolder(itemView, object : EnterParametersListener {

        })
    }

    override fun onBindViewHolder(holder: EnterParametersCellViewHolder, position: Int) {
        holder.bind(getItem(position))
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