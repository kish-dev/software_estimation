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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.databinding.FragmentEmployeeDetailsEditBinding
import com.example.softwareestimation.employee_details_edit_feature.specs.EmployeeDetailsEditSpecializationAdapter
import com.example.softwareestimation.employee_details_feature.EmployeeDetailsFragment.Companion.EMPLOYEE_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeDetailsEditFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailsEditBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EmployeeDetailsEditViewModel by viewModels()

    private val specAdapter = EmployeeDetailsEditSpecializationAdapter()

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
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.employee.collect { employee ->
                    with(binding) {
                        employeeDetailsEditName.setText(employee.name)
                        employeeDetailsEditSurname.setText(employee.surname)
                        specAdapter.submitList(employee.specializations)
                        employeeDetailsEditSaveButton.setOnClickListener {

                            viewModel.updateEmployee(
                                Employee(
                                    employee.guid,
                                    employeeDetailsEditName.text.toString(),
                                    employeeDetailsEditSurname.text.toString(),
                                    specAdapter.currentList,
                                )
                            )

                            val bundle = bundleOf(EMPLOYEE_ID to employee.guid)

                            findNavController().navigate(
                                R.id.action_employeeDetailsEditFragment_to_employeeDetailsFragment,
                                bundle
                            )
                        }
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