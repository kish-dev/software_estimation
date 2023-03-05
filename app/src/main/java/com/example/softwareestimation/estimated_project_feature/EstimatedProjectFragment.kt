package com.example.softwareestimation.estimated_project_feature

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.databinding.FragmentEstimatedProjectBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.animation.Easing.EaseInOutQuad
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EstimatedProjectFragment : Fragment() {

    private var _binding: FragmentEstimatedProjectBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EstimatedProjectViewModel by viewModels()
    private var projectName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectName = it.getString(PROJECT_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstimatedProjectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        getEstimatedProject()
    }

    private fun getEstimatedProject() {
        projectName?.let { viewModel.updateEstimatedProject(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.estimatedProject.collect {
                    with(binding) {
                        estimatedProjectTitle.text = it.projectName
                        estimatedProjectMonthValue.text =
                            "${String.format("%.1f", it.fullHumanMonth)} " +
                                    requireContext().getText(R.string.man_month)

                        it

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.projectPercentSpread.collect { projectSpread ->
                    initPieChart()
                    setPieChartDiagram(projectSpread)
                }
            }
        }
    }

    private fun setPieChartDiagram(projectPercentSpread: ProjectPercentSpreadForTypes?) {
        projectPercentSpread?.let {
            with(binding) {
                val androidPercentPair = projectPercentSpread.androidPercent?.let {
                    it to resources.getString(R.string.androidPercent)
                }

                val iosPercentPair = projectPercentSpread.iosPercent?.let {
                    it to resources.getString(R.string.iosPercent)
                }

                val analyticPercent = projectPercentSpread.analyticPercent?.let {
                    it to resources.getString(R.string.analyticPercent)
                }

                val backendPercent = projectPercentSpread.backendPercent?.let {
                    it to resources.getString(R.string.backendPercent)
                }

                val testPercent = projectPercentSpread.testPercent?.let {
                    it to resources.getString(R.string.testPercent)
                }

                val managePercent = projectPercentSpread.managePercent?.let {
                    it to resources.getString(R.string.managePercent)
                }

                val frontendPercent = projectPercentSpread.frontendPercent?.let {
                    it to resources.getString(R.string.frontendPercent)
                }
                val pieEntries = mutableListOf<PieEntry>()

                val colors: ArrayList<Int> = ArrayList()
                colors.add(Color.parseColor("#304567"))
                colors.add(Color.parseColor("#309967"))
                colors.add(Color.parseColor("#476567"))
                colors.add(Color.parseColor("#890567"))
                colors.add(Color.parseColor("#a35567"))
                colors.add(Color.parseColor("#ff5f67"))
                colors.add(Color.parseColor("#3ca567"))

                androidPercentPair?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }
                iosPercentPair?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }
                testPercent?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }
                backendPercent?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }

                analyticPercent?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }

                managePercent?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }

                frontendPercent?.let {
                    pieEntries.add(
                        PieEntry(
                            it.first.toFloat(),
                            it.second
                        )
                    )
                }


                val pieDataSet = PieDataSet(pieEntries, "")
                pieDataSet.valueTextSize = 12f
                pieDataSet.colors = colors

                val pieData = PieData(pieDataSet)
                pieData.setDrawValues(true)

                pieChart.data = pieData
                pieChart.invalidate()
            }
        }

    }

    private fun initPieChart() {
        with(binding) {
            //using percentage as values instead of amount
            pieChart.setUsePercentValues(true)

            //remove the description label on the lower left corner, default true if not set
            pieChart.description.isEnabled = false

            //enabling the user to rotate the chart, default true
            pieChart.isRotationEnabled = true
            //adding friction when rotating the pie chart
            pieChart.dragDecelerationFrictionCoef = 0.9f
            //setting the first entry start from right hand side, default starting from top
            pieChart.rotationAngle = 0f

            //highlight the entry when it is tapped, default true if not set
            pieChart.isHighlightPerTapEnabled = true
            //adding animation so the entries pop up from 0 degree
            pieChart.animateY(1400, EaseInOutQuad)
            //setting the color of the hole in the middle, default white
        }
    }


    companion object {
        private const val PROJECT_NAME = "project_name"

        @JvmStatic
        fun newInstance(productId: String): EstimatedProjectFragment =
            EstimatedProjectFragment().apply {
                arguments = Bundle().apply {
                    putString(PROJECT_NAME, productId)
                }
            }
    }

}