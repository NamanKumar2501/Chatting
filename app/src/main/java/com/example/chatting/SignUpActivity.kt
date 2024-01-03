package com.example.chatting

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatting.SharedPrefrence.Constant
import com.example.chatting.SharedPrefrence.PrefManager
import com.example.chatting.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val prefManager = PrefManager(applicationContext)

        mAuth = FirebaseAuth.getInstance()


        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.RegisterButton.setOnClickListener {

            if (binding.EmailAddress.text.toString().isEmpty()){
                Toast.makeText(this, "Enter Email Address", Toast.LENGTH_SHORT).show()
            }else if (binding.userName.text.toString().isEmpty()){
                Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show()
            }else if (binding.Password.text.toString().isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }else{

                val name = binding.userName.text.toString().trim()
                val email = binding.EmailAddress.text.toString().trim()
                val password = binding.Password.text.toString().trim()

                signUp(name,email,password)

                prefManager.name(Constant.PREF_IS_NAME, name)
                prefManager.Email(Constant.PREF_IS_EMAIL, email)
                prefManager.Password(Constant.PREF_IS_PASSWORD, password)


            }

        }

    }

    private fun signUp(name:String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,password,mAuth.currentUser!!.uid)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String,password: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,password,uid))
    }


}