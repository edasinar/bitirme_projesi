package com.edasinar.online_lab

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.edasinar.model.Users
import com.edasinar.online_lab.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore

        getDataFromFirestore { currentUser ->
            println(currentUser.name + " " + currentUser.surname + " bura da onviewcreated")
            binding.nameEditText.hint = currentUser.name
            binding.lastNameEditText.hint = currentUser.surname
            binding.usernameEditText.hint = currentUser.username

        }

        binding.button.setOnClickListener {
            uploadDataToFirestore()
            getDataFromFirestore { currentUser ->
                println(currentUser.name + " " + currentUser.surname + " bura da onviewcreated")
                binding.nameEditText.hint = currentUser.name
                binding.lastNameEditText.hint = currentUser.surname
                binding.usernameEditText.hint = currentUser.username

            }
            Toast.makeText(requireContext(), "başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT).show()

        }

    }

    private fun getDataFromFirestore(callback: (Users) -> Unit) {
        val currentUser = auth.currentUser?.email.toString()
        val userRef = firestore.collection("users")
        val query = userRef.whereEqualTo("email",currentUser)
        var name = ""
        var surname = ""
        var username = ""
        query.get().addOnSuccessListener {
            for (document in it.documents) {
                name = document.getString("name").toString()
                surname = document.getString("surname").toString()
                username = document.getString("username").toString()
            }
            println("$name $surname burası getdarafromfirestore")
            callback(Users(name, surname, username, currentUser,"student"))
        }




    }

    private fun uploadDataToFirestore() {
        val currentUser = auth.currentUser?.uid.toString()
        val userRef = firestore.collection("users").document(currentUser)

        val name = binding.nameEditText.text?.toString()
        val surname = binding.lastNameEditText.text?.toString()
        val username = binding.usernameEditText.text?.toString()

        val data = hashMapOf<String, Any>()

        if (!name.isNullOrEmpty()) {
            data["name"] = name
        }

        if (!surname.isNullOrEmpty()) {
            data["surname"] = surname
        }

        if (!username.isNullOrEmpty()) {
            data["username"] = username
        }

        if (data.isNotEmpty()) {
            userRef.update(data)
                .addOnSuccessListener {
                    // Update successful
                }
                .addOnFailureListener { e ->
                    // Update failed
                    Log.d(TAG, "Unexpected error: ${e.message}")
                }
        }
    }

}