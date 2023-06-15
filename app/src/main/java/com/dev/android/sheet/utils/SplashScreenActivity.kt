package com.dev.android.sheet.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.dev.android.sheet.R
import com.dev.android.sheet.admin.OptionsActivity
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.employee.EmpDataActivity
import com.dev.android.sheet.subadmin.ChooseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar!!.hide()

        val currentUser = FirebaseAuth.getInstance().currentUser
        Handler(Looper.getMainLooper()).postDelayed({
            if(currentUser != null){
                // just current user id who wants to log in
                val  currentUserId = currentUser.uid
                //initializing existedClientId and existedContractorId
                var existedAdminId = "random"
                var existedSubAdminId = "random"
                var existedEmpId = "random"
                val adminDatabaseReference = FirebaseDatabase.getInstance().getReference("Admin")
                adminDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(childrenSnapshot in snapshot.children){
                            val adminIds = childrenSnapshot.child("userId").value.toString()
                            if(adminIds== currentUserId){
                                existedAdminId=adminIds
                                break
                            }
                        }
                        if(existedAdminId== currentUserId) {
                            val intent = Intent(this@SplashScreenActivity,OptionsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onCancelled(error: DatabaseError){
                        Toast.makeText(this@SplashScreenActivity, error.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                })

                val subAdminDatabaseReference = FirebaseDatabase.getInstance().getReference("SubAdmin")
                subAdminDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childSnapshot in dataSnapshot.children) {
                            val subAdminId = childSnapshot.child("userId").value.toString()
                            if(subAdminId  == currentUserId){
                                existedSubAdminId=subAdminId
                                break
                            }
                        }
                        if( existedSubAdminId== currentUserId){
                            val intent = Intent(this@SplashScreenActivity, ChooseActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@SplashScreenActivity, databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })

                val empDatabaseReference = FirebaseDatabase.getInstance().getReference("Employee")
                empDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childSnapshot in dataSnapshot.children) {
                            val empId = childSnapshot.child("userId").value.toString()
                            if(empId == currentUserId){
                                existedEmpId=empId
                                break
                            }
                        }
                        if( existedEmpId== currentUserId){
                            val intent = Intent(this@SplashScreenActivity, ChooseActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@SplashScreenActivity, databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })


            }
            else{
                startActivity(Intent(this,LogInActivity::class.java))
                finish()
            }
        },1000)

    }


    }
