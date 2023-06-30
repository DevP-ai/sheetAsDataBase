package com.dev.android.sheet.adminFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.dev.android.sheet.R
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.databinding.FragmentProfileBinding
import com.dev.android.sheet.model.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
//    private   var LOG_CODE=1
    private val db=Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//   private lateinit var LogInLauncher:ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(layoutInflater)

        binding.txtLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivityForResult(Intent(requireContext(),LogInActivity::class.java),LOG_CODE)

        }

//        LogInLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            if(it.resultCode==Activity.RESULT_OK){
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .remove(this)
//                    .commit()
//            }
//        }
//
//        binding.txtLogout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            LogInLauncher.launch(Intent(requireContext(),LogInActivity::class.java))
//        }


        fetchData(binding)

        return binding.root
    }

    private fun fetchData(binding: FragmentProfileBinding) {
       val userRef=db.child("Admin")
        userRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnap in snapshot.children){
                    val user=childSnap.getValue(UserDataModel::class.java)
                    val name=user?.name
                    val email=user?.email

                    binding.profileName.text=name
                    binding.profileEmail.text=email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.profileEmail.error="something wrong"
                binding.profileName.error="something wrong"
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==LOG_CODE){
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    companion object{
        private const val LOG_CODE=1001
    }

}