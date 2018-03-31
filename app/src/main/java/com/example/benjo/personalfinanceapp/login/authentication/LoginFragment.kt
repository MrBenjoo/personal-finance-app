package com.example.benjo.personalfinanceapp.login.authentication


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.personalfinanceapp.R
import kotlinx.android.synthetic.main.credentials.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private var iLogin: ILogin? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_btn.setOnClickListener { checkCredentials() }
        login_tv_signup.setOnClickListener { iLogin!!.onSignup() }
    }

    private fun checkCredentials() {
        val username = credentials_username.text.toString()
        val password = credentials_password.text.toString()
        iLogin!!.onLogin(User(username, password))
    }

    override fun onResume() {
        super.onResume()
        iLogin = (activity as LoginActivity).getCallBack()
    }

    interface ILogin {
        fun onLogin(user: User)
        fun onSignup()
    }

    fun clearTxt() {
        credentials_username.text = null
        credentials_password.text = null
    }
}