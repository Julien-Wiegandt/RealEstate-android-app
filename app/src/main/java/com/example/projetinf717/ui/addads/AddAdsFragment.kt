package com.example.projetinf717.ui.addads

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.databinding.FragmentAddAdsBinding


class AddAdsFragment : Fragment() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextEstateType: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextEstatePrice: EditText
    private lateinit var editTextNumberBathroom: EditText
    private lateinit var editTextNumberBed: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText

    private lateinit var createAdsButton: Button

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

        val imageView: ImageView = binding.imageView

        val imgAddButton: ImageButton = binding.imgAddButton
        val locationButton: ImageButton = binding.locationButton
        val imageButtonBed: ImageButton = binding.imageButtonBed
        val imageButtonBathroom : ImageButton = binding.imageButtonBathroom
        createAdsButton = binding.createAdsButton
        createAdsButton.isEnabled = false

        val switchRent: Switch = binding.switchRent

        editTextTitle = binding.editTextTitle
        editTextAddress = binding.editTextAddress
        editTextDescription = binding.editTextDescription
        editTextEstateType = binding.editTextEstateType
        editTextEstatePrice = binding.editTextEstatePrice
        editTextNumberBathroom = binding.editTextNumberBathroom
        editTextNumberBed = binding.editTextNumberBed
        editTextEmail = binding.editTextEmail
        editTextPhone = binding.editTextPhone

        editTextTitle.addTextChangedListener(textWatcher)
        editTextAddress.addTextChangedListener(textWatcher)
        editTextDescription.addTextChangedListener(textWatcher)
        editTextEstateType.addTextChangedListener(textWatcher)
        editTextEstatePrice.addTextChangedListener(textWatcher)
        editTextNumberBathroom.addTextChangedListener(textWatcher)
        editTextNumberBed.addTextChangedListener(textWatcher)
        editTextEmail.addTextChangedListener(textWatcher)
        editTextPhone.addTextChangedListener(textWatcher)

        addAdsViewModel.getAction().observe(viewLifecycleOwner,
            { action -> action?.let { handleAction(it) } })


        createAdsButton.setOnClickListener {
            val title = editTextTitle.text.toString()
            val address = editTextAddress.text.toString()
            val desc = editTextDescription.text.toString()
            val estateType = editTextEstateType.text.toString()
            val estatePrice = editTextEstatePrice.text.toString()
            val numberBath = editTextNumberBathroom.text.toString()
            val numberBed = editTextNumberBed.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()
            val rent = switchRent.isChecked
            addAdsViewModel.createAd(title, address, desc, estateType, estatePrice, numberBath, numberBed, email, phone, rent)
        }


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

    private fun handleAction(action: Action) {
        when (action.value) {
            Action.SHOW_AD_CREATED -> {
                Toast.makeText(context,"Ad created", Toast.LENGTH_SHORT).show();
            }
            Action.SHOW_INVALID_FORM -> {
                Toast.makeText(context,"Invalid form", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    private fun checkFieldsForEmptyValues() {
        val s1: String = editTextTitle.text.toString()
        val s2: String = editTextAddress.text.toString()
        val s3: String = editTextDescription.text.toString()
        val s4: String = editTextEstateType.text.toString()
        val s5: String = editTextEstatePrice.text.toString()
        val s6: String = editTextNumberBathroom.text.toString()
        val s7: String = editTextNumberBed.text.toString()
        val s8: String = editTextEmail.text.toString()
        val s9: String = editTextPhone.text.toString()
        createAdsButton.isEnabled = s1.isNotEmpty() && s2.isNotEmpty() &&
                s3.isNotEmpty() && s4.isNotEmpty()&& s5.isNotEmpty()
                && s6.isNotEmpty()&& s7.isNotEmpty()&& s8.isNotEmpty()
                && s9.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}