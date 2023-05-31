package com.example.softwareestimation.time_diagram_feature

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


object FileShareUtils {
    /**
     * Access file from 'Application Directory'
     *
     * @param context - application context
     * @param fileName - name of file inside application directory to be shared
     * @return uri - returns URI of file.
     */
    fun accessFile(context: Context, fileName: String?): Uri? {
        val file = File(context.getExternalFilesDir(null), fileName)
        return if (file.exists()) {
            FileProvider.getUriForFile(
                context,
                "com.example.softwareestimation.fileprovider", file
            )
        } else {
            null
        }
    }
}