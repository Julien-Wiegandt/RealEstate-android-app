package com.example.projetinf717

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.widget.ViewPager2
import com.example.projetinf717.ui.login.LoginFragment
import com.example.projetinf717.ui.register.RegisterFragment
import com.auth0.android.jwt.JWT





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
        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val token : String? = sharedPreferences.getString("jwt", null)
        // Crash my app (JulienW)
        if(token !== null){
            Application.JWT = token
            val jwt = JWT(token)
            val isExpired: Boolean = jwt.isExpired(10) // 10 seconds leeway
            if(!isExpired){
                val appActivityIntent = Intent(applicationContext, AppActivity::class.java)
                startActivity(appActivityIntent)
                this.finish()
            }
        }
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

    override fun onResume() {
        super.onResume()
        Application.activityResumed()
    }

    override fun onPause() {
        super.onPause()
        Application.activityPaused()
    }
}