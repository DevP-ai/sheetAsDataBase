package com.dev.android.sheet.auth

//import android.content.SharedPreferences
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.dev.android.sheet.R
import com.dev.android.sheet.admin.OptionsActivity
import com.dev.android.sheet.databinding.ActivitySignUpBinding
import com.dev.android.sheet.employee.EmpDataActivity
import com.dev.android.sheet.model.UserDataModel
import com.dev.android.sheet.subadmin.ChooseActivity
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
//    private late init var sharedPreferences: SharedPreferences
    private lateinit var databaseReference: DatabaseReference
//    private var userPreferences:String?=null
    private var types:String?=null
    private lateinit var btnDialog:CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnDialog=findViewById(R.id.btnRegister)

        supportActionBar!!.hide()
        firebaseAuth=FirebaseAuth.getInstance()



        binding.apply {
            userRadioGroup.setOnCheckedChangeListener {_, checkedId ->
//                storingUserType(checkedId)
                types=findViewById<RadioButton>(checkedId).text.toString()
            }
            btnRegister.setOnClickListener {
                btnDialog.startAnimation()
                createNewUser()
            }
            txtLogin.setOnClickListener { startActivity(Intent(this@SignUpActivity,LogInActivity::class.java)) }
        }

    }

    private fun createNewUser() {
        val name=binding.userName.text!!.trim().toString()
        val email=binding.userEmail.text!!.trim().toString()
        val password=binding.userPassword.text!!.trim().toString()
        val confirmPassword=binding.userConfirmPassword.text!!.trim().toString()

//        sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE)
//        userPreferences=sharedPreferences.getString("userPreference","")

        if(types.equals("Admin")){
             databaseReference=FirebaseDatabase.getInstance().getReference("Admin")
             userValidation(name,email,password,confirmPassword)
            btnDialog.revertAnimation()
        }else if(types.equals("SubAdmin")){
            databaseReference=FirebaseDatabase.getInstance().getReference("SubAdmin")
            userValidation(name,email,password,confirmPassword)
            btnDialog.revertAnimation()
        }else if(types.equals("Employee")){
            databaseReference=FirebaseDatabase.getInstance().getReference("Employee")
            userValidation(name,email,password,confirmPassword)
            btnDialog.revertAnimation()
        }
    }

    private fun userValidation(name: String, email: String, password: String, confirmPassword: String) {
         if(name.isBlank()){
             binding.userName.error="Name required"
         }else if(email.isBlank()){
             binding.userEmail.error="Email Required"
         }else if(password.isBlank()){
             binding.userPassword.error="Password required"
         }else if(confirmPassword.isBlank()){
             binding.userConfirmPassword.error="Confirm your password"
         }else if(password!=confirmPassword){
             Toast.makeText(this,"Password are not match",Toast.LENGTH_SHORT).show()
         }else{
             firebaseAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener { task->
                     if(task.isSuccessful){
                         btnDialog.revertAnimation()
                         firebaseAuth.currentUser!!.sendEmailVerification()
                             .addOnSuccessListener {
                                 Toast.makeText(this,"Check email inbox or sperm folder",Toast.LENGTH_SHORT).show()
                                 val uid=task.result.user!!.uid
                                 val userData=UserDataModel(name,email,password,uid)
                                 databaseReference.child(firebaseAuth.currentUser!!.uid).setValue(userData)
                             }.addOnFailureListener {
                                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                             }
                     }else{
                         btnDialog.revertAnimation()
                         Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                     }
                 }
         }
    }

//    private fun storingUserType(checkedId: Int) {
//        val radioButton=findViewById<RadioButton>(checkedId)
//        val sharedPref=getSharedPreferences("MyPref", MODE_PRIVATE)
//        val editor=sharedPref.edit()
//
//        editor.putString("userPreference",radioButton.text.toString())
//        editor.apply()
//    }
}