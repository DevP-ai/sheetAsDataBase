package com.dev.android.sheet.employee

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dev.android.sheet.R
import com.dev.android.sheet.adapter.ReadAdapter
import com.dev.android.sheet.auth.LogInActivity
import com.dev.android.sheet.model.EmpDetails
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class EmpDataActivity : AppCompatActivity() {
    private lateinit var empRecyclerView: RecyclerView
    private lateinit var adapter: ReadAdapter
    private lateinit var detailsList:ArrayList<EmpDetails>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_data)

        this.supportActionBar!!.displayOptions=ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_bar)

        var logout=supportActionBar!!.customView.findViewById<ImageView>(R.id.imgLogout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,LogInActivity::class.java))
            this.finish()
            Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
        }


        dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()


        empRecyclerView=findViewById(R.id.empRecycler)
        detailsList= arrayListOf()
        adapter=ReadAdapter(this,detailsList)
        layoutManager= LinearLayoutManager(this)

        val queue=Volley.newRequestQueue(this)
        var url = "https://script.google.com/macros/s/AKfycbwrfU5PXJIJY2YTx8vMPf8waHgrYoO4BKr3WuV6YR2ybOMprNqVT8-En3_HcBvN95lx/exec?"
        url += "action=get"

        val jsonObjectRequest=object :JsonObjectRequest(
            Request.Method.GET,url,null,Response.Listener {
                dialog.dismiss()
                val data=it.getJSONArray("items")
                for (i in 0 until data.length()){
                    val empJsonObject=data.getJSONObject(i)

                    val empObject=EmpDetails(
                        empJsonObject.getString("date"),
                        empJsonObject.getString("adhar"),
                        empJsonObject.getString("salary"),
                        empJsonObject.getString("position"),
                        empJsonObject.getString("amount")
                    )
                    detailsList.add(empObject)
                }
                adapter= ReadAdapter(this,detailsList)
                empRecyclerView.adapter=adapter
                empRecyclerView.layoutManager=layoutManager
            },
            Response.ErrorListener {
                dialog.dismiss()
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }

        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)
    }
}
