package com.devcomm.quizohub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.devcomm.quizohub.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var newSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialising ActivitySignIn Binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialising firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // If user don't have account then redirecting him to register. other  alternative is below
//        binding.txtSignUpIfNotRegistered.setOnClickListener{
//            startActivity(Intent(this, SignUpActivity::class.java))
//        }

        //Adding click through binding
        binding.btnSignIn.setOnClickListener{
            // storing the data inputted by user in a string
            val email = binding.etSignInEmail.text.toString()
            val pass = binding.etSignInPassword.text.toString()

            // Adding condition to check and create new account.
            if (email.isNotEmpty() && pass.isNotEmpty()) {

                    // checking firebase and loggin in if details are correct.
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        // condition that what will happen if you are logged in.
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            } else {
                Toast.makeText(this, "Please Enter Username & Password", Toast.LENGTH_SHORT).show()
            }
        }

        // if user don't have account then click on it to create one
        newSignUp = findViewById(R.id.txtSignUpIfNotRegistered)
        newSignUp.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
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