package com.example.projetinf717.ui.addads

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.databinding.FragmentAddAdsBinding


class AddAdsFragment : Fragment() {

    private lateinit var addAdsViewModel: AddAdsViewModel
    private var _binding: FragmentAddAdsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addAdsViewModel =
            ViewModelProvider(this).get(AddAdsViewModel::class.java)

        _binding = FragmentAddAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Binding views
//        binding.imageView
//        binding.editTextTitle
//        binding.imgAddButton
//        binding.editTextAddress
//        binding.locationButton
//        binding.editTextDescription
//        binding.editTextEstateType
//        binding.editTextEstatePrice
//        binding.switchRent
//        binding.imageButtonBed
//        binding.editTextNumberBed
//        binding.imageButtonBathroom
//        binding.editTextNumberBathroom
//        binding.imageButtonGarage
//        binding.editTextNumberGarage
//        binding.editTextEmail
//        binding.editTextPhone
//        binding.createAdsButton
        binding.imageButtonBed.setOnClickListener{
            binding.editTextNumberBed.requestFocus()
        }
        binding.imageButtonBathroom.setOnClickListener{
            binding.editTextNumberBathroom.requestFocus()
        }
        binding.imageButtonGarage.setOnClickListener{
            binding.editTextNumberGarage.requestFocus()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}