package com.example.projetinf717.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentLoginBinding
import com.example.projetinf717.databinding.FragmentRegisterBinding
import com.example.projetinf717.ui.login.LoginViewModel

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}