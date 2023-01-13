package com.example.softwareestimation.fill_project_form_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.data.FunctionPoints
import com.example.softwareestimation.data.ProjectTypes
import com.example.softwareestimation.databinding.FragmentFillFormBinding
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParametersAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParametersCellAdapter
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FillFormFragment : Fragment() {

    private var _binding: FragmentFillFormBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: FillFormViewModel by viewModels()

    private val enterParametersAdapter = EnterParametersAdapter()
    private val mainCharacteristicsAdapter = MainCharacteristicsAdapter()

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
        view
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

            readyButton.setOnClickListener {
                if (isValid()) {
                    val functionPoint = FunctionPoints(
                        guid = UUID.randomUUID().toString(),
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
                    viewModel
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