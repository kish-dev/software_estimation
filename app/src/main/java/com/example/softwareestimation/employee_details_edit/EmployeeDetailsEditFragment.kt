package com.example.softwareestimation.employee_details_edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.databinding.FragmentEmployeeDetailsBinding
import com.example.softwareestimation.employee_details_feature.specs.EmployeeDetailsSpecializationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeDetailsEditFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EmployeeDetailsEditViewModel by viewModels()

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
            return EmployeeDetailsEditFragment().apply {
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