package com.edasinar.online_lab

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.edasinar.online_lab.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        setToggle()
        actionBarColor()
        navListener()
        getDataFromFirebase()

        supportActionBar!!.title = "PROFİLİM"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setToggle() {
        toggle = ActionBarDrawerToggle(
            this@ProfileActivity,
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

    @SuppressLint("SetTextI18n")
    private fun getDataFromFirebase() {
        val currentUserMail = auth.currentUser?.email.toString()
        val currentUserUid = auth.currentUser?.uid.toString()

        val userRef = firestore.collection("users").document(currentUserUid)
        val imageRef = firestore.collection("photo_info").document(currentUserMail)

         userRef.get().addOnSuccessListener {document ->
             if (document != null) {
                 binding.profileFullName.text = document.data?.get("name").toString() + " " + document.data?.get("surname").toString()
                 binding.profileUsername.text = "@" + document.data?.get("username").toString()
             } else {
                 Toast.makeText(this, "veritabanına erişirken bir hata gerçekleşti", Toast.LENGTH_SHORT).show()
             }
         }

        imageRef.get().addOnSuccessListener { document ->
            if(document != null){
                binding.profilePhoto.scaleType = ImageView.ScaleType.CENTER_CROP
                Picasso.get().load(document.data?.get("downloadUrl").toString()).into(binding.profilePhoto)
            }else
                Toast.makeText(this, "beklenmeyen bir hata oluştu", Toast.LENGTH_SHORT).show()
        }

        //Picasso.get().load(downloadUrl).into(binding.profilePhoto)
    }

    fun settings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun aboutUs(view: View) {
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
    }

    fun goToLesson(view: View) {
        val intent = Intent(this, LessonsActivity::class.java)
        startActivity(intent)
    }

    fun goToNotes(view: View) {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }

    fun goToMessages(view: View) {
        val intent = Intent(this, MessagesActivity::class.java)
        startActivity(intent)
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

}