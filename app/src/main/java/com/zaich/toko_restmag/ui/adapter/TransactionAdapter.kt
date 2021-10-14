package com.zaich.toko_restmag.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaich.toko_restmag.databinding.ItemTransactionBinding
import com.zaich.toko_restmag.databinding.ItemUserBinding
import com.zaich.toko_restmag.model.TransactionData
import com.zaich.toko_restmag.model.Users

class TransactionAdapter(private val list:ArrayList<TransactionData>, val context: Context): RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>()  {
    inner class TransactionViewHolder(val binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionData: TransactionData){
            with(binding){
                tvId.text = transactionData.id.toString()
                tvTotal.text = transactionData.total.toString()
                tvDate.text = transactionData.deposite_date
                tvTime.text = transactionData.deposite_time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setUser(user: ArrayList<TransactionData>) {
        with(list) {
            clear()
            addAll(user)
        }
        notifyDataSetChanged()
    }
}