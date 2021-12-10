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
    private lateinit var uiHandler : Handler

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        uiHandler = Handler()


        val fragmentTransaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainHomeLayout, HomeFragment(), "LIST")
        fragmentTransaction.add(R.id.mainHomeLayout, HomeMapFragment(), "MAP")
        fragmentTransaction.commit()

        binding.listOrMapSwitch.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                Application.homeListOrMap = true
                uiHandler.post(Runnable { switchToMap() })
            }else{
                Application.homeListOrMap = false
                uiHandler.post(Runnable { switchToList() })
            }
        }
        return root
    }

    override fun onResume() {
        binding.listOrMapSwitch.isChecked = !binding.listOrMapSwitch.isChecked
        binding.listOrMapSwitch.isChecked = !binding.listOrMapSwitch.isChecked
        super.onResume()
        if(Application.homeListOrMap){
            uiHandler.post(Runnable { switchToMap() })
        }else{
            uiHandler.post(Runnable { switchToList() })
        }
    }

    private fun switchToList() {
        val fragA: Fragment = requireActivity().supportFragmentManager.findFragmentByTag("LIST") as Fragment
        val fragmentTransaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        requireActivity().supportFragmentManager.findFragmentByTag("MAP")
            ?.let { fragmentTransaction.detach(it) }
        fragmentTransaction.attach(fragA)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
        requireActivity().supportFragmentManager.executePendingTransactions()
    }

    private fun switchToMap() {
        val fragB: Fragment = requireActivity().supportFragmentManager.findFragmentByTag("MAP") as Fragment
        val fragmentTransaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        requireActivity().supportFragmentManager.findFragmentByTag("LIST")
            ?.let { fragmentTransaction.detach(it) }
        fragmentTransaction.attach(fragB)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
        requireActivity().supportFragmentManager.executePendingTransactions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}