package com.example.softwareestimation.estimated_project_feature

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.softwareestimation.data.db.employees.*
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.databinding.FragmentEstimatedProjectBinding
import com.example.softwareestimation.time_diagram_feature.CellDto
import com.example.softwareestimation.time_diagram_feature.ExcelUtils
import com.example.softwareestimation.time_diagram_feature.RowDto
import com.example.softwareestimation.time_diagram_feature.TimeDiagramDto
import com.github.mikephil.charting.animation.Easing.EaseInOutQuad
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

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
                    //распределить по коэффициентам ребят, миддл - коэфф 1, сеньор - 1,5, джун - 0,5, интерн - 0,25, лид - 2
                    //чисто по зпхе
                    //
                    createExcelDiagram(
                        employees = viewModel.employees.value,
                        estimatedProject = viewModel.estimatedProject.value,
                        projectPercentSpread = projectSpread,
                    )
                }
            }
        }
    }

    private fun createExcelDiagram(
        employees: List<Employee>,
        estimatedProject: EstimatedProject,
        projectPercentSpread: ProjectPercentSpreadForTypes?,
    ) {
        projectPercentSpread?.let { notNullProjectPercentSpread ->
            var analyticsWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.analyticPercent
                    ?: 0.0) * 4.0
            var androidWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.androidPercent
                    ?: 0.0) * 4.0
            var frontendWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.frontendPercent
                    ?: 0.0) * 4.0
            var backendWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.backendPercent
                    ?: 0.0) * 4.0
            var iosWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.iosPercent
                    ?: 0.0) * 4.0
            var testWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.testPercent
                    ?: 0.0) * 4.0
            var manageWeeks =
                estimatedProject.fullHumanMonth * (notNullProjectPercentSpread.managePercent
                    ?: 0.0) * 4.0

            //будем проходиться грубым алгоритмом
            //первый кто нужен - менеджер и аналитик
            //второй кто нужен это бекендер
            //мобилки + фронт
            //тестирование


            //в каждом месяце - 4 недели

            // вычислить для следующей недели кол-во работников по специальности
            // закрасить их (либо после)
            // перейти на следующую неделю

            val newEmployeesManager = employees.filter {
                it.isSpecificWorker(EmployeeSpheres.PROJECT_MANAGER)
            }

            var currentDateForCount = getNextMonday().time
            val startBusiesTime = currentDateForCount

            val weekCounts = mutableListOf<String>()

            while (manageWeeks > 0) {
                val weekCount = getCountEmployeeFreeInWeek(currentDateForCount, newEmployeesManager)
                manageWeeks -= weekCount
                weekCounts.add(weekCount.toString())
                currentDateForCount += (ONE_DAY * 7)
            }

            val newManagerEmployees =
                markBusiesForEmployees(startBusiesTime, currentDateForCount, newEmployeesManager)
            viewModel.uploadNewEmployees(newManagerEmployees)

            val cells = weekCounts.map {
                CellDto(
                    text = it,
                    color = ""
                )
            }

            val dataTimeDiagramDto = TimeDiagramDto(
                rows = listOf(
                    RowDto(
                        cells = cells
                    )
                ),
                projectName = estimatedProject.projectName,
                startWeek = Date(startBusiesTime),
                lastWeek = Date(currentDateForCount),
                )

            ExcelUtils.exportDataIntoWorkbook(
                context = requireContext(),
                fileName = estimatedProject.projectName,
                diagramDto = dataTimeDiagramDto
            )

        }


    }

    private fun exportDataIntoWorkbook(context: Context, weekCount: List<Double>) {
        val fileName = UUID.randomUUID().toString()


    }

    private fun markBusiesForEmployees(
        startDate: Long,
        endDate: Long,
        employees: List<Employee>
    ): List<Employee> {
        val newEmployees = mutableListOf<Employee>()
        employees.forEach {
            val newBusies = it.busies.toMutableList()
            newBusies.add(EmployeeBusiness(startDate, endDate))
            newEmployees.add(
                Employee(
                    guid = it.guid,
                    name = it.name,
                    surname = it.surname,
                    specializations = it.specializations,
                    busies = newBusies
                )
            )
        }

        return newEmployees
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNextMonday(): Date {
        val localDate = LocalDate.now().dayOfWeek.name

        val plusToDate = when (localDate) {
            "MONDAY" -> 0
            "TUESDAY" -> 1
            "WEDNESDAY" -> 2
            "THURSDAY" -> 3
            "FRIDAY" -> 4
            "SATURDAY" -> 5
            "SUNDAY" -> 6

            else -> 0
        }


        val currentMonday =
            Date(
                System.currentTimeMillis() + (plusToDate * ONE_DAY)
            )

        return currentMonday
    }

    private fun getCountEmployeeFreeInWeek(monday: Long, employees: List<Employee>): Double {
        val countInDays = getCountEmployeeFreeInDay(monday, employees) +
                getCountEmployeeFreeInDay(monday + ONE_DAY, employees) +
                getCountEmployeeFreeInDay(monday + (2 * ONE_DAY), employees) +
                getCountEmployeeFreeInDay(monday + (3 * ONE_DAY), employees) +
                getCountEmployeeFreeInDay(monday + (4 * ONE_DAY), employees)

        return countInDays.toDouble() / 5
    }

    private fun getCountEmployeeFreeInDay(day: Long, employees: List<Employee>): Int {

        return employees.count {
            !it.busies.contains(
                EmployeeBusiness(
                    startDate = day,
                    endDate = day + ONE_DAY - 1
                )
            )
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
        const val PROJECT_NAME = "projectName"

        const val ONE_DAY = 86_400_000

    }

}