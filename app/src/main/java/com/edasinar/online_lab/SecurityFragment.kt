package com.edasinar.online_lab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.edasinar.online_lab.databinding.FragmentSecurityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SecurityFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSecurityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecurityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.securitysSaveButton.setOnClickListener {
            updateValues()
        }
    }

    private fun updateValues() {
        val email = binding.emailChangeEditText.text?.toString()
        val newPass1 = binding.newPasswordFirstEditText.text.toString()
        val newPass2 = binding.newPasswordSecondEditText.text.toString()

        val currentUser = auth.currentUser

        var flagE = false
        var flagP = false

        if(!email.equals(null)) {
            currentUser?.updateEmail(email!!)?.addOnCompleteListener { task ->
                if(task.isSuccessful)
                    flagE = true
                else
                    println("olmadı...")

            }
        }

        if(!newPass1.equals(null) && !newPass2.equals(null)) {
            if (newPass1 == newPass2) {
               currentUser?.updatePassword(newPass1)?.addOnCompleteListener { task ->
                   if(task.isSuccessful)
                       flagP = true
                   else
                       println("olmadı")

               }
            }
            else
                Toast.makeText(requireContext(),"Girdiğiniz şifreler eşleşmemektedir.", Toast.LENGTH_SHORT).show()
        }

        if(flagE || flagP)
            Toast.makeText(requireContext(),"başarılı bir şekilde güncellendi",Toast.LENGTH_SHORT).show()

    }
}