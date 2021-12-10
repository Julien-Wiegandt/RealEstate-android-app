package com.example.projetinf717.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.projetinf717.Application
import com.example.projetinf717.BuildConfig.DEBUG
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentMainHomeBinding


class MainHomeFragment : Fragment() {
    private var _binding: FragmentMainHomeBinding? = null

    val homeFragment = HomeFragment()
    val homeMapFragment = HomeMapFragment()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val transaction = parentFragmentManager.beginTransaction()
        switchToList()

        binding.listOrMapSwitch.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                switchToMap()
            }else{
                switchToList()
            }
        }
        return root
    }

    private fun switchToList() {

        val transaction = parentFragmentManager.beginTransaction()
        Application.homeListOrMap = false
        transaction.replace(R.id.mainHomeLayout, homeFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun switchToMap() {

        val transaction = parentFragmentManager.beginTransaction()
        Application.homeListOrMap = true
        transaction.replace(R.id.mainHomeLayout, homeMapFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}