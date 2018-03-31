package com.example.benjo.personalfinanceapp.login.authentication


import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import com.example.benjo.personalfinanceapp.utils.ContainerFragment


class AuthPresenter(activity: LoginActivity, fragments: HashMap<String, Fragment>) : LoginFragment.ILogin, RegisterFragment.IRegister {
    private var container: ContainerFragment? = null
    private var loginActivity: LoginActivity? = null
    private var sharedPref: SharedPreferences? = null
    private var loginFragment: LoginFragment? = null
    private var registerFragment: RegisterFragment? = null

    init {
        loginActivity = activity
        container = fragments.get(Constants.CONTAINER) as ContainerFragment
        loginFragment = fragments.get(Constants.LOGIN) as LoginFragment
        registerFragment = fragments.get(Constants.REGISTER) as RegisterFragment
        sharedPref = activity.getSharedPreferences(Constants.USER, Context.MODE_PRIVATE)
    }

    override fun onLogin(user: User) {
        val validUser = checkCredentials(user)
        if (validUser) {
            loginFragment!!.clearTxt()
            registerFragment!!.clearTxt();
            loginActivity!!.startUserActivity(user)
        } else {
            showText(Constants.LOGIN_UNSUCCESSFUL)
        }
    }

    override fun onRegister(user: User) {
        if (credentialsOK(user)) {
            if (isRegistered(user)) {
                showText(Constants.ALREADY_REGISTERED)
            } else {
                if (register(user)) {
                    container!!.show(Constants.LOGIN)
                    showText(Constants.REGISTER_SUCCESS)
                }
            }
        } else {
            showText(Constants.REGISTER_UNSUCCESSFUL)
        }
    }

    override fun onSignup() {
        container!!.show(Constants.REGISTER)
    }

    private fun checkCredentials(user: User): Boolean {
        return if (credentialsOK(user)) isRegistered(user) else false
    }

    private fun credentialsOK(user: User): Boolean {
        return (user.username.length > 2 && user.password.length > 2)
    }

    private fun isRegistered(user: User): Boolean {
        val sUsername = sharedPref!!.getString(user.username, "")
        val sPassword = sharedPref!!.getString(user.password, "")
        return (sUsername == user.username && sPassword == user.password)
    }

    private fun register(user: User): Boolean {
        val editor = sharedPref!!.edit()
        editor.putString(user.username, user.username)
        editor.putString(user.password, user.password)
        editor.apply()
        return true
    }

    override fun onBackPressed() {
        container!!.show(Constants.LOGIN)
    }

    private fun showText(text: String) {
        loginActivity!!.showText(text)
    }

    fun getCurrentTag(): String? {
        return container!!.currentTag
    }

    fun show(tag: String) {
        container!!.show(tag)
    }

    fun clearRegTxt() {
        registerFragment!!.clearTxt()
    }

}