package com.example.projetinf717.ui.login

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.AppActivity
import com.example.projetinf717.Application
import com.example.projetinf717.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvTitle
        val loginButton: Button = binding.btnLogin

        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        loginViewModel.getAction()?.observe(viewLifecycleOwner,
            Observer<Action?> { action -> action?.let { handleAction(it) } })

        loginButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginViewModel.userWantToLogin(password, email);
        }

        return root
    }
    private fun handleAction(action: Action) {
        when (action.value) {
            Action.SHOW_WELCOME -> {
                val jwt = Application.JWT
                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putString("jwt", jwt.toString())
                myEdit.apply()
                val appActivityIntent = Intent(activity, AppActivity::class.java)
                startActivity(appActivityIntent)
                activity?.finish()
            }
            Action.SHOW_INVALID_PASSWARD_OR_LOGIN -> {
                Toast.makeText(context,"Bad email/password, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }




}