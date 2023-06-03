package com.edasinar.online_lab

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.edasinar.online_lab.databinding.ActivityAboutUsBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AboutUsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAboutUsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        auth = Firebase.auth

        actionBarColor()
        navListener()
        setToggle()
        fillTextViews()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "HAKKIMIZDA"
    }

    private fun setToggle() {
        toggle = ActionBarDrawerToggle(
            this@AboutUsActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun actionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#E8E8E8"))
        actionBar?.setBackgroundDrawable(colorDrawable)
    }

    @SuppressLint("SetTextI18n")
    private fun fillTextViews() {
        binding.hakkimizda.text = "HAKKIMIZDA" +
                "\n"+
                "Adalete ve eşitliğe adanmış bir eğitim platformuyuz. Amacımız, her çocuğun, gençin ve yetişkinin eşit ve adil bir şekilde kaliteli eğitim imkanlarından yararlanmasını sağlamaktır. Farkındayız ki, eğitimde fırsat eşitsizliği toplumlarımızı derinden etkileyen bir sorundur. Bu nedenle, sponsor öğretmenlerimizle birlikte hareket ederek, eğitimdeki bu adaletsizliği gidermek için çaba harcıyoruz.\n" +
                "\n" +
                "Projemiz, sponsor öğretmenlerin cömert katkılarına dayanmaktadır. Bu öğretmenler, kendi bilgi, deneyim ve enerjilerini paylaşarak, öğrencilerin gelecekteki başarıları için güçlü bir temel oluşturmayı amaçlamaktadır. Onların desteğiyle, eğitim kaynaklarını ve ders videolarını ücretsiz olarak sunarak, her öğrencinin potansiyelini gerçekleştirmesine yardımcı olmayı hedefliyoruz.\n" +
                "\n" +
                "Gururla ifade ediyoruz ki, bu projenin temelinde ortak değerler ve işbirliği yatmaktadır. Sponsorlarımızın destekleriyle, öğretmenlerimizin özverili çalışmasıyla ve öğrencilerimizin öğrenme arzusuyla, eğitimde fırsat eşitliği sağlayarak toplumlarımızı dönüştürmeye yönelik bir çaba ortaya koyuyoruz.\n" +
                "\n" +
                "Hep birlikte, bu projenin büyümesini ve genişlemesini sağlayarak, gelecekte daha da fazla öğrenciye ulaşmayı ve onlara eşit bir eğitim fırsatı sunmayı hedefliyoruz. Eğitimdeki bu dönüşümün, toplumumuzdaki eşitsizlikleri azaltacağına, bireylerin hayallerini gerçekleştireceğine ve birlikte daha adil bir dünya inşa edeceğimize inanıyoruz."

        binding.vizyon.text = "VİZYONUMUZ" +
                "\n" +
                "Vizyonumuz, eğitimde fırsat eşitsizliğini gidermektir. İnanıyoruz ki her öğrencinin kaliteli bir eğitime erişimi olmalıdır, çünkü eğitim, bireylerin potansiyellerini keşfetmelerine ve toplumlarına değer katmalarına olanak sağlar. Sponsor öğretmenlerimizle birlikte, kaynaklara erişim konusunda yaşanan eşitsizlikleri ortadan kaldırarak, tüm öğrencilerin eşit şekilde yararlanabileceği bir öğrenme ortamı oluşturmayı hedefliyoruz."

        binding.misyon.text ="MİSYONUMUZ" +
                "\n" +
                "Misyonumuz, sponsor öğretmenlerin desteğiyle ücretsiz ve yüksek kaliteli eğitim kaynaklarına erişimi olmayan öğrencilere destek sağlamaktır. Öğretmenlerimiz, gönüllü olarak bu projeye katılarak belli bir ücret talep etmeksizin, bilgi ve deneyimlerini paylaşacaklardır. Sistemimize yükledikleri ders videoları ve kaynaklar, öğrencilerin herhangi bir mali yükü olmadan bu değerli kaynaklardan yararlanmalarını sağlayacaktır."

        ///dkdlsdkldldl
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
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
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