package com.edasinar.online_lab

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.edasinar.model.TeacherModel
import com.edasinar.model.Users
import com.edasinar.online_lab.databinding.ActivityTeacherRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TeacherRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
    }

    fun teacherRegister(view: View) {
        val name = binding.teacherRegisterName.text.toString()
        val surname = binding.teacherRegisterSurname.text.toString()
        val password = binding.teacherRegisterPassword.text.toString()
        val branch = binding.teacherRegisterBranch.text.toString()
        val email = formatEmail(name,surname)

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Teacher Created", Toast.LENGTH_SHORT).show()
                userID = auth.currentUser!!.uid
                val documentReference =
                    firestore.collection("teachers").document(userID)

                val teacherModel = TeacherModel(concatenateName(name, surname), email,
                    branch, "teacher")

                documentReference.set(teacherModel)
                    .addOnSuccessListener {
                        Log.d(
                            ContentValues.TAG,
                            "on success: teacher profile is created for$userID, email: $email"
                        )
                    }
                startActivity(Intent(this, TeacherHomeActivity::class.java))
            } else {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun formatEmail(name: String, surname: String): String {
        val newName = name.trim().replace(" ",".")
        val newSurname = surname.trim().replace(" ",".")
        return "$newName.$newSurname@onlinelab.com"
    }

    private fun concatenateName(name: String, surname: String): String {
        val formattedName = name.trim().replace("\\s+".toRegex(), " ")
        val formattedSurname = surname.trim().replace("\\s+".toRegex(), " ")
        return "$formattedName $formattedSurname"
    }


}