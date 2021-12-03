package com.example.projetinf717.ui.notifications

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.projetinf717.AppActivity
import com.example.projetinf717.MainActivity
import com.example.projetinf717.R
import com.example.projetinf717.databinding.ActivityAppBinding
import com.example.projetinf717.databinding.FragmentNotificationsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        binding.profileSwitchAgency.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                binding.profileImageView.setBackgroundResource(R.drawable.profile_picture_agency)
            }else{
                binding.profileImageView.setBackgroundResource(R.drawable.profile_picture)
            }
        }

        disconnectButton.setOnClickListener {
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