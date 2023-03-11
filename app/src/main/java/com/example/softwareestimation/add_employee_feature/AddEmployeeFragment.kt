package com.example.softwareestimation.add_employee_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.add_employee_feature.specs.AddEmployeeSpecializationAdapter
import com.example.softwareestimation.all_employees_feature.AllEmployeesFragment
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.data.db.employees.EmployeesLevels
import com.example.softwareestimation.databinding.FragmentAddEmployeeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEmployeeFragment : Fragment() {

    private var _binding: FragmentAddEmployeeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: AddEmployeeViewModel by viewModels()

    private val addSpecAdapter = AddEmployeeSpecializationAdapter(
        listener = object : AddEmployeeSpecializationAdapter.AddEmployeeViewHolderListener {
            override fun onDeleteButtonClick(
                position: Int,
            ) {
                viewModel.deleteEmployeeSpec(position)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddEmployeeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        with(binding) {

            addEmployeeSpecRv.apply {
                adapter = addSpecAdapter
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }

            addEmployeeReady.setOnClickListener {
                viewModel.addEmployee(
                    EmployeeVo(
                        name = addEmployeeName.text.toString(),
                        surname = addEmployeeSurname.text.toString(),
                        specializations = addSpecAdapter.currentList
                    )
                ) {
                    findNavController().navigate(
                        R.id.action_addEmployeeFragment_to_allEmployeesFragment
                    )
                }
            }

            addEmployeeAddSpec.setOnClickListener {
                createAddSpecDialog()
            }

        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.employee.collect { employee ->
                    with(binding) {
                        addEmployeeName.setText(employee.name)
                        addEmployeeSurname.setText(employee.surname)
                        addSpecAdapter.submitList(employee.specializations)
                    }
                }
            }
        }
    }

    private fun createAddSpecDialog() {

        val levels: Array<String> =
            arrayOf(
                EmployeesLevels.INTERN.name,
                EmployeesLevels.JUNIOR.name,
                EmployeesLevels.MIDDLE.name,
                EmployeesLevels.SENIOR.name,
                EmployeesLevels.LEAD.name,
            )

        val spheres: Array<String> =
            arrayOf(
                EmployeeSpheres.ANALYTIC.name,
                EmployeeSpheres.ANDROID_DEVELOPER.name,
                EmployeeSpheres.BACKEND_DEVELOPER.name,
                EmployeeSpheres.IOS_DEVELOPER.name,
                EmployeeSpheres.PROJECT_MANAGER.name,
                EmployeeSpheres.TESTER.name,
                EmployeeSpheres.WEB_DEVELOPER.name,

            )

//        MaterialAlertDialogBuilder(requireContext(),)
//            .setMessage(resources.getString(R.string.specs))
//            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                // Respond to negative button press
//            }
//            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
//                // Respond to positive button press
//            }
//            .setSingleChoiceItems(levels, 1) {dialog, which ->
//
//            }
//            .setSingleChoiceItems(spheres, 1) {dialog, which ->
//
//            }
//            .show()

        val singleItems = arrayOf("Item 1", "Item 2", "Item 3")
        val checkedItem = 1

        MaterialAlertDialogBuilder(requireContext(),)
            .setMessage(resources.getString(R.string.specs))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
            }
            // Single-choice items (initialized with checked item)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                // Respond to item chosen
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}