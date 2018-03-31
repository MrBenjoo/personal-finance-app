package com.example.benjo.personalfinanceapp

import com.example.benjo.personalfinanceapp.login.authentication.LoginActivity
import com.example.benjo.personalfinanceapp.login.authentication.AuthPresenter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *
 * Each test should be created from the following blocks:
 * - Arrange/Given - in which we will prepare all needed data required to perform test
 * - Act/When - in which we will call single method on tested object
 * - Assert/Then - in which we will check result of the test, either pass or fail
 *
 * ctrl + shift + f10 to run the test
 *
 */
class ExampleUnitTest {

    @Mock
    val loginActivity : LoginActivity = LoginActivity()

    var presenter: AuthPresenter? = null

    @Before
    fun createLogHistory() {
        presenter = AuthPresenter(loginActivity)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
