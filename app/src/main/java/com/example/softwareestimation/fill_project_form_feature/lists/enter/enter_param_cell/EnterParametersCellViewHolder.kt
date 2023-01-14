package com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R

class EnterParametersCellViewHolder(
    private val itemView: View,
    private val listener: EnterParametersCellAdapter.EnterParametersListener,
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    var countEditText: AppCompatEditText? = null

    init {
        itemView.apply {
            title = findViewById(R.id.enter_params_cell_title_tv)
            countEditText = findViewById(R.id.enter_params_cell_et)
        }
    }

    fun bind(enterParameterCellVo: EnterParameterCellVo, position: Int) {
        title?.text = itemView.context.getText(enterParameterCellVo.title)
        countEditText?.setText(enterParameterCellVo.count.toString())

        countEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onCellCountChange(position, enterParameterCellVo.type, s.toString().toInt())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

}