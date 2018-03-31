package com.example.benjo.personalfinanceapp.user.summary


import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class ChartHandler(pieChart: PieChart, data: ChartData) {
    private var pieChart: PieChart? = null
    private var chartData: ChartData? = null
    private var dataSet: PieDataSet? = null
    private var pieData: PieData? = null

    init {
        this.pieChart = pieChart
        this.chartData = data
        initDataSet()
        initPieData()
        initPieChart()
    }

    private fun initDataSet() {
        val values = ArrayList<PieEntry>()
        chartData?.let {
            values.add(PieEntry(it.revenue, "Revenue"))
            values.add(PieEntry(it.expenditure, "Expenditure"))
        }
        dataSet = PieDataSet(values, "")
        with(dataSet!!) {
            sliceSpace = 3f
            selectionShift = 5f
            colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
        }
    }

    private fun initPieData() {
        pieData = PieData(dataSet)
        pieData?.setValueTextSize(10f)
    }

    private fun initPieChart() {
        with(pieChart!!) {
            data = pieData
            description.isEnabled = false
            setCenterTextColor(Color.WHITE)
            setCenterTextSize(18f)
            centerText = chartData!!.balance.toString()
            legend?.isEnabled = false
            setHoleColor(Color.TRANSPARENT)
            animateY(1000, Easing.EasingOption.EaseInQuad)
        }
    }

}