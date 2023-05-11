package com.example.softwareestimation.add_employee_feature

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.softwareestimation.R
import com.example.softwareestimation.add_employee_feature.busies.AddEmployeeBusiesAdapter
import com.example.softwareestimation.add_employee_feature.specs.AddEmployeeSpecializationAdapter
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import com.example.softwareestimation.databinding.FragmentAddEmployeeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class AddEmployeeFragment : Fragment() {

    private var _binding: FragmentAddEmployeeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: AddEmployeeViewModel by activityViewModels()

    private val addSpecAdapter = AddEmployeeSpecializationAdapter(
        listener = object : AddEmployeeSpecializationAdapter.AddEmployeeViewHolderListener {
            override fun onDeleteButtonClick(
                position: Int,
            ) {
                viewModel.deleteEmployeeSpec(position)
            }
        }
    )

    private val addBusyAdapter = AddEmployeeBusiesAdapter(
        listener = object : AddEmployeeBusiesAdapter.AddEmployeeViewHolderListener {
            override fun onDeleteButtonClick(position: Int) {
                viewModel.deleteEmployeeBusies(position)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

            val dividerItemDecoration =  DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                .also {
                    it.setDrawable(resources.getDrawable(R.drawable.cell_item_divider))
                }

            addEmployeeSpecRv.addItemDecoration(dividerItemDecoration)

            addEmployeeBusyRv.addItemDecoration(dividerItemDecoration)

            addEmployeeBusyRv.apply {
                adapter = addBusyAdapter
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
                        specializations = addSpecAdapter.currentList,
                        busies = addBusyAdapter.currentList
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

            addEmployeeAddBusy.setOnClickListener {
                createAddBusyDialog()
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
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listBusiness.collect { businesses ->
                    with(binding) {
                        addBusyAdapter.submitList(businesses)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listSpecialization.collect { specs ->
                    with(binding) {
                        addSpecAdapter.submitList(specs)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.specializations.collect { spec ->
                    spec?.let { notNullSpec ->
                        val list = addSpecAdapter.currentList.toMutableList()
                        list.add(notNullSpec)
                        addSpecAdapter.submitList(list)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.busies.collect { business ->
                    business?.let { notNullBusiness ->
                        val list = addBusyAdapter.currentList.toMutableList()
                        list.add(notNullBusiness)
                        addBusyAdapter.submitList(list)
                    }
                }
            }
        }
    }

    private fun createAddSpecDialog() {
        findNavController().navigate(
            R.id.action_addEmployeeFragment_to_addSpecializationDialog
        )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}