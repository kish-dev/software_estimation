package com.example.softwareestimation

import android.app.Application
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.SoftwareEstimationDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var softwareEstimationDatabase: SoftwareEstimationDatabase

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            ProjectPercentSpreadForTypes.typeInformation.forEach {
                softwareEstimationDatabase.projectPercentSpreadDao().addProjectPercentSpread(it)
            }
        }
    }

}