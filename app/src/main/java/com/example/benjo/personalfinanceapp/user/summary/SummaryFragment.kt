package com.example.benjo.personalfinanceapp.user.summary

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.login.authentication.Constants
import kotlinx.android.synthetic.main.fragment_summary.*


class SummaryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summary_tv.text = Constants.welcomeMsg(activity.intent.getStringExtra(Constants.USER))
        ChartHandler(summary_chart, ChartData(100f, 200f))
    }

    override fun onResume() {
        super.onResume()
    }
}