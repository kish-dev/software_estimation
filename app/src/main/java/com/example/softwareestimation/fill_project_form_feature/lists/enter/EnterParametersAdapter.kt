package com.example.softwareestimation.fill_project_form_feature.lists.enter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.softwareestimation.R

class EnterParametersAdapter :
    ListAdapter<EnterParameterVo, EnterParametersViewHolder>(
        EnterParametersVoDiffUtil()
    ) {

    interface EnterParametersListener {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnterParametersViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.enter_main_parameter_cell_item, parent, false)
        return EnterParametersViewHolder(itemView, object : EnterParametersListener {

        })
    }

    override fun onBindViewHolder(holder: EnterParametersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class EnterParametersVoDiffUtil : DiffUtil.ItemCallback<EnterParameterVo>() {
        override fun areItemsTheSame(
            oldItem: EnterParameterVo,
            newItem: EnterParameterVo
        ): Boolean =
            oldItem == newItem && oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: EnterParameterVo,
            newItem: EnterParameterVo
        ): Boolean =
            oldItem.title == newItem.title

    }

}