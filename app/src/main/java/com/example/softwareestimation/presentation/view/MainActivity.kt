package com.example.softwareestimation.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.softwareestimation.R
import com.example.softwareestimation.all_projects_feature.AllProjectsFragment
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
        val allProjectsFragment = AllProjectsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                allProjectsFragment as Fragment,
                AllProjectsFragment::class.java.simpleName
            )
            .addToBackStack(
                AllProjectsFragment::class.java.simpleName
            ).commit()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            1 -> {
                finish()
            }

            else -> {
                super.onBackPressed()
            }
        }
    }
}