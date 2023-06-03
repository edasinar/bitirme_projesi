package com.edasinar.online_lab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.edasinar.online_lab.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        auth = FirebaseAuth.getInstance()

        actionBarColor()

        supportActionBar!!.title= "AYARLAR"
    }

    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#E8E8E8"))
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    fun changePhoto(view: View) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changePhotoFragment = PhotoFragment()
        fragmentTransaction.replace(R.id.frameLayout, changePhotoFragment).commit()
    }

    fun changeProfileFeatures(view: View) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changeProfileFragment = ProfilFragment()
        fragmentTransaction.replace(R.id.frameLayout, changeProfileFragment).commit()


    }

    fun signOut(view: View) {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}