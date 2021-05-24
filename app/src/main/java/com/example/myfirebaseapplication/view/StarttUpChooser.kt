package com.example.myfirebaseapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class StartUpChooser : AppCompatActivity() {

    lateinit var openIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified != false) {
            //Logged in and email verified...navigate to home
            openIntent = Intent(this, HomePageActivity::class.java)
        } else {
            //Not logged in... navigate to login
            openIntent = Intent(this, SignInActivity::class.java)
        }

        startActivity(openIntent.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}