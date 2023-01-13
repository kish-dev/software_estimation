package com.example.softwareestimation.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.softwareestimation.R
import com.example.softwareestimation.fill_project_form_feature.FillFormFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToFillFormFragment()
    }

    private fun navigateToFillFormFragment() {
        val fillFormFragment = FillFormFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                fillFormFragment as Fragment,
                FillFormFragment::class.java.simpleName
            )
            .addToBackStack(
                FillFormFragment::class.java.simpleName
            ).commit()
    }
}