package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.edasinar.model.Lessons
import com.edasinar.online_lab.databinding.ActivityLessonsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class LessonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLessonsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth

        navListener()
        actionBarColor()
        setToggle()

        getValueFromDB { nameOfDocs ->

            val lessonList = nameOfDocs.map { it } as ArrayList<Lessons>
            val adapter = LessonsAdapter(lessonList)
            val layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "DERS VİDEOLARI"
    }

    private fun setToggle() {
        toggle = ActionBarDrawerToggle(
            this@LessonsActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#000066"))
        actionBar?.setBackgroundDrawable(colorDrawable)
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
                R.id.notes -> {
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                }
                R.id.askQue -> {
                    val intent = Intent(this, AskQuestionActivity::class.java)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.messages -> {
                    val intent = Intent(this, MessagesActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutUs -> {
                    val intent = Intent(this, AboutUsActivity::class.java)
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

    private fun getValueFromDB(callback: (ArrayList<Lessons>) -> Unit) {
        val nameOfDoc = ArrayList<Lessons>()
        val ref = firestore.collection("lessons")
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val fileName = document.get("file_name") as String
                    val token = document.get("token") as String
                    nameOfDoc.add(Lessons(fileName,token))
                }
                callback(nameOfDoc)
            }
            .addOnFailureListener { exception ->
                println("hata hata hata!!!!")
                callback(nameOfDoc)
            }
    }

}