package com.edasinar.online_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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