package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.edasinar.model.MessageInfo
import com.edasinar.online_lab.databinding.ActivityMessagesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class MessagesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMessagesBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        navListener()
        actionBarColor()
        setToggle()

        getCurrentUserMessagesFromFirestore { messageInfoList ->
            val adapter = MessagesAdapter(messageInfoList)
            val layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "MESAJLARIM"
    }

    private fun setToggle() {
        toggle = ActionBarDrawerToggle(
            this@MessagesActivity,
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

    private fun getCurrentUserMessagesFromFirestore(callback: (ArrayList<MessageInfo>) -> Unit) {
        val currentUserEmail = auth.currentUser?.email.toString()
        val messageInfoList = ArrayList<MessageInfo>()
        val messageRef = firestore.collection("message_info")
        val query = messageRef.whereEqualTo("email", currentUserEmail)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val messageLabel = document.getString("messageLabel")
                    val teacher = document.getString("teacher")
                    val messageBody = document.getString("messageBody")
                    val fileDownloadUrl = document.getString("fileDownloadUrl")
                    messageInfoList.add(MessageInfo(currentUserEmail, teacher!!, messageLabel!!,
                        messageBody!!, fileDownloadUrl!!))
                }

                callback(messageInfoList)
            }
            .addOnFailureListener { exception ->
                println("Exception: $exception")
                callback(ArrayList())
            }
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
                R.id.aboutUs -> {
                    val intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                }
                R.id.lessons -> {
                    val intent = Intent(this, LessonsActivity::class.java)
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