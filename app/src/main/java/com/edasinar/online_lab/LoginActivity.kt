package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.edasinar.online_lab.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        actionBarColor()

        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar!!.title = "GİRİŞ YAP"
    }

    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#E8E8E8"))
        actionBar?.setBackgroundDrawable(colorDrawable)
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
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if(task.isSuccessful) {
                    getUserStatus()
                } else {
                    Toast.makeText(this,"Giriş yapılırken beklenmeyen bir hata oluştu daha sonra tekrar deneyiniz",
                    Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun getUserStatus() {
        val uid = auth.currentUser?.uid

        firestore.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val status = document.getString("status")

                    if (status == "student") {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Beklenmeyen hata..", Toast.LENGTH_SHORT).show()
            }
        firestore.collection("teachers")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val status = document.getString("status")
                    if (status == "teacher") {
                        Toast.makeText(this,"Öğretmen Girişi Yasaktır..",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "beklenmeyen hata", Toast.LENGTH_SHORT).show()
            }
    }

}