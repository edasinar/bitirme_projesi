package com.edasinar.online_lab

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.edasinar.model.Lessons
import com.edasinar.model.Notes
import com.edasinar.online_lab.databinding.ActivityNotesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        getValueFromDB { nameOfDocs ->
            val notesList = nameOfDocs.map { it } as ArrayList<Notes>
            val adapter = NotesAdapter(notesList)
            val layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.setLayoutManager(layoutManager)
            binding.recyclerView.setAdapter(adapter)
        }
        supportActionBar!!.setTitle("")
        navListener()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
            this@NotesActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun navListener() {
        binding.navView.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.signOut -> {
                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.askQue -> {
                    val intent = Intent(this, AskQuestionActivity::class.java)
                    startActivity(intent)
                }
                R.id.lessons -> {
                    val intent = Intent(this, LessonsActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutUs -> {
                    val intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                }
                R.id.messages -> {
                    val intent = Intent(this, MessagesActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun getValueFromDB(callback: (ArrayList<Notes>) -> Unit) {
        val nameOfDoc = ArrayList<Notes>()
        val ref = firestore.collection("notes")
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val fileName = document.get("file_name") as String
                    val token = document.get("token") as String
                    nameOfDoc.add(Notes(fileName,token))
                }
                callback(nameOfDoc)
            }
            .addOnFailureListener { exception ->
                println("hata hata hata!!!!")
                callback(nameOfDoc)
            }
    }
}