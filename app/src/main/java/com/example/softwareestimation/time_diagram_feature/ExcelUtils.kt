package com.example.softwareestimation.time_diagram_feature

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.data.db.employees.EmployeesLevels
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectFragment
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


object ExcelUtils {
    const val TAG = "ExcelUtil"
    private var cell: Cell? = null
    private var sheet: Sheet? = null
    private var workbook: XSSFWorkbook? = null
    private var headerCellStyle: CellStyle? = null
    private const val ONE_DAY = 86_400_000
    private const val ONE_WEEK = ONE_DAY * 7

    /**
     * Export Data into Excel Workbook
     *
     * @param context  - Pass the application context
     * @param fileName - Pass the desired fileName for the output excel Workbook
     * @param dataList - Contains the actual data to be displayed in excel
     */
    fun exportDataIntoWorkbook(
        context: Context,
        fileName: String,
        diagramDto: TimeDiagramDto,
    ): Uri? {
        val isWorkbookWrittenIntoStorage: Uri?

        // Check if available and not read only
        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            Log.e(TAG, "Storage not available or read only")
            return null
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = XSSFWorkbook()
        setHeaderCellStyle()

        // Creating a New Sheet and Setting width for each column
        sheet = workbook!!.createSheet(diagramDto.projectName)
        sheet!!.setColumnWidth(0, 15 * 400)
        sheet!!.setColumnWidth(1, 15 * 400)
        fillDataIntoExcel(diagramDto)
        isWorkbookWrittenIntoStorage = storeExcelInStorage(context, fileName)
        return isWorkbookWrittenIntoStorage
    }

    /**
     * Checks if Storage is READ-ONLY
     *
     * @return boolean
     */
    private val isExternalStorageReadOnly: Boolean
        private get() {
            val externalStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED_READ_ONLY == externalStorageState
        }

    /**
     * Checks if Storage is Available
     *
     * @return boolean
     */
    private val isExternalStorageAvailable: Boolean
        private get() {
            val externalStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == externalStorageState
        }

    /**
     * Setup header cell style
     */
    private fun setHeaderCellStyle() {
//        headerCellStyle = workbook!!.createCellStyle()
//        headerCellStyle!!.setFillForegroundColor(HSSFColor.AQUA.index)
//        headerCellStyle!!.setFillPattern(HSSFCellStyle)
//        headerCellStyle!!.setAlignment(CellStyle.ALIGN_CENTER)
    }

    /**
     * Fills Data into Excel Sheet
     *
     *
     * NOTE: Set row index as i+1 since 0th index belongs to header row
     *
     * @param dataList - List containing data to be filled into excel
     */
    private fun fillDataIntoExcel(diagramDto: TimeDiagramDto) {

        val rowForWeeks: Row = sheet!!.createRow(1)
        cell = rowForWeeks.createCell(0)
        cell!!.setCellValue("")

        var startDate = diagramDto.startWeek
        val lastDate = diagramDto.lastWeek
        var cellIndex = 1
        val datePattern = "dd.MM.yy"
        val simpleDateFormatter = SimpleDateFormat(datePattern)

        while (startDate < lastDate) {
            cell = rowForWeeks.createCell(cellIndex)
            cell!!.setCellValue(simpleDateFormatter.format(startDate))
            ++cellIndex
            startDate = Date(startDate.time + ONE_WEEK)
        }

        for (i in diagramDto.rows.indices) {
            // Create a New Row for every new entry in list
            val rowData: Row = sheet!!.createRow(i + 2)

            cell = rowData.createCell(0)
            cell!!.setCellValue(diagramDto.rows[i].firstCellString)

            for (j in diagramDto.rows[i].cells.indices) {

                // Create Cells for each row
                cell = rowData.createCell(j + 1)
                cell!!.setCellValue(diagramDto.rows[i].cells[j].text)
            }
        }
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param fileName - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    private fun storeExcelInStorage(context: Context, fileName: String): Uri? {
        val excelFileName = "${fileName}.xlsx"
        var isSuccess: Boolean
        var file = File(context.getExternalFilesDir(null), excelFileName)
        if (file.exists()) {
            file.delete()
        }
        file = File(context.getExternalFilesDir(null), excelFileName)
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            workbook!!.write(fileOutputStream)
            Log.e(TAG, "Writing file$file")
            isSuccess = true
        } catch (e: IOException) {
            Log.e(TAG, "Error writing Exception: ", e)
            isSuccess = false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save file due to Exception: ", e)
            isSuccess = false
        } finally {
            try {
                fileOutputStream?.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName.toString() + ".provider",
            file
        )
    }


    @SuppressLint("SimpleDateFormat")
    fun getNextMonday(): Date {
        val localDate = LocalDate.now().dayOfWeek.name

        val plusToDate = when (localDate) {
            "MONDAY" -> 0
            "TUESDAY" -> 1
            "WEDNESDAY" -> 2
            "THURSDAY" -> 3
            "FRIDAY" -> 4
            "SATURDAY" -> 5
            "SUNDAY" -> 6

            else -> 0
        }


        val currentMonday =
            Date(
                System.currentTimeMillis() + (plusToDate * EstimatedProjectFragment.ONE_DAY)
            )

        return currentMonday
    }


    fun getCountEmployeeFreeInWeek(
        monday: Long,
        employees: List<Employee>,
        currentSphere: EmployeeSpheres,
    ): Double {
        val countInDays = getCountEmployeeFreeInDay(monday, employees, currentSphere) +
                getCountEmployeeFreeInDay(monday + ONE_DAY, employees, currentSphere) +
                getCountEmployeeFreeInDay(monday + (2 * ONE_DAY), employees, currentSphere) +
                getCountEmployeeFreeInDay(monday + (3 * ONE_DAY), employees, currentSphere) +
                getCountEmployeeFreeInDay(monday + (4 * ONE_DAY), employees, currentSphere)

        return countInDays / 5
    }

    private fun getMultiplyCounter(level: EmployeesLevels?): Double {
        return when (level) {
            null -> 0.0
            EmployeesLevels.INTERN -> 0.25
            EmployeesLevels.JUNIOR -> 0.5
            EmployeesLevels.MIDDLE -> 1.0
            EmployeesLevels.SENIOR -> 1.5
            EmployeesLevels.LEAD -> 2.0
        }
    }

    private fun getCountEmployeeFreeInDay(
        day: Long,
        employees: List<Employee>,
        currentSphere: EmployeeSpheres,
    ): Double {
        var count = 0.0
        employees.forEach { employee ->
            if (!employee.hasBusies(day, day + ONE_DAY - 1)) {
                count += 1 * getMultiplyCounter(employee.specializations.first {
                    it.sphere == currentSphere
                }.level)
            }
        }
        return count
    }
}