package com.example.softwareestimation.all_projects_feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject

class AllProjectsAdapter(
    private val listener: AllProjectsViewHolderListener
) : ListAdapter<EstimatedProject, AllProjectsViewHolder>(
    EstimatedProjectDiffUtil()
) {

    interface AllProjectsViewHolderListener {
        fun onProjectClick(position: Int, holder: AllProjectsViewHolder)
    }

    private class EstimatedProjectDiffUtil : DiffUtil.ItemCallback<EstimatedProject>() {
        override fun areItemsTheSame(
            oldItem: EstimatedProject,
            newItem: EstimatedProject
        ): Boolean =
            oldItem.guid == newItem.guid && oldItem.hashCode() == newItem.hashCode()


        override fun areContentsTheSame(
            oldItem: EstimatedProject,
            newItem: EstimatedProject
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProjectsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView =
            inflater.inflate(R.layout.all_projects_cell_item, parent, false)
        return AllProjectsViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: AllProjectsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}