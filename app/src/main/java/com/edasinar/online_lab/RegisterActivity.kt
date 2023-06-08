package com.edasinar.online_lab

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.edasinar.model.Users
import com.edasinar.online_lab.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        actionBarColor()

        supportActionBar!!.title = "ARAMIZA KATILIN"
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // İsteğe bağlı olarak, mevcut aktiviteyi sonlandırabilirsiniz
        super.onBackPressed()
    }


    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#000066"))
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    fun signUp(view: View?) {
        val email: String = binding.emailRegText.text.toString()
        val password: String = binding.passwRegText.text.toString()
        val name: String = binding.nameRegText.text.toString()
        val surname: String = binding.surnameRegText.text.toString()
        val username: String = binding.userNameRegText.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "User Created", Toast.LENGTH_SHORT).show()
                    userID = auth.currentUser!!.uid
                    val documentReference =
                        firestore.collection("users").document(
                            userID
                        )
                    val user = Users(name, surname, username, email,"student")
                    documentReference.set(user)
                        .addOnSuccessListener {
                            Log.d(
                                ContentValues.TAG,
                                "on success: user profile is created for$userID"
                            )
                        }
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(this@RegisterActivity, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}