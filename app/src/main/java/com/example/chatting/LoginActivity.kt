package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatting.SharedPrefrence.Constant
import com.example.chatting.SharedPrefrence.PrefManager
import com.example.chatting.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        prefManager = PrefManager(applicationContext)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference("user")

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        binding.loginButton.setOnClickListener {

            if (binding.emailAddress.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            } else if (binding.Password.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else {

                val email = binding.emailAddress.text.toString().trim()
                val password = binding.Password.text.toString().trim()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener

                        //  prefManager.checkLogin(Constant.PREF_IS_LOGIN, true)
                        Log.d("TAG", "Successfully logged in: ${it.result!!.user?.uid}")
                        Toast.makeText(this, "${it.result}", Toast.LENGTH_SHORT).show()

                        prefManager.checkLogin(Constant.PREF_IS_LOGIN, true)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()

                    }


//                var etEmail = binding.emailAddress.text.toString().trim()
//                var etPassword = binding.Password.text.toString().trim()
//
//                val email = prefManager.getValue(Constant.PREF_IS_EMAIL)
//                val password = prefManager.getValue(Constant.PREF_IS_PASSWORD)
//
//                if (email == etEmail && password == etPassword) {
//                    val email = binding.emailAddress.text.toString()
//                    val password = binding.Password.text.toString()
//
//                    login(email, password)
//
//                    prefManager.checkLogin(Constant.PREF_IS_LOGIN, true)
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intent)
//                    Toast.makeText(this, "Yor are Login", Toast.LENGTH_SHORT).show()
//                    finish()
//
//
//                } else {
//                   Toast.makeText(this, "Please Register ", Toast.LENGTH_SHORT).show()
//                }
            }
        }

    }


//    private fun login(email: String, password: String) {
//
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//
//                    val firebaseUser : FirebaseUser = mAuth.currentUser!!
//
//                    if (firebaseUser.isEmailVerified){
//                        Toast.makeText(this, "Yor are log in now", Toast.LENGTH_SHORT).show()
//                    }
//
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    Log.w("TAG", "signInWithEmail:failure", task.exception)
//                    Toast.makeText(this, "User does not Exits", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//    }

    override fun onStart() {
        super.onStart()

        if (prefManager.getBoolean(Constant.PREF_IS_LOGIN)) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}


