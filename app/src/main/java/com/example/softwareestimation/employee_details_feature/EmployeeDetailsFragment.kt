package com.example.softwareestimation.employee_details_feature

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
import com.example.softwareestimation.add_employee_feature.EmployeeVo
import com.example.softwareestimation.add_employee_feature.specs.AddEmployeeSpecializationAdapter
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.databinding.FragmentEmployeeDetailsBinding
import com.example.softwareestimation.databinding.FragmentEstimatedProjectBinding
import com.example.softwareestimation.employee_details_feature.specs.EmployeeDetailsSpecializationAdapter
import com.example.softwareestimation.employee_details_feature.specs.EmployeeDetailsSpecializationViewHolder
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectFragment
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EmployeeDetailsViewModel by viewModels()

    private val specAdapter = EmployeeDetailsSpecializationAdapter()

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
        _binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        with(binding) {

            employeeDetailsSpecRv.apply {
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
                        employeeDetailsName.text = employee.name
                        employeeDetailsSurname.text = employee.surname
                        specAdapter.submitList(employee.specializations)
                    }
                }
            }
        }

    }

    companion object {

        private const val EMPLOYEE_ID = "employee_id"

        @JvmStatic
        fun newInstance(employeeId: String): Fragment {
            return EmployeeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(EMPLOYEE_ID, employeeId)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}