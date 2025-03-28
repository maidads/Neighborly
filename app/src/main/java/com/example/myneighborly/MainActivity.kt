package com.example.myneighborly

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myneighborly.chat.ChatsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarIcon: ImageView
    private lateinit var toolbarTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbarIcon = findViewById(R.id.toolbarIcon)
        toolbarTitle = findViewById(R.id.toolbarTitle)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        /*
        val logOutButton: Button = findViewById(R.id.logOutButton)

        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
         */

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNav)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = SearchFragment()
            var selectedIcon: Int = R.drawable.search_icon
            var selectedTitle: String = "Search"

            when (item.itemId) {
                R.id.navigation_search -> {
                    selectedFragment = SearchFragment()
                    selectedIcon = R.drawable.search_icon
                    selectedTitle = "Search"
                }
                R.id.navigation_help -> {
                    selectedFragment = HomeFragment()
                    selectedIcon = R.drawable.home_icon
                    selectedTitle = "Help"
                }
                R.id.navigation_chats -> {
                    selectedFragment = ChatsFragment()
                    selectedIcon = R.drawable.chat_icon
                    selectedTitle = "Chats"
                }
                R.id.navigation_profile -> {
                    selectedFragment = ProfileFragment()
                    selectedIcon = R.drawable.person_icon
                    selectedTitle = "My profile"
                }
            }

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

            toolbarIcon.setImageResource(selectedIcon)
            toolbarTitle.text = selectedTitle

            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commit()
            toolbarIcon.setImageResource(R.drawable.search_icon)
            toolbarTitle.text = "Search"
        }
    }
}
