package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.edasinar.model.MessageInfo
import com.edasinar.online_lab.databinding.ActivityOneMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class OneMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOneMessageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var messageInfo: MessageInfo
    private lateinit var label: String

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar!!.title = "MESAJLARIM"
        navListener()
        actionBarColor()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        firestore = Firebase.firestore
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        messageInfo = intent.getParcelableExtra<MessageInfo>("message")!!
        label = messageInfo.messageLabel

        binding.messageLabelText.text = messageInfo.messageLabel
        binding.questionAreaEditText.text = messageInfo.messageBody

        teacherAnswer {
            binding.messageAreaEditText.text = it
        }
    }

    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#EDA123"))
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun teacherAnswer(callback: (String) -> Unit) {
        val currentUserEmail = auth.currentUser?.email.toString()
        val answerRef = firestore.collection("teacher_message_info")
        val user = firestore.collection("message_info")
        val userQuery = user.whereEqualTo("email", currentUserEmail)
        userQuery.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val docLabel = document.getString("messageLabel")
                    if(label == docLabel) {
                        val query = answerRef.whereEqualTo("messageLabel", label)
                        query.get()
                            .addOnSuccessListener { querySnap ->
                                var answerBody = ""
                                for(doc in querySnap.documents) {
                                     answerBody = doc.getString("messageBody").toString()
                                }

                                callback(answerBody)
                            }
                            .addOnFailureListener { exception ->
                                println("Exception: $exception")
                                callback("")
                            }
                    }
                }


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
                    finish()
                }
                R.id.notes -> {
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.askQue -> {
                    val intent = Intent(this, AskQuestionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.lessons -> {
                    val intent = Intent(this, LessonsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.aboutUs -> {
                    val intent = Intent(this, AboutUsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.messages -> {
                    val intent = Intent(this, MessagesActivity::class.java)
                    startActivity(intent)
                    finish()
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