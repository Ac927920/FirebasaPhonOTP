package com.example.otpdataaccess

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var  logout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)



        logout = findViewById(R.id.logout)
         logout.setOnClickListener{
             auth.signOut()

         }



    }
}