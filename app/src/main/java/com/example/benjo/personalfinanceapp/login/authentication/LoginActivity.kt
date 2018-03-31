package com.example.benjo.personalfinanceapp.login.authentication

import android.app.Fragment
import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.benjo.personalfinanceapp.R
import com.example.benjo.personalfinanceapp.user.activity.UserActivity

import com.example.benjo.personalfinanceapp.utils.ContainerFragment
import java.util.HashMap

class LoginActivity : AppCompatActivity() {
    private var presenter: AuthPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = AuthPresenter(this, fragments(savedInstanceState))
    }

    private fun fragments(savedInstanceState: Bundle?): HashMap<String, Fragment> {
        val container = fragmentManager.findFragmentById(R.id.login_container_fragment) as ContainerFragment?
        container!!.setAnimation(R.animator.slide_in_left, R.animator.slide_out_left)
        val fragmentManager = container.childFragmentManager
        val fragments = if (savedInstanceState == null) createFragments(container) else fragmentsFromFM(fragmentManager)
        addFragmentsToContainer(container, fragments)
        fragments!!.put(Constants.CONTAINER, container)
        return fragments
    }

    private fun fragmentsFromFM(fm: FragmentManager?): HashMap<String, Fragment>? {
        val fragments = HashMap<String, Fragment>()
        with(fragments) {
            with(Constants) {
                put(LOGIN, fm!!.findFragmentByTag(LOGIN) as LoginFragment)
                put(REGISTER, fm.findFragmentByTag(REGISTER) as RegisterFragment)
            }
            return this
        }
    }

    private fun addFragmentsToContainer(container: ContainerFragment, fragments: HashMap<String, Fragment>?) {
        with(container) {
            with(fragments!!) {
                with(Constants) {
                    add(get(LOGIN)!!, LOGIN)
                    add(get(REGISTER)!!, REGISTER)
                }
            }
        }
    }

    private fun createFragments(container: ContainerFragment): HashMap<String, Fragment>? {
        val fragments = HashMap<String, Fragment>()
        with(fragments) {
            put(Constants.LOGIN, LoginFragment())
            put(Constants.REGISTER, RegisterFragment())
            container.currentTag = Constants.LOGIN
            return this
        }
    }

    fun showText(text: String) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        when (presenter!!.getCurrentTag()) {
            Constants.REGISTER ->
            {
                presenter!!.show(Constants.LOGIN)
                presenter!!.clearRegTxt()
            }
            Constants.LOGIN -> super.onBackPressed()
        }
    }

    fun getCallBack(): AuthPresenter? {
        return presenter
    }

    fun startUserActivity(user: User) {
        val intent = UserActivity.newIntent(this, user)
        startActivity(intent)
    }
}
