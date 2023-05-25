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
import com.squareup.picasso.Picasso

internal class TeamsRecyclerViewAdapter(context : Context, teamsModel:ArrayList<TeamModel>, recyclerViewInterface: RecyclerViewInterface) :
 RecyclerView.Adapter<TeamsRecyclerViewAdapter.MyViewHolder>(){
    var context: Context
    var teamsModel: ArrayList<TeamModel>
    var recyclerViewIf: RecyclerViewInterface

    init {
        this.context = context
        this.teamsModel = teamsModel
        this.recyclerViewIf = recyclerViewInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_team, parent,false)
        return MyViewHolder(view, recyclerViewIf)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.tvName.text = teamsModel[position].team

        val imageUrl = teamsModel[position].imgLink
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.player)  // Placeholder image
            .error(R.drawable.error)  // Error image
            .fit()
            .into(holder.imageView)
        //holder.imageView.setImageResource(playersModel[position].image)
    }

    override fun getItemCount(): Int {
        return teamsModel.size
    }

    class MyViewHolder(itemView: View, recyclerViewIf: RecyclerViewInterface)
        : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var tvName: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            tvName = itemView.findViewById(R.id.nameAndNumber)

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