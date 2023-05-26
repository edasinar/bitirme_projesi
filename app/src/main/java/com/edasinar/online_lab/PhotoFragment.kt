package com.edasinar.online_lab

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.edasinar.model.PhotoInfo
import com.edasinar.online_lab.databinding.FragmentPhotoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class PhotoFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var binding: FragmentPhotoBinding

    private lateinit var imageUri: Uri
    private var downloadUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage
        binding.choosePhotoButton.setOnClickListener {
            getPhotoFromGallery()
        }
        binding.savePhotoButton.setOnClickListener {
            uploadDataToFirestore()
            Toast.makeText(requireContext(),"fotoğraf başarılı bir şekilde kaydedilmiştir.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadDataToFirestore() {

        val photoInfo = PhotoInfo(auth.currentUser!!.email!!, downloadUrl)
        firestore.collection("photo_info").document(auth.currentUser!!.email!!).set(photoInfo)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.profilePhotoChange.setImageURI(imageUri)
            binding.profilePhotoChange.scaleType = ImageView.ScaleType.CENTER_CROP
            val ref = storage.reference
            val imageName = auth.currentUser?.uid
            val imageRef = ref.child("profilePhoto").child("${imageName}.jpeg")
            imageRef.putFile(imageUri).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "fotoğraf galeriye yüklenemedi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

}