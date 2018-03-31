package com.example.benjo.personalfinanceapp.user.summary

data class ChartData(val revenue: Float, val expenditure: Float) {
    val balance get() = revenue-expenditure
}