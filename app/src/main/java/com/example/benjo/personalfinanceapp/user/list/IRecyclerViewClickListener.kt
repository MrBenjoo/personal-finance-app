package com.example.benjo.personalfinanceapp.user.list

import android.view.View

interface IRecyclerViewClickListener {
    fun onLongClick(view: View, position: Int)
}