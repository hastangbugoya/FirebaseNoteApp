package com.example.myfirebaseapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.model.SignUpUser
import com.example.myfirebaseapplication.utility.DebugHelper.Companion.LogKitty
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signin_layout.*

class SignInActivity : AppCompatActivity() {

//    private val postReference = FirebaseDatabase.getInstance().reference.child("Posts")
//    private val userReference = FirebaseDatabase.getInstance().reference.child("Users")

    private val signupFragment = SignupFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_layout)
        LogKitty("onCreate")
        signin_button.setOnClickListener {
            val email = email_edittext.text.toString().trim()
            val password = password_edittext.text.toString().trim()
            LogKitty("${email},${password}")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    LogKitty("onCreate->FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)\n" +
                            "                .addOnCompleteListener")
                    if (task.isSuccessful) {
                        if (FirebaseAuth.getInstance().currentUser?.isEmailVerified != false) {
                            LogKitty("onCreate->FirebaseAuth.getInstance().currentUser?.isEmailVerified != false")
                            openHomePage()
                        } else {
                            showVerificationDialog(email)
                        }
                    } else {
                        showError(task)
                    }
                }

        }

        signup_textview.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                ).addToBackStack(signupFragment.tag)
                .replace(R.id.signup_frame, signupFragment)
                .commit()
        }

//        userReference.push().setValue(user)

//        val key: String = postReference.push().key ?: ""
//        Log.d("TAG_X", "pushed -> empty space -> $key")
//        postReference.child(key).setValue(dish)
//        Log.d("TAG_X", "Food item posted $key")

        //Read from Realtime Database
//        val postList = mutableListOf<Food>()
//        postReference.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                snapshot.children.forEach {
//                    it.getValue(Food::class.java)?.let { food ->
//                        postList.add(food)
//                    }
//                }
////                addToRecyclerView(postList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            //we never make mistakes..
//            }
//        })
    }

    fun signupUser(signUpUser: SignUpUser) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(signUpUser.email, signUpUser.password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    //User created
                    if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                        openHomePage()
                    } else {
                        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                        showVerificationDialog(signUpUser.email)

                    }

                } else {
                    Log.d("TAG_X", task.exception.toString())
                    showError(task)
                }

            }


    }

    private fun showError(task: Task<AuthResult>) {
        Snackbar.make(
            root,
            "Error: ${task.exception?.localizedMessage}",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showVerificationDialog(signUpUser: String) {
        AlertDialog.Builder(
            ContextThemeWrapper(
                this,
                R.style.Theme_AppCompat_Dialog
            )
        )
            .setTitle("Confirm Email")
            .setMessage("Please check your email: ${signUpUser}. A confirmation email has been sent.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun openHomePage() {
        startActivity(Intent(this, HomePageActivity::class.java).also {
            it.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

//    data class Food(
//        var dishName: String,
//        val type: String,
//        val calories: Int,
//        val recipe: String,
//        var key: String
//    ) {
//        constructor() : this("", "", 0, "", "")
//    }
//
//    data class User(val userName: String, val userId: String)

}