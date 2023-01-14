package com.example.softwareestimation.estimated_project_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.softwareestimation.databinding.FragmentEstimatedProjectBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EstimatedProjectFragment : Fragment() {

    private var _binding: FragmentEstimatedProjectBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EstimatedProjectViewModel by viewModels()
    private var projectName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectName = it.getString(PROJECT_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstimatedProjectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        getEstimatedProject()
    }

    private fun getEstimatedProject() {
        projectName?.let { viewModel.getEstimatedProject(it) }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.estimatedProject.collect {
                    with(binding) {
                        estimatedProjectTitle.text = it?.projectName
                        estimatedProjectMonthValue.text = it?.fullHumanMonth.toString()
                    }
                }
            }
        }
    }


    companion object {
        private const val PROJECT_NAME = "project_name"

        @JvmStatic
        fun newInstance(productId: String): EstimatedProjectFragment = EstimatedProjectFragment().apply {
            arguments = Bundle().apply {
                putString(PROJECT_NAME, productId)
            }
        }
    }

}