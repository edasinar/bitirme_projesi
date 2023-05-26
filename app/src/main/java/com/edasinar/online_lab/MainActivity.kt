package com.edasinar.online_lab

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view : View = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navListener()

        val menuNames = ArrayList(Arrays.asList("Notlar", "Mesajlar", "Dersler"))

        binding.recViewMain.layoutManager = LinearLayoutManager(this)
        val adapter = MainAdapter(menuNames)
        binding.recViewMain.adapter = adapter
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
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
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