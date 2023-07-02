package com.dev.android.sheet.subadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dev.android.sheet.R
import com.dev.android.sheet.adapter.ReadAdapter
import com.dev.android.sheet.model.EmpDetails

class ReadDataActivity : AppCompatActivity() {
    private lateinit var subAdminRecyclerView: RecyclerView
    private lateinit var adapter: ReadAdapter
    private lateinit var details:ArrayList<EmpDetails>
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)

        title="User Data"

//        subAdminRecyclerView=findViewById(R.id.SubAdminRecyclerView)
//        layoutManager= LinearLayoutManager(this)
//        details= arrayListOf()
//        adapter= ReadAdapter(this,details)

        val queue= Volley.newRequestQueue(this)
        var url = "https://script.google.com/macros/s/AKfycbwrfU5PXJIJY2YTx8vMPf8waHgrYoO4BKr3WuV6YR2ybOMprNqVT8-En3_HcBvN95lx/exec?"
        url += "action=get"

        val jsonObjectRequest=object:JsonObjectRequest(
            Method.GET,url,null, Response.Listener {
                val data=it.getJSONArray("items")
                for (i in 0 until data.length()){
                    var empJsonObject=data.getJSONObject(i)

                    var empObject=EmpDetails(
                        empJsonObject.getString("date"),
                        empJsonObject.getString("adhar"),
                        empJsonObject.getString("salary"),
                        empJsonObject.getString("position"),
                        empJsonObject.getString("amount")
                    )

                    details.add(empObject)
                }

                adapter= ReadAdapter(this,details)
                subAdminRecyclerView.adapter=adapter
                subAdminRecyclerView.layoutManager=layoutManager

            },Response.ErrorListener {
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