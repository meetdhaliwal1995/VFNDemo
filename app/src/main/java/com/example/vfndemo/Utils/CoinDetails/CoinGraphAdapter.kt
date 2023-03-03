package com.example.vfndemo.Utils.CoinDetails

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.vfndemo.R
import com.example.vfndemo.Utils.CoinDetails.Model.CoinInfoModel
import com.example.vfndemo.Utils.CoinDetails.StocksChartsHelper.configureBarDataSet
import com.example.vfndemo.Utils.CoinDetails.StocksChartsHelper.configureLineDataSet
import com.example.vfndemo.databinding.FragmentCoinGraphBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


class CoinGraphAdapter : BaseRecyclerViewAdapter<GraphModel?>() {

    //    lateinit var profileId: String
    lateinit var interval: String
//    lateinit var coinChangeModel: ResponseDataWrapper<CoinInfoModel>
//    lateinit var clickHandler: GraphInterface

    override fun doCreateViewHolder(view: View): BaseViewHolder<GraphModel?> {
        var obj = HoldingViewHolder(view, interval)
//        obj.profileId = profileId
//        obj.clickHandler = clickHandler
        return obj
    }

    fun setIntervalPeriod(interval: String) {
        this.interval = interval
    }

    fun setChangeModel(coinChangeModel: ResponseDataWrapper<CoinInfoModel>) {
//        this.coinChangeModel = coinChangeModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_coin_graph
    }

    class HoldingViewHolder(
        itemView: View,
        private val interval: String = "1",
        private val coinChangeModel: ResponseDataWrapper<CoinInfoModel>? = null
    ) :
        BaseViewHolder<GraphModel?>(itemView),
        View.OnClickListener {
        val binding = FragmentCoinGraphBinding.bind(itemView)
        private var multiplicationFactor = 1L

        var profileId: String? = null
        var clickHandler: GraphInterface? = null

        @SuppressLint("SetTextI18n")
        override fun bind(item: GraphModel?, position: Int) {

            StocksChartsHelper.configureLineChart(binding.lineChart)
            binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                var intervalPeriod = binding.radioGroup.context.getString(R.string.interval_1)
                when (checkedId) {
                    R.id.oneDay -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_1)
                    }
                    R.id.oneWeek -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_7)
                    }
                    R.id.oneMonth -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_30)
                    }
                    R.id.threeMonth -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_90)
                    }
                    R.id.oneYear -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_365)
                    }
                    R.id.max -> {
                        intervalPeriod = binding.radioGroup.context.getString(R.string.interval_max)
                    }
                }
                clickHandler?.changeInterval(intervalPeriod)
            }

            val linkDrawable2 = binding.viewSemiCircular2.background.mutate()
            val linkDrawable = binding.viewSemiCircular1.background.mutate()

//            if (linkDrawable is GradientDrawable) {
//                linkDrawable.setStroke(
//                    4,
//                    ContextCompat.getColor(
//                        binding.radioGroup.context,
//                        R.color.color_E9E9EE
//                    )
//                )
//                linkDrawable.setColor(
//                    ContextCompat.getColor(
//                        binding.radioGroup.context,
//                        R.color.color_E7ECF5
//                    )
//                )
//                linkDrawable.cornerRadii = floatArrayOf(16f, 16f, 16f, 16f, 0f, 0f, 0f, 0f)
//            }
//
//            if (linkDrawable2 is GradientDrawable) {
//                linkDrawable2.setStroke(
//                    4,
//                    ContextCompat.getColor(
//                        binding.radioGroup.context,
//                        R.color.color_E9E9EE
//                    )
//                )
//                linkDrawable2.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 16f, 16f, 16f, 16f)
//            }
            val map: HashMap<Long, Long> = HashMap()
            binding.barChart.clear()
            binding.lineChart.clear()
            binding.lineChart.animateX(1000)
            binding.lineChart.invalidate()
            binding.lineChart.fitScreen()

            val minValue = item?.totalVolumes?.getOrNull(0)?.getOrNull(0)?.toLong() ?: 0
            val maxValue =
                item?.totalVolumes?.getOrNull(item.totalVolumes.size - 1)?.getOrNull(0)?.toLong()
                    ?: 0
            multiplicationFactor = ((maxValue - minValue) / 120).toLong()

            val lineData = LineData()

            val listItem = item?.prices?.toMutableList()
            listItem?.add(listOf(1667559654375.toBigDecimal(), 21234.42098438317.toBigDecimal()))
            listItem?.sortedWith(compareBy { it[0] })

            val lineDataSet = LineDataSet(
                listItem?.mapIndexed { index, item ->
                    if (multiplicationFactor == 0L) multiplicationFactor = 1
                    Log.e("LOOOPPPP", item.toString())
                    if (item[0].toLong() == "1668117693296".toLong()) {

                        Entry(
                            (item[0].toLong() / multiplicationFactor).toFloat(),
                            item[1].toFloat(),
                            ContextCompat.getDrawable(
                                binding.lineChart.context,
                                R.drawable.ic_coin_profile
                            ),
                            index
                        )
                    } else {
                        Entry(
                            (item[0].toLong() / multiplicationFactor).toFloat(),
                            item[1].toFloat(),
                            index
                        )
                    }
                },
                ""
            )
//            val tf = ResourcesCompat.getFont(binding.lineChart.context, R.font.customfont)
//            lineDataSet.valueTypeface = tf
//            binding.lineChart.axisLeft.axisMinimum = 0f
//            binding.lineChart.axisRight.axisMinimum = 0f

//            binding.lineChart.scrollY = 10
//            binding.lineChart.moveViewToX(10f)
//            binding.lineChart.xAxis?.spaceMin = 2.5f // As per you requiedment
//            binding.lineChart.xAxis?.spaceMax = 0.1f // As per you requiedment
            lineDataSet.setDrawHorizontalHighlightIndicator(false)
            lineDataSet.highLightColor =
                binding.lineChart.context.getColorById(R.color.color_000000)
            lineDataSet.highlightLineWidth = 1.9f
            configureLineDataSet(
                lineDataSet,
                binding.lineChart.context.getColorById(R.color.color_734b6d)
            )
            lineData.addDataSet(lineDataSet)
            binding.lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)
            binding.lineChart.isScaleXEnabled
            binding.lineChart.fitScreen()
            binding.lineChart.isScaleYEnabled = false
            binding.lineChart.legend.isEnabled = false
            binding.lineChart.data = lineData
            binding.lineChart.highlightValue(null)

            StocksChartsHelper.configureBarChart(binding.barChart, multiplicationFactor)
            val barData = BarData()
            val barDataSet = BarDataSet(
                item?.totalVolumes?.mapIndexed { index, item ->
                    BarEntry(
                        (item[0].toLong() / multiplicationFactor).toFloat(),
                        item[1].toFloat(),
                        index
                    )
                }, ""
            )

            barDataSet.isHighlightEnabled = true
            binding.title.text = "Bitcoin"
//            binding.coinPrice.text = item?.prices?.get(0) .toString()
            item?.prices?.get(0)?.let { Log.e("PRICEEEE", it.toString()) }
            barDataSet.highLightColor = binding.lineChart.context.getColorById(R.color.color_000000)
            configureBarDataSet(
                barDataSet,
                binding.lineChart.context.getColorById(R.color.color_000000)
            )
            barData.addDataSet(barDataSet)
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawHighlightIndicators(false)
            lineDataSet.setDrawIcons(true)

            binding.barChart.legend.isEnabled = false
            binding.barChart.data = barData
            binding.barChart.highlightValue(null)
            binding.lineChart.marker =
                StocksMarkerView(
                    chart = binding.lineChart,
                    lineDataSet2 = barDataSet,
                    multiplicationFactor = multiplicationFactor
                )

            binding.lineChart.setOnChartValueSelectedListener(object :
                OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    if (e?.x !=  ("1668117693296".toLong() / multiplicationFactor).toFloat()) {
                        binding.lineChart.highlightValue(null)
                    }
                }

                override fun onNothingSelected() {
//                    binding.barChart.highlightValue(null)
                }
            })
            binding.barChart.setOnChartValueSelectedListener(object :
                OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
//                    binding.lineChart.highlightValue(h)
                }

                override fun onNothingSelected() {
//                    binding.lineChart.highlightValue(null)
                }
            })

            with(binding.root) {
                binding.oneDayChange.setTextColorRedGreen(
                    coinChangeModel?.data?.priceChangePercentage24H ?: BigDecimal(0),
                    binding.oneDayChange.context
                )
                binding.oneWeekChange.setTextColorRedGreen(
                    coinChangeModel?.data?.priceChangePercentage7D ?: BigDecimal(0),
                    binding.oneWeekChange.context
                )
                binding.oneMonthChange.setTextColorRedGreen(
                    coinChangeModel?.data?.priceChangePercentage30D ?: BigDecimal(0),
                    binding.oneMonthChange.context
                )
                binding.oneYearChange.setTextColorRedGreen(
                    coinChangeModel?.data?.priceChangePercentage1Y ?: BigDecimal(0),
                    binding.oneYearChange.context
                )
                binding.maxChange.setTextColorRedGreen(
                    coinChangeModel?.data?.allTimeLowChangePercentage ?: BigDecimal(0),
                    binding.maxChange.context
                )

                binding.oneDayChange.text =
                    formatDecimalString(coinChangeModel?.data?.priceChangePercentage24H)
                binding.oneWeekChange.text =
                    formatDecimalString(coinChangeModel?.data?.priceChangePercentage7D)
                binding.oneMonthChange.text =
                    formatDecimalString(coinChangeModel?.data?.priceChangePercentage30D)
                binding.oneYearChange.text =
                    formatDecimalString(coinChangeModel?.data?.priceChangePercentage1Y)
                binding.maxChange.text =
                    formatDecimalString(coinChangeModel?.data?.allTimeLowChangePercentage)
                var maxLength = maxOf(
                    binding.oneDayChange.text.toString().length,
                    binding.oneWeekChange.text.toString().length,
                    binding.oneMonthChange.text.toString().length,
                    binding.oneYearChange.text.toString().length,
                    binding.maxChange.text.toString().length
                )

                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.mainContainer)
                constraintSet.connect(
                    R.id.viewSemiCircular2,
                    ConstraintSet.RIGHT,
                    R.id.viewSemiCircular1,
                    ConstraintSet.RIGHT,
                    0
                )
                constraintSet.connect(
                    R.id.viewSemiCircular2,
                    ConstraintSet.TOP,
                    R.id.viewSemiCircular1,
                    ConstraintSet.BOTTOM,
                    0
                )

                constraintSet.connect(
                    R.id.viewSemiCircular2,
                    ConstraintSet.START,
                    R.id.viewSemiCircular1,
                    ConstraintSet.START,
                    0
                )

                constraintSet.connect(
                    R.id.viewSemiCircular2,
                    ConstraintSet.BOTTOM,
                    when (maxLength) {
                        binding.oneDayChange.text.length -> R.id.oneDayChange
                        binding.oneWeekChange.text.length -> R.id.oneWeekChange
                        binding.oneMonthChange.text.length -> R.id.oneMonthChange
                        binding.oneYearChange.text.length -> R.id.oneYearChange
                        else -> R.id.maxChange
                    },
                    ConstraintSet.BOTTOM,
                    0
                )
                constraintSet.applyTo(binding.mainContainer)
            }
        }

        private fun formatDecimalString(value: BigDecimal?): String {
            return if (value.toString().indexOf(".") == 1 && value.toString()[0] != '-')
                String.format("%.2f", value ?: 0.0) + "%"
            else if (value.toString().indexOf(".") == 2 && value.toString()[0] == '-')
                String.format("%.2f", value ?: 0.0) + "%"
            else String.format("%.0f", value ?: 0.0) + "%"
        }

        override fun onClick(view: View) {
            //No code required
        }

        private fun setData(count: Int, range: Float) {
            val values = ArrayList<Entry>()
            for (i in 0 until count) {
                val `val` = (Math.random() * range).toFloat() - 30
                values.add(
                    Entry(
                        i.toFloat(),
                        `val`,
                        ContextCompat.getDrawable(
                            binding.lineChart.context,
                            com.google.androidgamesdk.R.drawable.notification_bg
                        )
                    )
                )
            }
        }

    }
}

fun Context.getColorById(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun TextView.setTextColorRedGreen(value: BigDecimal, context: Context) {
    if (value < BigDecimal(0)) this.setTextColor(context.getColorById(R.color.color_F14C46))
    else this.setTextColor(context.getColorById(R.color.color_01ba61))
}

interface GraphInterface {
    fun changeInterval(interval: String)
}