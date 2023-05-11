package com.example.softwareestimation.employee_details_edit_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.add_employee_feature.EmployeeVo
import com.example.softwareestimation.add_employee_feature.busies.AddEmployeeBusiesAdapter
import com.example.softwareestimation.add_employee_feature.specs.AddEmployeeSpecializationAdapter
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import com.example.softwareestimation.databinding.FragmentEmployeeDetailsEditBinding
import com.example.softwareestimation.employee_details_feature.EmployeeDetailsFragment.Companion.EMPLOYEE_ID
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class EmployeeDetailsEditFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailsEditBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EmployeeDetailsEditViewModel by viewModels()

    private val specAdapter = AddEmployeeSpecializationAdapter(
        listener = object : AddEmployeeSpecializationAdapter.AddEmployeeViewHolderListener {
            override fun onDeleteButtonClick(position: Int) {
                viewModel.deleteEmployeeSpec(position)
            }
        }
    )

    private val busyAdapter = AddEmployeeBusiesAdapter(
        listener = object : AddEmployeeBusiesAdapter.AddEmployeeViewHolderListener {
            override fun onDeleteButtonClick(position: Int) {
                viewModel.deleteEmployeeBusy(position)
            }
        }
    )

    private var employeeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employeeId = it.getString(EMPLOYEE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeDetailsEditBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        employeeId?.let { viewModel.getEmployee(it) }
    }

    private fun initViews() {
        with(binding) {

            employeeDetailsEditSpecRv.apply {
                adapter = specAdapter
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }

            employeeDetailsEditBusyRv.apply {
                adapter = busyAdapter
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }


            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                    .also {
                        it.setDrawable(resources.getDrawable(R.drawable.cell_item_divider))
                    }

            employeeDetailsEditSpecRv.addItemDecoration(dividerItemDecoration)

            employeeDetailsEditBusyRv.addItemDecoration(dividerItemDecoration)

            employeeDetailsEditAddSpec.setOnClickListener {
                createAddSpecDialog()
            }

            employeeDetailsEditAddBusy.setOnClickListener {
                createAddBusyDialog()
            }

            employeeDetailsEditSaveButton.setOnClickListener {

                val newEmployee = employeeId?.let { guid ->
                    Employee(
                        guid = guid,
                        name = employeeDetailsEditName.text.toString(),
                        surname = employeeDetailsEditSurname.text.toString(),
                        specializations = specAdapter.currentList,
                        busies = busyAdapter.currentList,
                    )
                }

                newEmployee?.let { emp ->
                    viewModel.updateEmployee(emp)
                    val bundle = bundleOf(EMPLOYEE_ID to newEmployee.guid)
                    findNavController().navigate(
                        R.id.action_employeeDetailsEditFragment_to_employeeDetailsFragment,
                        bundle
                    )
                }
            }
        }
    }

    private fun createAddBusyDialog() {
        val now = Calendar.getInstance()
        val picker = MaterialDatePicker
            .Builder
            .dateRangePicker()
            .setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
            .build()

        picker.addOnPositiveButtonClickListener {
            viewModel.addBusy(
                business = EmployeeBusiness(
                    startDate = it.first,
                    endDate = it.second,
                )
            )
            picker.dismiss()
        }

        picker.show(activity?.supportFragmentManager!!, picker.toString())

    }

    private fun createAddSpecDialog() {
        findNavController().navigate(
            R.id.action_addEmployeeFragment_to_addSpecializationDialog
        )
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.employee.collect { employee ->
                    with(binding) {
                        employeeDetailsEditName.setText(employee.name)
                        employeeDetailsEditSurname.setText(employee.surname)
                        specAdapter.submitList(employee.specializations)
                        busyAdapter.submitList(employee.busies)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listBusiness.collect { businesses ->
                    with(binding) {
                        busyAdapter.submitList(businesses)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listSpecialization.collect { specs ->
                    with(binding) {
                        specAdapter.submitList(specs)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.specializations.collect { spec ->
                    spec?.let { notNullSpec ->
                        val list = specAdapter.currentList.toMutableList()
                        list.add(notNullSpec)
                        specAdapter.submitList(list)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.busies.collect { business ->
                    business?.let { notNullBusiness ->
                        val list = busyAdapter.currentList.toMutableList()
                        list.add(notNullBusiness)
                        busyAdapter.submitList(list)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}