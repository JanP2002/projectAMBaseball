package com.example.baseballapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView


internal class StadiumRecyclerViewAdapter(context : Context, stadiumModel:ArrayList<StadiumModel>, recyclerViewInterface: RecyclerViewInterface) :
    RecyclerView.Adapter<StadiumRecyclerViewAdapter.MyViewHolder>(){
    var context: Context
    var stadiumModel: ArrayList<StadiumModel>
    var recyclerViewIf: RecyclerViewInterface

    init {
        this.context = context
        this.stadiumModel = stadiumModel
        this.recyclerViewIf = recyclerViewInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_stadium, parent,false)
        return MyViewHolder(view, recyclerViewIf)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = stadiumModel[position].name
    }



    override fun getItemCount(): Int {
        return stadiumModel.size
    }

    class MyViewHolder(itemView: View, recyclerViewIf: RecyclerViewInterface)
        : RecyclerView.ViewHolder(itemView) {

        var tvName: TextView

        init {

            tvName = itemView.findViewById(R.id.stadiumName)

            itemView.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                {
                    recyclerViewIf.onItemClick(position)
                }
            }
        }
    }
}