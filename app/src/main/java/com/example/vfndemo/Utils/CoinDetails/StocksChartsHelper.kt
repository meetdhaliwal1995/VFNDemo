package com.example.vfndemo.Utils.CoinDetails

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.example.vfndemo.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


object StocksChartsHelper {

    fun configureLineChart(
        chart: LineChart,
        axisLeftFormatter: ValueFormatter? = null
    ) {
        val gridColorTemp = chart.context.getColorById(R.color.color_3a3a3a)
        chart.apply {
            isHighlightPerTapEnabled = true
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBorders(false)
            setNoDataTextColor(Color.LTGRAY)
            isDragEnabled = true
            isDragYEnabled = true
            isDragXEnabled = true
            isHighlightPerDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            isDoubleTapToZoomEnabled = true
            isHighlightPerDragEnabled = true
            isHighlightPerTapEnabled = true
            setDrawMarkers(true)
        }
        chart.legend.apply {
            isEnabled = true
            form = Legend.LegendForm.LINE
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            formLineWidth = 1.5f
            maxSizePercent = 0.5f
            yOffset = 10f
        }
        chart.axisRight.apply {
            gridColor = gridColorTemp
            axisLineColor = gridColorTemp
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }
        chart.axisLeft.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
//            gridColor = gridColorTemp
            setDrawAxisLine(false)
//            textColor = Color.BLACK
//            textSize = 12f
            yOffset = -10f
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
//            valueFormatter = axisLeftFormatter
//                ?: object : ValueFormatter() {
//                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
////                        val index = value.toInt()
////                        axis?.mEntries?.size?.let { size ->
////                            return if (index < size) {
//                        var j = 1f
//                        var k = 0
//                        var floatArray = axis?.mEntries?.map { i -> i * j }?.toFloatArray()
//                        while (!checkAllValueDifferent(floatArray, k)) {
//                            j *= 10f
//                            floatArray = axis?.mEntries?.map { i -> i * j }?.toFloatArray()
//                            k += 1
////                                    break
//                        }
//                        return "$${
//                            BigDecimal(value.toDouble()).setScale(
//                                k,
//                                RoundingMode.CEILING
//                            )
//                        }"
////                            } else {
////                                "test"
////                            }
////                        } ?: run {
////                            return ""
////                        }
//
//                    }
//                }
        }
        chart.xAxis.apply {
            gridColor = gridColorTemp
            setDrawAxisLine(false)
            setDrawLabels(false)
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
            valueFormatter = null
        }
    }

    fun checkAllValueDifferent(mEntries: FloatArray?, kIndex: Int): Boolean {
        var intAllValueDifferent = true
        mEntries?.forEachIndexed { index, fl ->
            if (fl.toInt() == mEntries.getOrNull(index + kIndex)?.toInt()) {
                intAllValueDifferent = false
                return false
            }
        }
        return true
    }

    fun configureLineDataSet(
        set: LineDataSet,
        color: Int,
//        drawable: Drawable?
    ) {
        set.color = color
        set.setDrawValues(false)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.valueTextColor = Color.LTGRAY
        set.setDrawFilled(false)
        set.setDrawIcons(true)
        set.lineWidth = 1.3f
//        if (drawable != null) {
//            set.fillDrawable = drawable
//            set.setDrawFilled(true)
//        }
        set.setDrawCircles(false)
        set.cubicIntensity = 0f
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
    }



    fun configureBarDataSet(
        set: BarDataSet,
        color: Int
    ) {
        set.color = color
        set.setDrawValues(false)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.valueTextColor = Color.LTGRAY
        set.color = Color.LTGRAY
    }

    fun configureBarChart(
        chart: BarChart,
        multiplicationFactor: Long,
        axisLeftFormatter: ValueFormatter? = null
    ) {
        val gridColorTemp = chart.context.getColorById(R.color.color_3a3a3a)
        chart.apply {
            isHighlightPerTapEnabled = true
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBorders(false)
            setNoDataTextColor(Color.LTGRAY)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            isDoubleTapToZoomEnabled = false
            isHighlightPerDragEnabled = true
            isHighlightPerTapEnabled = true
            setDrawMarkers(true)
        }
        chart.legend.apply {
            isEnabled = true
            form = Legend.LegendForm.LINE
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            formLineWidth = 1.5f
            maxSizePercent = 0.5f
            yOffset = 10f
        }
        chart.axisRight.apply {
            gridColor = gridColorTemp
            axisLineColor = gridColorTemp
            setDrawAxisLine(false)
            setDrawGridLines(false)
            setDrawLabels(false)
        }
        chart.axisLeft.apply {
            gridColor = gridColorTemp
            setDrawAxisLine(false)
            setDrawGridLines(false)
            valueFormatter = axisLeftFormatter
                ?: object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return ""
                    }
                }
        }
        chart.xAxis.apply {
            gridColor = gridColorTemp
            setDrawAxisLine(false)
            setDrawLabels(true)
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
            setLabelCount(4, true)
            position = XAxis.XAxisPosition.BOTTOM
            textColor = Color.LTGRAY
            valueFormatter = DateAxisFormatter(multiplicationFactor)
        }
    }
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.DMMMYYYYHHMM(): String = SimpleDateFormat("d MMM yyyy, HH:mm", Locale.ENGLISH).format(this)
fun Date.DMMMYYYY(): String = SimpleDateFormat("d MMM", Locale.ENGLISH).format(this)

class DateAxisFormatter(private val multiplicationFactor: Long) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return (value.toLong()*multiplicationFactor).toDate().DMMMYYYY()
    }
}