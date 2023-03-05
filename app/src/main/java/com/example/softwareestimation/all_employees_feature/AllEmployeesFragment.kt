package com.example.softwareestimation.all_employees_feature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.databinding.FragmentAllEmployeesBinding
import com.example.softwareestimation.fill_project_form_feature.FillFormFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllEmployeesFragment : Fragment() {

    private var _binding: FragmentAllEmployeesBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: AllEmployeesViewModel by viewModels()

    private val allEmployeesAdapter = AllEmployeesAdapter(
        listener = object : AllEmployeesAdapter.AllEmployeesViewHolderListener {
            override fun onEmployeeClick(position: Int, holder: AllEmployeesViewHolder) {
                //TODO add navigation to screen editEmployeesFragment/employeeFragment
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllEmployeesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.getAllEmployees(null)
    }

    private fun initViews() {
        with(binding) {

            //TODO add AddEmployeeFragment
            allEmployeesAddEmployee.setOnClickListener {
                val fillFormFragment = FillFormFragment()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        fillFormFragment as Fragment,
                        FillFormFragment::class.java.simpleName
                    )
                    .addToBackStack(this@AllEmployeesFragment.javaClass.simpleName)
                    .commit()
            }

            allEmployeesSearchEmployees.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.getAllEmployees(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            allEmployeesRv.apply {
                adapter = allEmployeesAdapter
                layoutManager = LinearLayoutManager(
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
                viewModel.allEmployee.collect {
                    allEmployeesAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}