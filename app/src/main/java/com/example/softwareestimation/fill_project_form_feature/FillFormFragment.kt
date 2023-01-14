package com.example.softwareestimation.fill_project_form_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.databinding.FragmentFillFormBinding
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectFragment
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParametersAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParametersCellAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FillFormFragment : Fragment() {

    private var _binding: FragmentFillFormBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: FillFormViewModel by viewModels()

    private val enterParametersAdapter = EnterParametersAdapter(
        listener = object : EnterParametersCellAdapter.EnterParametersListener {
            override fun onCellCountChange(
                position: Int,
                type: EnterParameterCellType,
                count: Int
            ) {
                updateViewModelState()
            }
        }
    )
    private val mainCharacteristicsAdapter = MainCharacteristicsAdapter(
        listener = object : MainCharacteristicsAdapter.MainCharacteristicsViewHolderListener {
            override fun onSpinnerChange(
                position: Int,
                holder: MainCharacteristicsViewHolder,
                spinnerItemPosition: Int
            ) {
                updateViewModelState()
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFillFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.functionPoint.collect {
                    enterParametersAdapter.submitList(it.enterParams)
                    mainCharacteristicsAdapter.submitList(it.mainSystemCharacteristics)
                    with(binding) {
                        projectTitleEt.setText(it.projectName)
                        projectDescriptionEt.setText(it.projectDescription)
                    }
                }
            }
        }
    }

    private fun updateViewModelState() {
        with(binding) {

            val functionPoint = FillFormFunctionPointsVo(
                projectName = projectTitleEt.text.toString(),
                projectDescription = projectDescriptionEt.text.toString(),
                projectType = when (chooseProjectTypeSpinner.selectedItemPosition) {
                    0 -> {
                        ProjectTypes.ANDROID
                    }
                    1 -> {
                        ProjectTypes.IOS
                    }
                    2 -> {
                        ProjectTypes.WEB
                    }
                    3 -> {
                        ProjectTypes.BACKEND
                    }
                    else -> {
                        ProjectTypes.ANDROID
                    }
                },
                enterParams = enterParametersAdapter.currentList,
                mainSystemCharacteristics = mainCharacteristicsAdapter.currentList,
            )
            viewModel.updateViewModelState(functionPoint)
        }
    }

    private fun initViews() {
        with(binding) {

            enterParamsRv.apply {
                adapter = enterParametersAdapter
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }

            mainCharacteristicsRv.apply {
                adapter = mainCharacteristicsAdapter
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.project_types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                chooseProjectTypeSpinner.adapter = adapter
            }

            chooseProjectTypeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        positionSpinner: Int,
                        id: Long
                    ) {
                        updateViewModelState()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            readyButton.setOnClickListener {
                val projectName = projectTitleEt.text.toString()
                if (isValid()) {
                    val functionPoint = FillFormFunctionPointsVo(
                        projectName = projectName,
                        projectDescription = projectDescriptionEt.text.toString(),
                        projectType = when (chooseProjectTypeSpinner.selectedItemPosition) {
                            0 -> {
                                ProjectTypes.ANDROID
                            }
                            1 -> {
                                ProjectTypes.IOS
                            }
                            2 -> {
                                ProjectTypes.WEB
                            }
                            3 -> {
                                ProjectTypes.BACKEND
                            }
                            else -> {
                                ProjectTypes.ANDROID
                            }
                        },
                        enterParams = enterParametersAdapter.currentList,
                        mainSystemCharacteristics = mainCharacteristicsAdapter.currentList,
                    )
                    viewModel.fillForm(functionPoint) {
                        val estimatedProjectFragment = EstimatedProjectFragment.newInstance(projectName)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                estimatedProjectFragment as Fragment,
                                EstimatedProjectFragment::class.java.simpleName
                            )
                            .addToBackStack(this@FillFormFragment.javaClass.simpleName)
                            .commit()
                    }
                }
            }

        }
    }

    private fun isValid(): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}