package com.edasinar.online_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.edasinar.model.MessageInfo
import com.edasinar.online_lab.databinding.ActivityOneMessageBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * TODO: öğrenci hem kendi sorusunu görecek hem de öğretmenin mesajını görecek. ayrıca başlığı eklemeyi de unutma
 * */


class OneMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOneMessageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var messageInfo: MessageInfo

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar!!.setTitle("One Message")
        navListener()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        messageInfo = intent.getParcelableExtra<MessageInfo>("message")!!

        binding.messageLabelText.text = messageInfo.messageLabel
        binding.questionAreaEditText.setText(messageInfo.messageBody)

        //TODO("ÖĞRETMENİN GÖNDERDİĞİ MESAJI DA BURADA GÖSTERECEKSİN")
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