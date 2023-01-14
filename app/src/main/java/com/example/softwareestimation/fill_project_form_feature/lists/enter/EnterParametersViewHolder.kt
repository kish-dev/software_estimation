package com.example.softwareestimation.fill_project_form_feature.lists.enter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParametersCellAdapter

class EnterParametersViewHolder(
    private val itemView: View,
    private val listener: EnterParametersCellAdapter.EnterParametersListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    private var rv: RecyclerView? = null

    private var enterParametersCellAdapter: EnterParametersCellAdapter = EnterParametersCellAdapter(
        object : EnterParametersCellAdapter.EnterParametersListener {
            override fun onCellCountChange(
                position: Int,
                type: EnterParameterCellType,
                count: Int
            ) {
                listener.onCellCountChange(position, type, count)
            }

        }
    )

    init {
        itemView.apply {
            title = findViewById(R.id.enter_params_title_tv)
            rv = findViewById(R.id.enter_param_cell_rv)

            rv?.apply {
                adapter = enterParametersCellAdapter
                layoutManager =
                    LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
            }
        }
    }

    fun bind(enterParameterVo: EnterParameterVo, position: Int) {
        title?.text = itemView.context.getText(enterParameterVo.title)
        enterParametersCellAdapter.submitList(enterParameterVo.listOfParams)
    }

}