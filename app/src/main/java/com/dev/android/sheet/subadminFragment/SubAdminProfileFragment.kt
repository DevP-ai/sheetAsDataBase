package com.dev.android.sheet.subadminFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.android.sheet.R
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.databinding.FragmentSubAdminProfileBinding
import com.dev.android.sheet.model.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SubAdminProfileFragment : Fragment() {
   private lateinit var binding:FragmentSubAdminProfileBinding
   private val db=Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSubAdminProfileBinding.inflate(layoutInflater)


        binding.subTxtLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivityForResult(Intent(requireContext(),LogInActivity::class.java), LOG_CODE)
        }

        fetchData(binding)

        return binding.root
    }

    private fun fetchData(binding: FragmentSubAdminProfileBinding) {
        val userRef=db.child("SubAdmin")
        userRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                    val user=snap.getValue(UserDataModel::class.java)
                    val name=user?.name
                    val email=user?.email

                    binding.subProfileName.text=name
                    binding.subProfileEmail.text=email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.subProfileName.error="something wrong"
                binding.subProfileEmail.error="something wrong"
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== LOG_CODE){
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    companion object {
        private const val LOG_CODE=1002

    }
}