package com.example.softwareestimation.add_employee_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.add_employee_feature.specs.AddEmployeeSpecializationAdapter
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import com.example.softwareestimation.databinding.FragmentAddEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.specializations.collect { spec ->
                    with(binding) {
                        spec?.let { notNullSpec ->
                            val list = addSpecAdapter.currentList.toMutableList()
                            list.add(notNullSpec)
                            addSpecAdapter.submitList(list)
                        }

                    }
                }
            }
        }
    }

    private fun createAddSpecDialog() {

        val specs: MutableList<EmployeeSpecialization> = mutableListOf()

        findNavController().navigate(
            R.id.action_addEmployeeFragment_to_addSpecializationDialog
        )

}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}