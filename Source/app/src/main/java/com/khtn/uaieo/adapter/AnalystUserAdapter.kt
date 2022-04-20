package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Tip
import com.khtn.uaieo.model.itemAnalystUser

class AnalystUserAdapter(private val userList: ArrayList<itemAnalystUser>):
    RecyclerView.Adapter<AnalystUserAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_analyst_user, parent, false);
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = userList[position]

        holder.overall.text=dictionary[currentItem.overall].toString()
        holder.email.text=currentItem.email
        holder.part1.text=currentItem.part1.toString()
        holder.part2.text=currentItem.part2.toString()
        holder.part3.text=currentItem.part3.toString()
        holder.part4.text=currentItem.part4.toString()
        holder.part5.text=currentItem.part5.toString()
        holder.part6.text=currentItem.part6.toString()
        holder.part7.text=currentItem.part7.toString()
        holder.ranking.text=(position+1).toString()
    }

    override fun getItemCount(): Int {
        return userList.size;
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val email: TextView = itemView.findViewById(R.id.emailUserTV)
        val overall: TextView = itemView.findViewById(R.id.overallTV)
        val part1: TextView = itemView.findViewById(R.id.p1TV)
        val part2: TextView = itemView.findViewById(R.id.p2TV)
        val part3: TextView = itemView.findViewById(R.id.p3TV)
        val part4: TextView = itemView.findViewById(R.id.p4TV)
        val part5: TextView = itemView.findViewById(R.id.p5TV)
        val part6: TextView = itemView.findViewById(R.id.p6TV)
        val part7: TextView = itemView.findViewById(R.id.p7TV)
        val ranking: TextView=itemView.findViewById(R.id.rankingTV)

    }
    val dictionary = mapOf(
        0 to 0,
        1 to 10,
        2 to 10,
        3 to 10,
        4 to 10,
        5 to 10,
        6 to 10,
        7 to 15,
        8 to 20,
        9 to 25,
        10 to 35,
        11 to 45,
        12 to 55,
        13 to 35,
        14 to 75,
        15 to 85,
        16 to 95,
        17 to 105,
        18 to 115,
        19 to 125,
        20 to 135,
        21 to 145,
        22 to 155,
        23 to 165,
        24 to 175,
        25 to 190,
        26 to 200,
        27 to 210,
        28 to 225,
        29 to 235,
        30 to 245,
        31 to 260,
        32 to 270,
        33 to 280,
        34 to 290,
        35 to 300,
        36 to 310,
        37 to 320,
        38 to 330,
        39 to 350,
        40 to 360,
        41 to 370,
        42 to 380,
        43 to 395,
        44 to 410,
        45 to 425,
        46 to 435,
        47 to 450,
        48 to 460,
        49 to 470,
        50 to 480,
        51 to 490,
        52 to 505,
        53 to 515,
        54 to 530,
        55 to 545,
        56 to 555,
        57 to 565,
        58 to 585,
        59 to 590,
        60 to 600,
        61 to 610,
        62 to 620,
        63 to 630,
        64 to 640,
        65 to 650,
        66 to 660,
        67 to 670,
        68 to 680,
        69 to 690,
        70 to 700,
        71 to 710,
        72 to 720,
        73 to 730,
        74 to 740,
        75 to 750,
        76 to 760,
        77 to 770,
        78 to 780,
        79 to 790,
        80 to 800,
        81 to 810,
        82 to 820,
        83 to 830,
        84 to 840,
        85 to 850,
        86 to 860,
        87 to 870,
        88 to 885,
        89 to 900,
        90 to 935,
        91 to 950,
        92 to 960,
        93 to 980,
        94 to 975,
        95 to 980,
        96 to 985,
        97 to 990,
        98 to 990,
        99 to 990,
        100 to 990,
    )
}