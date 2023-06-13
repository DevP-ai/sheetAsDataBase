package com.dev.android.sheet.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.android.sheet.admin.OptionsActivity
import com.dev.android.sheet.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener {
            val email=binding.loginEmail.text!!.trim().toString()
            val password=binding.loginPassword.text!!.trim().toString()
            validation(email,password)
        }
    }

    private fun validation(email: String, password: String) {
       if(email.isBlank()){
           binding.loginEmail.error="Please fill email"
       }else if(password.isBlank()){
           binding.loginPassword.error="Enter password"
       }else{
           loggingUser(email,password)
       }
    }
    private fun loggingUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Task Successful",Toast.LENGTH_SHORT).show()
                    val currentUserId=firebaseAuth.currentUser!!.uid

                    val adminDatabase=FirebaseDatabase.getInstance().getReference("Admin")
                    adminDatabase.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                           for (childrenSnap in snapshot.children){
                               val adminId=childrenSnap.child("userId").value.toString()
                               if(adminId==currentUserId){
                                   startActivity(Intent(this@LogInActivity, OptionsActivity::class.java))
                                   finish()
                               }
                           }
                        }

                        override fun onCancelled(error: DatabaseError) {
                          Toast.makeText(this@LogInActivity,error.message,Toast.LENGTH_SHORT).show()
                        }

                    })
                }else{
                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

}