package com.example.softwareestimation.all_projects_feature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softwareestimation.R
import com.example.softwareestimation.databinding.FragmentAllProjectsBinding
import com.example.softwareestimation.databinding.FragmentEstimatedProjectBinding
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectFragment
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectViewModel
import com.example.softwareestimation.fill_project_form_feature.FillFormFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProjectsFragment : Fragment() {

    private var _binding: FragmentAllProjectsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: AllProjectsViewModel by viewModels()

    private val allProjectsAdapter = AllProjectsAdapter(
        listener = object : AllProjectsAdapter.AllProjectsViewHolderListener {
            override fun onProjectClick(position: Int, holder: AllProjectsViewHolder) {
                holder.project?.let {
                    val estimatedProjectFragment =
                        EstimatedProjectFragment.newInstance(it.projectName)
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            estimatedProjectFragment as Fragment,
                            EstimatedProjectFragment::class.java.simpleName
                        )
                        .addToBackStack(this@AllProjectsFragment.javaClass.simpleName)
                        .commit()
                }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllProjectsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.getAllProjects("")
    }

    private fun initViews() {
        with(binding) {

            plusToDelete.setOnClickListener {
                val fillFormFragment = FillFormFragment()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        fillFormFragment as Fragment,
                        FillFormFragment::class.java.simpleName
                    )
                    .addToBackStack(this@AllProjectsFragment.javaClass.simpleName)
                    .commit()
            }

            searchProject.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.getAllProjects(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            allProjectsRv.apply {
                adapter = allProjectsAdapter
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
                viewModel.allProjects.collect {
                    allProjectsAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}