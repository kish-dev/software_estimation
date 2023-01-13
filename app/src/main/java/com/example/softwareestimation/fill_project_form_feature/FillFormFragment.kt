package com.example.softwareestimation.fill_project_form_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.softwareestimation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillFormFragment : Fragment() {

    private val viewModel: FillFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fill_form, container, false)
    }
}