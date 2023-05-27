package com.edasinar.online_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TeacherReplyMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_reply_message)
    }

    fun uploadFile(view: View) {}
    fun sendMessageToStudent(view: View) {}
}