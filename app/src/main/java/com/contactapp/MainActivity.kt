package com.contactapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val tvTitle : TextView  = findViewById(R.id.tv_title)




        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AddMailFragment())
            .addToBackStack(null)
            .commit()
        "Add Email".also { tvTitle.text = it }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_menu)

        bottomNavigationView.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.add_email -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AddMailFragment())
                        .addToBackStack(null)
                        .commit()
                    "Add Email".also { tvTitle.text = it }
                    true
                }
                R.id.contact_list->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ContactListFragment())
                        .addToBackStack(null)
                        .commit()
                    "Contact List".also { tvTitle.text = it }
                    true
                }
                R.id.add_phone->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AddPhoneFragment())
                        .addToBackStack(null)
                        .commit()
                    "Add Phone".also { tvTitle.text = it }
                    true
                }
                else -> false
            }
        }


    }
}