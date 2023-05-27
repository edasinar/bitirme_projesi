package com.edasinar.online_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TeacherFileUploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_file_upload)
    }

    fun uploadFileFromPhone(view: View) {}
    fun uploadVideoFromFirestore(view: View) {}
    fun uploadNotesFromFirestore(view: View) {}
}