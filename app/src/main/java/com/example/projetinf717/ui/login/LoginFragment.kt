package com.example.projetinf717.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentLoginBinding
import com.example.projetinf717.ui.home.HomeViewModel
import androidx.annotation.NonNull
import com.example.projetinf717.AppActivity
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences





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
                val jwt = loginViewModel.jwt
                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putString("jwt", jwt.toString());
                val appActivityIntent = Intent(activity, AppActivity::class.java)
                startActivity(appActivityIntent)
            }
            Action.SHOW_INVALID_PASSWARD_OR_LOGIN -> {
                Toast.makeText(context,"Bad email/password, try again", Toast.LENGTH_SHORT).show();
            }
        }
    }




}