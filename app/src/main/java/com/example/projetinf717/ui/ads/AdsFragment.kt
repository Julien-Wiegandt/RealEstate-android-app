package com.example.projetinf717.ui.ads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.databinding.FragmentAdsBinding
import android.R
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.projetinf717.ui.addads.AddAdsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AdsFragment : Fragment() {

    private lateinit var adsViewModel: AdsViewModel
    private var _binding: FragmentAdsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adsViewModel =
            ViewModelProvider(this).get(AdsViewModel::class.java)

        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAds
        adsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.floatingAddButton.setOnClickListener{
            parentFragmentManager.commit {
                replace(container!!.id, AddAdsFragment())
                setReorderingAllowed(true)
                addToBackStack("AddAdsFragment in the place")
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}