package com.example.softwareestimation.fill_project_form_feature

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.databinding.FragmentFillFormBinding
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectFragment.Companion.PROJECT_NAME
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParametersAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParametersCellAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FillFormFragment : Fragment() {

    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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
        initViewModelState()
        return binding.root
    }

    private fun checkPermissionsAtRuntime(): Boolean {
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            PERMISSIONS,
            Constants.REQUEST_PERMISSION_ALL
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        if (!checkPermissionsAtRuntime()) {
            requestPermissions()
        }
    }

    private fun initViewModelState() {
        viewModel.updateViewModelState(FillFormFunctionPointsVo.baseState())
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
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
                        ProjectTypes.MOBILE
                    }
                    1 -> {
                        ProjectTypes.WEB
                    }
                    else -> {
                        ProjectTypes.MOBILE
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
                                ProjectTypes.MOBILE
                            }
                            1 -> {
                                ProjectTypes.WEB
                            }
                            else -> {
                                ProjectTypes.MOBILE
                            }
                        },
                        enterParams = enterParametersAdapter.currentList,
                        mainSystemCharacteristics = mainCharacteristicsAdapter.currentList,
                    )
                    viewModel.fillForm(functionPoint) {
                        val bundle = bundleOf(PROJECT_NAME to projectName)

                        findNavController().navigate(
                            R.id.action_fillFormFragment_to_estimatedProjectFragment,
                            bundle
                        )
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