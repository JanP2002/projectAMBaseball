package com.example.baseballapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class PlayersRecyclerViewAdapter(context: Context, playersModel: ArrayList<PlayersModel>) :
    RecyclerView.Adapter<PlayersRecyclerViewAdapter.MyViewHolder>() {
    var context: Context
    var playersModel: ArrayList<PlayersModel>

    init {
        this.context = context
        this.playersModel = playersModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_player, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.setText(playersModel[position].nameAndNumber)
        holder.tvStat1.setText(playersModel[position].stat1)
        holder.tvStat2.setText(playersModel[position].stat2)
        holder.tvStat3.setText(playersModel[position].stat3)
        holder.tvStat4.setText(playersModel[position].stat4)
        //holder.imageView.setImageResource(playersModel[position].image)
    }

    override fun getItemCount(): Int {
        return playersModel.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var tvName: TextView
        var tvStat1: TextView
        var tvStat2: TextView
        var tvStat3: TextView
        var tvStat4: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            tvName = itemView.findViewById(R.id.nameAndNumber)
            tvStat1 = itemView.findViewById(R.id.stat1)
            tvStat2 = itemView.findViewById(R.id.stat2)
            tvStat3 = itemView.findViewById(R.id.stat3)
            tvStat4 = itemView.findViewById(R.id.stat4)
        }
    }
}