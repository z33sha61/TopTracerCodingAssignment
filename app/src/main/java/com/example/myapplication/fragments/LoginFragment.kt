package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.LoginFragmentBinding
import com.example.myapplication.viewmodel.LoginViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.viewmodel.LoginState


class LoginFragment : Fragment() {

    lateinit var binding: LoginFragmentBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.tvLogin.setOnClickListener {
            loginViewModel.login(binding.etName.text.toString(), binding.etPassword.text.toString())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.loginState.observe(viewLifecycleOwner, {
            when (it) {
                LoginState.Idle -> {
                    //Do nothing
                }
                is LoginState.Success -> {
                    (requireActivity() as MainActivity).loginSuccessful(it.username)
                }
                LoginState.UsernameError -> {
                    showDialog(getString(R.string.username_error))
                }
                LoginState.PasswordError -> {
                    showDialog(getString(R.string.password_error))
                }
            }
        })
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(context)
            .setTitle("Oops!")
            .setMessage(message)
            .setNegativeButton(android.R.string.ok, null)
            .show()
    }
}