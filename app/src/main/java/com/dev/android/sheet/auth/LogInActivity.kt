package com.dev.android.sheet.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.android.sheet.R
import com.dev.android.sheet.admin.OptionsActivity
import com.dev.android.sheet.databinding.ActivityLogInBinding
import com.dev.android.sheet.employee.EmpDataActivity
import com.dev.android.sheet.subadmin.ChooseActivity
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loading:CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading=findViewById(R.id.btnLogin)

        firebaseAuth=FirebaseAuth.getInstance()

        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
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
        loading.startAnimation()
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
                                   loading.revertAnimation()
                                   startActivity(Intent(this@LogInActivity, OptionsActivity::class.java))
                                   finish()
                               }
                           }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            loading.revertAnimation()
                          Toast.makeText(this@LogInActivity,error.message,Toast.LENGTH_SHORT).show()
                        }

                    })

                    val employeeDataBase=FirebaseDatabase.getInstance().getReference("Employee")
                    employeeDataBase.addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(empSnap in snapshot.children){
                                val empId=empSnap.child("userId").value.toString()
                                if(empId==currentUserId){
                                    loading.revertAnimation()
                                    startActivity(Intent(this@LogInActivity,EmpDataActivity::class.java))
                                    finish()
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            loading.revertAnimation()
                           Toast.makeText(this@LogInActivity,error.message,Toast.LENGTH_SHORT).show()
                         }

                    })

                    val subAdminDataBase=FirebaseDatabase.getInstance().getReference("SubAdmin")
                    subAdminDataBase.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(subAdminSnap in snapshot.children){
                                val subAdminId=subAdminSnap.child("userId").value.toString()
                                if(subAdminId==currentUserId){
                                    loading.revertAnimation()
                                    startActivity(Intent(this@LogInActivity,ChooseActivity::class.java))
                                    finish()
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            loading.revertAnimation()
                            Toast.makeText(this@LogInActivity,error.message,Toast.LENGTH_SHORT).show()
                        }

                    })
                }else{
                    loading.revertAnimation()
                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

}