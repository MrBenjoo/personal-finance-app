package com.example.benjo.personalfinanceapp.utils


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.personalfinanceapp.R


class ContainerFragment : Fragment() {
    private var viewer: FragmentViewer? = null

    var currentTag: String?
        get() = viewer!!.currentTag
        set(tag) {
            viewer!!.currentTag = tag
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        val fm = childFragmentManager
        viewer = FragmentViewer(fm, R.id.fragment_container)
        if (savedInstanceState != null) {
            viewer!!.currentTag = savedInstanceState.getString("currentTag")
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        viewer!!.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("currentTag", viewer!!.currentTag)
        super.onSaveInstanceState(outState)
    }

    fun add(fragment: android.app.Fragment, tag: String) {
        viewer!!.add(fragment, tag)
    }

    fun show(): String? {
        return viewer!!.show()
    }

    fun setAnimation(var1: Int, var2: Int) {
        viewer!!.setAnimation(var1, var2)
    }

    fun show(tag: String): String? {
        return viewer!!.show(tag)
    }

}
