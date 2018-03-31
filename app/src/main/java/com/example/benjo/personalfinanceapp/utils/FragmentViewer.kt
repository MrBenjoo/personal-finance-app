package com.example.benjo.personalfinanceapp.utils


import android.app.FragmentManager



class FragmentViewer(private val fm: FragmentManager, private val container: Int) {
    var currentTag: String? = null
    private var var1: Int? = null
    private var var2: Int? = null

    fun add(fragment: android.app.Fragment, tag: String) {
        val ft = fm.beginTransaction()
        if (!fragment.isAdded)
            ft.add(container, fragment, tag)
        ft.hide(fragment)
        ft.commit()
    }

    fun show(tag: String? = currentTag): String? {
        val fragment = fm.findFragmentByTag(tag)
        val currentFragment = fm.findFragmentByTag(currentTag)
        fragment.let {
            val ft = fm.beginTransaction()
            if (!tag.equals(currentTag))
                ft.setCustomAnimations(var1!!, var2!!)
            if (currentFragment != null)
                ft.hide(currentFragment)
            ft.show(it)
            ft.commit()
            currentTag = tag
        }
        return currentTag
    }

    fun setAnimation(int1: Int, int2: Int) {
        var1 = int1
        var2 = int2
    }

}
