package com.example.vfndemo.Utils.CoinDetails

import android.annotation.SuppressLint
import android.widget.TextView
import com.example.vfndemo.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class StocksMarkerView(
    chart: LineChart,
    private val lineDataSet2: BarDataSet,
    private val multiplicationFactor: Long,
) :
    MarkerView(chart.context, R.layout.layout_stocks_marker), IMarker {

    init {
        chartView = chart
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null) {
            try {
                val markerDate: TextView =
                    rootView.findViewById<TextView>(R.id.markerDate) as TextView
                val markerValue1: TextView =
                    rootView.findViewById<TextView>(R.id.markerValue1) as TextView
                val markerValue2: TextView =
                    rootView.findViewById<TextView>(R.id.markerValue2) as TextView
                markerDate.text = (lineDataSet2.getEntriesForXValue(e.x).getOrNull(0)?.x?.toLong()
                    ?.times(multiplicationFactor))?.toDate()?.DMMMYYYYHHMM()
                markerValue1.text =
                    "Price- $" + BigDecimal.valueOf(e.y.toDouble()).formatBigDecimal()
                markerValue2.text =
                    "Vol-     " + lineDataSet2.getEntriesForXValue(e.x).getOrNull(0)?.y?.toLong()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

    fun BigDecimal.formatBigDecimal(): String {
        // When number is greater than 1
        if (this.compareTo(1.toBigDecimal()) == 1) {
            return this.setScale(2, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString()
        }
        // when number is smaller than 0.000001
        if (this.compareTo(0.001.toBigDecimal()) < 1) {
            var numberFormatter = DecimalFormat("0.0E0")
            numberFormatter.roundingMode = RoundingMode.CEILING
            numberFormatter.maximumFractionDigits = 2
            return numberFormatter.format(this.stripTrailingZeros())
        }
        //when number is between 0.000001 and 1
        return (this.setScale(5, RoundingMode.HALF_DOWN).stripTrailingZeros() ?: 0).toString()
    }

    private fun Long.toDate(): Date {
        return Date(this)
    }

    private fun Date.DMMMYYYYHHMM(): String = SimpleDateFormat("d MMM yyyy, HH:mm", Locale.ENGLISH).format(this)
    fun Date.DMMMYYYY(): String = SimpleDateFormat("d MMM", Locale.ENGLISH).format(this)
}