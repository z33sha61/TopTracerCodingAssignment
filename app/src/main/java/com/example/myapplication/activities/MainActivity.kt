package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.fragments.LoginFragment
import com.example.myapplication.fragments.WelcomeFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null)
            addFragment(R.id.fcView, LoginFragment(), "LoginFragment", "Login")

    }

    fun loginSuccessful(username: String) {
        addFragment(R.id.fcView, WelcomeFragment.newInstance(username), "WelcomeFragment", "Main")
    }

    fun logoutSuccessful() {
        addFragment(R.id.fcView, LoginFragment(), "LoginFragment", "Login")
    }

    override fun onBackPressed() {
        finish()
    }

    private fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        @Nullable backStackStateName: String?
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }

}