package com.example.projetinf717

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.widget.ViewPager2
import com.example.projetinf717.ui.login.LoginFragment
import com.example.projetinf717.ui.register.RegisterFragment


class MainActivity : AppCompatActivity() {
    private lateinit var authenticationStateAdapter: AuthenticationStateAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authenticationStateAdapter: AuthenticationStateAdapter = AuthenticationStateAdapter (
            this
        )
        authenticationStateAdapter.addFragment(LoginFragment())
        authenticationStateAdapter.addFragment(RegisterFragment())
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = authenticationStateAdapter
    }

    class AuthenticationStateAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
        private val fragmentList: ArrayList<Fragment> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}