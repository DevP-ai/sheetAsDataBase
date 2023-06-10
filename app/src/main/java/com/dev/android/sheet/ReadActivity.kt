package com.dev.android.sheet

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ReadActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var  adapter: ReadAdapter
   private lateinit var dialog: Dialog
   private lateinit var detailsList:ArrayList<EmpDetails>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        swipeRefreshLayout=findViewById(R.id.container)

        dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_bar)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        title="Employee Details"

        recyclerView=findViewById(R.id.recyclerView)
        layoutManager=LinearLayoutManager(this)

        detailsList= arrayListOf()

        adapter=ReadAdapter(this,detailsList)

        val queue=Volley.newRequestQueue(this)
        var url = "https://script.google.com/macros/s/AKfycbwrfU5PXJIJY2YTx8vMPf8waHgrYoO4BKr3WuV6YR2ybOMprNqVT8-En3_HcBvN95lx/exec?"
        url += "action=get"

        val jsonObjectRequest=object :JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                dialog.dismiss()
                val data=it.getJSONArray("items")
                for(i in 0 until(data.length())){
                    val empJSONObject=data.getJSONObject(i)
                    val empObject=EmpDetails(
                        empJSONObject.getString("date"),
                        empJSONObject.getString("adhar"),
                        empJSONObject.getString("salary"),
                        empJSONObject.getString("position"),
                        empJSONObject.getString("amount")
                    )
                    detailsList.add(empObject)
                }
                adapter=ReadAdapter(this,detailsList)
                recyclerView.adapter=adapter
                recyclerView.layoutManager=layoutManager
            },
            Response.ErrorListener {
                dialog.dismiss()
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)

        adapter.notifyDataSetChanged()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing=false

            Collections.shuffle(detailsList, Random(System.currentTimeMillis()))

            adapter.notifyDataSetChanged()
        }
    }

}