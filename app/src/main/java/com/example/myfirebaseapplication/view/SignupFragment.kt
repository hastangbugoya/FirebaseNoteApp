package com.example.myfirebaseapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.model.SignUpUser
import kotlinx.android.synthetic.main.signup_layout.*

class SignupFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.signup_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        signup_button.setOnClickListener {
            val email = su_email_edittext.text.toString().trim()
            val password = su_password_edittext.text.toString().trim()

            (requireActivity() as SignInActivity).signupUser(SignUpUser(email, password))
        }
    }
}