package com.example.softwareestimation.all_projects_feature

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject

class AllProjectsViewHolder(
    private val itemView: View,
    private val listener: AllProjectsAdapter.AllProjectsViewHolderListener
) : RecyclerView.ViewHolder(itemView) {

    var title: AppCompatTextView? = null
    var image: AppCompatImageView? = null
    var layout: ConstraintLayout? = null
    var project: EstimatedProject? = null

    init {
        itemView.apply {
            title = findViewById(R.id.all_projects__project_name)
            image = findViewById(R.id.all_projects__project_image)
            layout = findViewById(R.id.all_projects__layout)
        }
    }

    fun bind(estimatedProject: EstimatedProject, position: Int) {

        layout?.setOnClickListener {
            listener.onProjectClick(position, this)
        }

        project = estimatedProject
        title?.text = estimatedProject.projectName
        when (estimatedProject.projectTypes) {
            ProjectTypes.MOBILE -> {
                image?.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_phone))
            }

            ProjectTypes.WEB -> {
                image?.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_web))
            }
        }
    }
}