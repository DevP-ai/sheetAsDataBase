package com.dev.android.sheet.subadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.dev.android.sheet.R
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.databinding.ActivityChooseBinding
import com.google.firebase.auth.FirebaseAuth

class ChooseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        this.supportActionBar!!.displayOptions=ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar!!.setDisplayShowCustomEnabled(true)
//        supportActionBar!!.setCustomView(R.layout.custom_bar)
//
//        var logout=supportActionBar!!.customView.findViewById<ImageView>(R.id.imgLogout)
//        logout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            startActivity(Intent(this,LogInActivity::class.java))
//            this.finish()
//            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
//        }

        val subNavHost=supportFragmentManager.findFragmentById(R.id.SubAdminFragmentContainerView)!!.findNavController()

        val popupMenu=PopupMenu(this,null)
        popupMenu.inflate(R.menu.sub_menu)
        binding.subAdminBottomBar.setupWithNavController(popupMenu.menu,subNavHost)


        subNavHost.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                 title=when(destination.id){
                     R.id.subAdminProfileFragment ->"Account"

                     else ->"Home"
                 }
            }

        })

//        binding.SubAdminReadData.setOnClickListener {
//            startActivity(Intent(this,ReadDataActivity::class.java))
//        }
//
//        binding.SubAdminEnterData.setOnClickListener {
//            startActivity(Intent(this,EnterDataActivity::class.java))
//        }

    }
}