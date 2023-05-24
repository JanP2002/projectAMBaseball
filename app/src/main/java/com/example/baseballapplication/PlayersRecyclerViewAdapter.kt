package com.example.baseballapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

internal class PlayersRecyclerViewAdapter(context: Context, playersModel: ArrayList<PlayersModel>,
recyclerViewIf: RecyclerViewInterface) :
    RecyclerView.Adapter<PlayersRecyclerViewAdapter.MyViewHolder>() {
    var context: Context
    var playersModel: ArrayList<PlayersModel>
    var recyclerViewIf: RecyclerViewInterface

    init {
        this.context = context
        this.playersModel = playersModel
        this.recyclerViewIf = recyclerViewIf
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_player, parent,false)
        return MyViewHolder(view, recyclerViewIf)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val imageUrl = playersModel[position].image

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.player)  // Placeholder image
            .error(R.drawable.error)  // Error image
            .fit()
            .into(holder.imageView)

        holder.tvName.text = playersModel[position].nameAndNumber
        holder.tvStat1.text = playersModel[position].stat1.toString()+" G"
        holder.tvStat2.text = playersModel[position].stat2.toString()+" PA"
        holder.tvStat3.text = playersModel[position].stat3.toString()+" RBI"
        holder.tvStat4.text = playersModel[position].stat4.toString()+" OPS"
        //holder.imageView.setImageResource(playersModel[position].image)
    }

    override fun getItemCount(): Int {
        return playersModel.size
    }

    class MyViewHolder(itemView: View, recyclerViewIf: RecyclerViewInterface)
        : RecyclerView.ViewHolder(itemView) {
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