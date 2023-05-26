package com.edasinar.online_lab

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.edasinar.model.MessageInfo
import com.edasinar.model.Teachers
import com.edasinar.online_lab.databinding.ActivityAskQuestionBinding
import com.google.api.HttpBody
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList

class AskQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAskQuestionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firestore : FirebaseFirestore
    private lateinit var storage : FirebaseStorage

    private lateinit var fileUri: Uri

    private var downloadUrl = ""

    private val PERMISSION_CODE = 1000
    private val FILE_PICK_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskQuestionBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        supportActionBar!!.title = "Sorunu Sor"
        navListener()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        firestore = Firebase.firestore
        storage = Firebase.storage
        spinner()
        toggle = ActionBarDrawerToggle(
            this@AskQuestionActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == FILE_PICK_CODE) {

            fileUri = data?.data!!

            val fileName = fileUri.path?.substringAfterLast("/")

            val ref = storage.reference
            val fileRef = ref.child("messageFile").child("$fileName")

            fileRef.putFile(fileUri).addOnSuccessListener {
                downloadUrl = fileRef.downloadUrl.toString()
            }.addOnFailureListener {
                Toast.makeText(this,"dosya yüklenemedi hata....", Toast.LENGTH_SHORT).show()
            }

        }
        val selectedFilePath = fileUri.path // Dosya yolu
        val selectedFileName = selectedFilePath?.substringAfterLast("/") // Dosya adı

        binding.fileNameTextView.text = selectedFileName
    }

    private fun selectFileFromPhone() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, FILE_PICK_CODE)

    }

    fun selectFileFromPhone(view: View) {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            selectFileFromPhone()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
        }
    }

    private fun uploadDataToFirebase(teacher: String, messageLabel: String, messageBody: String) {

        val uuid = UUID.randomUUID()
        val messageInfo = MessageInfo(auth.currentUser!!.email!!, teacher, messageLabel,
            messageBody, downloadUrl)
        firestore.collection("message_info").document("$uuid").set(messageInfo)

    }

    @SuppressLint("SetTextI18n")
    fun sendMessage(view: View) {
        val spinner = binding.spinner
        val questionLabel = binding.questionLabelEditText.text.toString()
        val questionText = binding.questionBodyEditText.text.toString()
        var isValid = true
        if(spinner.selectedItem == "Öğretmen Seçin") {
            Toast.makeText(this, "öğretmen seçmeniz zorunludur.", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if(questionLabel.isEmpty()) {
            Toast.makeText(this, "soru başlığı boş geçilemez", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if(questionText.isEmpty()) {
            Toast.makeText(this, "soru kısmı boş geçilemez", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (isValid) {
            uploadDataToFirebase(spinner.selectedItem.toString(), questionLabel, questionText)

            spinner.setSelection(0)
            binding.questionLabelEditText.setText("")
            binding.questionBodyEditText.setText("")
            binding.fileNameTextView.text = "eklemek istediğiniz dosyayı seçin"
        }
    }

    private fun getTeachers(callback: (ArrayList<Teachers>) -> Unit) {
        val teacherList = ArrayList<Teachers>()
        val ref = firestore.collection("teachers")
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val fullName = document.get("full_name") as String
                    teacherList.add(Teachers(fullName))
                }
                callback(teacherList)
            }
            .addOnFailureListener { exception ->
                println("hata hata hata!!!!")
                callback(teacherList)
            }
    }

    private fun spinner() {

        getTeachers { teacher ->
            val names = teacher.map { it.fullName } as ArrayList<String>
            names.add(0, "Öğretmen Seçin")
            val spinner = binding.spinner
            spinner.adapter = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, names) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val textView = view.findViewById<TextView>(R.id.custom_spinner_item_textview)
                    textView.text = names[position]
                    return view
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val textView = view.findViewById<TextView>(R.id.custom_spinner_item_textview)
                    textView.text = names[position]
                    return view
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
                }
                R.id.notes -> {
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
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