package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        bottomNav.setOnItemReselectedListener {
            print(it.itemId.toString())
            when(it.itemId){
                R.id.nav_home ->  {
                    loadFragment(HomeFragment())
                }
                R.id.nav_favourites -> {
                    loadFragment(FavouritesFragment())

                }
                R.id.nav_profile ->  {
                    loadFragment(ProfileFragment())
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


}