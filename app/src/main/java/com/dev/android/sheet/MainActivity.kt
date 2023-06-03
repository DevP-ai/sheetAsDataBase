package com.dev.android.sheet

import andreasagap.loadingbutton.ButtonLoading
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dev.android.sheet.databinding.ActivityMainBinding
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loading:CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="Data Entry"

        loading=findViewById(R.id.btnSave);

        loading.revertAnimation()
        binding.btnSave.setOnClickListener {
            loading.startAnimation()
            var date=binding.dateOfJoin.text.toString()
            var adhar=binding.aadhaarNumber.text.toString()
            var salary=binding.salary.text.toString()
            var position=binding.position.text.toString()
            var amount=binding.advanceAmount.text.toString()

            validateData(date,adhar,salary,position,amount)

        }


    }

    private fun validateData(date: String, adhar: String, salary: String, position: String, amount: String) {
        loading.revertAnimation()
        if(date.isBlank()){
            binding.dateOfJoin.error="Date is required"
        }else if(adhar.isBlank()){
            binding.aadhaarNumber.error="Aadhaar number is required"
        }else if(salary.isBlank()){
            binding.salary.error="Salary is required"
        }else if(position.isBlank()){
            binding.position.error="Position is required"
        }else if(amount.isBlank()){
            binding.advanceAmount.error="Advance amount is required"
        }
        else{
            saveData(date,adhar,salary,position,amount)

            binding.dateOfJoin.setText("")
            binding.aadhaarNumber.setText("")
            binding.salary.setText("")
            binding.position.setText("")
            binding.advanceAmount.setText("")
        }
    }


    private fun saveData(date: String, adhar: String,salary: String,position:String,amount:String) {
        var url = "https://script.google.com/macros/s/AKfycbwrfU5PXJIJY2YTx8vMPf8waHgrYoO4BKr3WuV6YR2ybOMprNqVT8-En3_HcBvN95lx/exec?"
        url += "action=create&date=$date&adhar=$adhar&salary=$salary&position=$position&amount=$amount"
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {
                Toast.makeText(this@MainActivity,it.toString(), Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)

    }

}

