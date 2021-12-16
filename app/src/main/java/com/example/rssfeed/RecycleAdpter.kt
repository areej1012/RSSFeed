package com.example.rssfeed

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.databinding.CardViewBinding

class RecycleAdpter(var listQ :List<Questions>, var context : Context) : RecyclerView.Adapter<RecycleAdpter.ItemHolder>(){
    class ItemHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val list = listQ
        holder.binding.apply {
            tvQue.text = list[position].title
            tvAuther.text = list[position].author
        }
        holder.itemView.setOnClickListener {
            showAlert(list[position].summary)
        }
    }

    private fun showAlert(summary : String) {
        val dial = AlertDialog.Builder(context)
        dial.setMessage("Det")
            .setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.cancel()  })
        val tvSummary = TextView(context)
        tvSummary.text = summary
        val alert = dial.create()
        alert.setView(tvSummary)
        alert.show()


    }

    override fun getItemCount(): Int = listQ.size
}