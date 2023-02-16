package com.devcomm.quizohub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.devcomm.quizohub.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    lateinit var haveAccountSignIn: TextView
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialising ActivitySignUp Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Setting if user have account then go back to signIn Page other alternative is present below
//        binding.txtSignInIfRegistered.setOnClickListener {
//            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
//        }

        // binding buttons with click
        binding.btnSignUp.setOnClickListener {
            // storing the data inputted by user in a string
            val email = binding.etSignUpEmail.text.toString()
            val pass = binding.etSignUpPass.text.toString()
            val confirmPass = binding.etSignUpConfirmPass.text.toString()
            // Adding condition to check and create new account.
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                // checking if both pass are same or not.
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        // condition that what will happen if account is created and what if not?
                        if (it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Username & Password", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

//         Alternative and shortcut of this code is written already in above line using binding.
        // Setting View of Text
        haveAccountSignIn = findViewById(R.id.txtSignInIfRegistered)
        // Adding click on text to signIn if user have an account.
        haveAccountSignIn.setOnClickListener{
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        // check if user is already logged in
        if (firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}