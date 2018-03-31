package com.example.benjo.personalfinanceapp.login.authentication


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benjo.personalfinanceapp.R
import kotlinx.android.synthetic.main.credentials.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    private var iRegister: IRegister? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_btn.setOnClickListener { onRegister() }
        register_tv_bottom.setOnClickListener { onBackPressed() }
    }

    private fun onRegister() {
        val username = credentials_username.text.toString()
        val password = credentials_password.text.toString()
        clearTxt()
        iRegister!!.onRegister(User(username, password))
    }

    private fun onBackPressed() {
        clearTxt()
        iRegister!!.onBackPressed()
    }

    fun clearTxt() {
        credentials_username.text = null
        credentials_password.text = null
    }

    override fun onResume() {
        super.onResume()
        iRegister = (activity as LoginActivity).getCallBack()
    }

    interface IRegister {
        fun onRegister(user: User)
        fun onBackPressed()
    }


}
