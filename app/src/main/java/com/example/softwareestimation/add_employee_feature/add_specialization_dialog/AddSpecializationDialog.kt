package com.example.softwareestimation.add_employee_feature.add_specialization_dialog

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.softwareestimation.add_employee_feature.AddEmployeeViewModel
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.data.db.employees.EmployeesLevels


class AddSpecializationDialog : DialogFragment() {

    private val viewModel: AddEmployeeViewModel by activityViewModels()

    var chosenSpec: EmployeeSpheres = EmployeeSpheres.ANALYTIC
    var chosenLevel: EmployeesLevels = EmployeesLevels.INTERN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            com.example.softwareestimation.R.layout.add_employee__add_spec_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }

    private fun initViews(view: View) {
        val specSpinner: Spinner? =
            view.findViewById(com.example.softwareestimation.R.id.add_specialization_dialog__add_spheres)
        val levelSpinner: Spinner? =
            view.findViewById(com.example.softwareestimation.R.id.add_specialization_dialog__add_levels)

        val cancelButton: AppCompatButton? =
            view.findViewById(com.example.softwareestimation.R.id.add_specialization_dialog__cancel_button)
        val okButton: AppCompatButton? =
            view.findViewById(com.example.softwareestimation.R.id.add_specialization_dialog__ok_button)


            ArrayAdapter.createFromResource(
                view.context,
                com.example.softwareestimation.R.array.specs_spinner,
                R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                specSpinner?.adapter = adapter
            }


        specSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                positionSpinner: Int,
                id: Long
            ) {
                chosenSpec = when (specSpinner?.selectedItemPosition) {
                    null -> {
                        EmployeeSpheres.ANALYTIC
                    }
                    0 -> {
                        EmployeeSpheres.ANALYTIC
                    }
                    1 -> {
                        EmployeeSpheres.ANALYTIC
                    }
                    2 -> {
                        EmployeeSpheres.BACKEND_DEVELOPER
                    }
                    3 -> {
                        EmployeeSpheres.IOS_DEVELOPER
                    }
                    4 -> {
                        EmployeeSpheres.PROJECT_MANAGER
                    }
                    5 -> {
                        EmployeeSpheres.TESTER
                    }
                    6 -> {
                        EmployeeSpheres.WEB_DEVELOPER
                    }
                    else -> {
                        EmployeeSpheres.ANALYTIC
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

            ArrayAdapter.createFromResource(
                view.context,
                com.example.softwareestimation.R.array.levels_spinner,
                R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                levelSpinner?.adapter = adapter
            }

        levelSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                positionSpinner: Int,
                id: Long
            ) {
                chosenLevel = when (levelSpinner?.selectedItemPosition) {
                    null -> {
                        EmployeesLevels.INTERN
                    }
                    0 -> {
                        EmployeesLevels.INTERN
                    }
                    1 -> {
                        EmployeesLevels.JUNIOR

                    }
                    2 -> {
                        EmployeesLevels.MIDDLE
                    }
                    3 -> {
                        EmployeesLevels.SENIOR
                    }
                    4 -> {
                        EmployeesLevels.LEAD
                    }
                    else -> {
                        EmployeesLevels.INTERN
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        cancelButton?.setOnClickListener {
            dialog?.dismiss()
        }

        okButton?.setOnClickListener {
            viewModel.addSpecialization(
                EmployeeSpecialization(
                    sphere = chosenSpec,
                    level = chosenLevel
                )
            )
            dialog?.dismiss()
        }
    }

}