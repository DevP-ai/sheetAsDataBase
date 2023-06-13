package com.dev.android.sheet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.android.sheet.model.EmpDetails
import com.dev.android.sheet.R

class ReadAdapter(val context: Context,val detailsList:ArrayList<EmpDetails>)
    :RecyclerView.Adapter<ReadAdapter.DetailsViewHolder>(){

    class DetailsViewHolder(view: View):RecyclerView.ViewHolder(view){
        val Txtdate:TextView=view.findViewById(R.id.Ddate)
        val Txtadhar:TextView=view.findViewById(R.id.Dadhar)
        val Txtsalary:TextView=view.findViewById(R.id.Dsalary)
        val Txtposition:TextView=view.findViewById(R.id.Dposition)
        val Txtamount:TextView=view.findViewById(R.id.Damount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
       val view=LayoutInflater.from(context).inflate(R.layout.data_design,parent,false)
       return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.Txtdate.text=detailsList[position].date
        holder.Txtadhar.text=detailsList[position].adhar
        holder.Txtsalary.text=detailsList[position].salary
        holder.Txtposition.text=detailsList[position].position
        holder.Txtamount.text=detailsList[position].amount
    }

    override fun getItemCount(): Int {
       return  detailsList.size
    }
}