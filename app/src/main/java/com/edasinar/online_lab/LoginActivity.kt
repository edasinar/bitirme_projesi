package com.edasinar.online_lab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edasinar.online_lab.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun register(view: View?) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun login(view: View?) {
        val email: String = binding.emailEditText.text.toString()
        val password: String = binding.passwordEditText.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@LoginActivity,
                    e.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}