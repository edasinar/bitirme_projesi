package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.edasinar.online_lab.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*


/**
 * TODO: burada cardviewlerin isimleri değişecek o kadar o da veritabanından alınacak. pek gerek de olmayabilir duruma göre
 *
 * */

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var toggle : ActionBarDrawerToggle

    private lateinit var scrollView: ScrollView
    private lateinit var actionBarImage: View
    private lateinit var rectangleAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view : View = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        intents()
        actionBarColor()

        supportActionBar!!.title = "GİRİŞ SAYFASI"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#000066"))
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun intents() {
        binding.profileCardView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.messageCardView.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
        binding.noteCardView.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }
        binding.lessonsCardView.setOnClickListener {
            val intent = Intent(this, LessonsActivity::class.java)
            startActivity(intent)
        }
        binding.askQuestionCardView.setOnClickListener {
            val intent = Intent(this, AskQuestionActivity::class.java)
            startActivity(intent)
        }
        binding.aboutUsCardView.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
    }

}