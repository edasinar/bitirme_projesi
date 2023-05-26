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

    //TODO: ayarları değiştirirken yeni activity yerine fragment kullan nasıl kullanıldığına bak
    //TODO: her ayar için farklı bir fragment olacak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        auth = FirebaseAuth.getInstance()
    }

    fun changePhoto(view: View) {
        //TODO: galeriden fotoğraf seçecek ya da var olan fotoğrafı silecek
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changePhotoFragment = PhotoFragment()
        fragmentTransaction.replace(R.id.frameLayout, changePhotoFragment).commit()
    }

    fun changeProfileFeatures(view: View) {
        //TODO: isim soyisim ve varsa ek bilgileri değiştirecek
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changeProfileFragment = ProfilFragment()
        fragmentTransaction.replace(R.id.frameLayout, changeProfileFragment).commit()


    }

    fun changeAccountSecurity(view: View) {
        //TODO: email ve şifreyi değiştirecek
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changeSecurityFragment = SecurityFragment()
        fragmentTransaction.replace(R.id.frameLayout, changeSecurityFragment).commit()
    }


    fun notificationSetting(view: View) {
        //TODO: checkbox vardı. ona göre bildirim eklenecek eğer check box işaretliyse bildirim gelecek.
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val changeNotificationFragment = NotificationFragment()
        fragmentTransaction.replace(R.id.frameLayout, changeNotificationFragment).commit()
    }

    fun signOut(view: View) {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}