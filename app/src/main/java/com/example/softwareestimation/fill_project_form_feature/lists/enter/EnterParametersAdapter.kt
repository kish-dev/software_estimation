package com.example.softwareestimation.fill_project_form_feature.lists.enter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.softwareestimation.R
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParametersCellAdapter

class EnterParametersAdapter(
    private val listener: EnterParametersCellAdapter.EnterParametersListener
) :
    ListAdapter<EnterParameterVo, EnterParametersViewHolder>(
        EnterParametersVoDiffUtil()
    ) {

    interface EnterParametersListener {
        fun onCountChange(
            position: Int,
            type: EnterParameterCellType,
            count: Int,
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnterParametersViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.enter_params_view, parent, false)
        return EnterParametersViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: EnterParametersViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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