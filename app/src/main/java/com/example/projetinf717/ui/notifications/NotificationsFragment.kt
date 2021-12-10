package com.example.projetinf717.ui.notifications

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.Application
import com.example.projetinf717.MainActivity
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val disconnectButton: Button = binding.btnDisconnect

//        Next fav feature
//        binding.profileSwitchAgency.setOnCheckedChangeListener{_, isChecked ->
//            if(isChecked){
//                Application.agencyMode = true
//                binding.profileImageView.setBackgroundResource(R.drawable.profile_picture_agency)
//            }else{
//                Application.agencyMode = false
//                binding.profileImageView.setBackgroundResource(R.drawable.profile_picture)
//            }
//        }

        disconnectButton.setOnClickListener {
            Application.JWT = null
            val sharedPreferences: SharedPreferences =
                requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.putString("jwt", null)
            myEdit.apply()
            val mainActivityIntent = Intent(context, MainActivity::class.java)
            startActivity(mainActivityIntent)
            activity?.finish()
        }

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}