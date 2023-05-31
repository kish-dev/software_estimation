package com.example.softwareestimation.time_diagram_feature

import android.content.Context
import android.os.Environment
import android.text.style.DynamicDrawableSpan.ALIGN_CENTER
import android.util.Log
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object ExcelUtils {
    const val TAG = "ExcelUtil"
    private var cell: Cell? = null
    private var sheet: Sheet? = null
    private var workbook: Workbook? = null
    private var headerCellStyle: CellStyle? = null
    const val ONE_WEEK = 86_400_000 * 7

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
    ): Boolean {
        val isWorkbookWrittenIntoStorage: Boolean

        // Check if available and not read only
        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            Log.e(TAG, "Storage not available or read only")
            return false
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = HSSFWorkbook()
        setHeaderCellStyle()

        // Creating a New Sheet and Setting width for each column
        sheet = workbook!!.createSheet(diagramDto.projectName)
        sheet!!.setColumnWidth(0, 15 * 400)
        sheet!!.setColumnWidth(1, 15 * 400)
        setHeaderRow()
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
        headerCellStyle = workbook!!.createCellStyle()
        headerCellStyle!!.setFillForegroundColor(HSSFColor.AQUA.index)
//        headerCellStyle!!.setFillPattern(HSSFCellStyle)
//        headerCellStyle!!.setAlignment(CellStyle.ALIGN_CENTER)
    }

    /**
     * Setup Header Row
     */
    private fun setHeaderRow() {
        val headerRow: Row = sheet!!.createRow(0)
        cell = headerRow.createCell(0)
        cell!!.setCellValue("Name")
        cell!!.cellStyle = headerCellStyle
        cell = headerRow.createCell(1)
        cell!!.setCellValue("Phone Number")
        cell!!.cellStyle = headerCellStyle
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

            for (j in diagramDto.rows[i].cells.indices) {

                // Create Cells for each row
                cell = rowData.createCell(j)
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
    private fun storeExcelInStorage(context: Context, fileName: String): Boolean {
        var isSuccess: Boolean
        val file = File(context.getExternalFilesDir(null), fileName)
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
                if (null != fileOutputStream) {
                    fileOutputStream.close()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return isSuccess
    }
}