package com.example.myneighborly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNav)

        // Lägga till lyssnare för BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = HomeFragment()  // Standardfragment

            when (item.itemId) {
                R.id.navigation_help -> selectedFragment = HomeFragment()
                R.id.navigation_chats -> selectedFragment = ChatsFragment()
                R.id.navigation_profile -> selectedFragment = ProfileFragment()
            }

            // Ersätt fragmentet i FrameLayout
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

            true
        }

        // Sätt standardfragment
        if (savedInstanceState == null) {
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }
    }
}
