package com.zaich.toko_restmag.pegawai.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaich.toko_restmag.databinding.RowCartBinding
import com.zaich.toko_restmag.pegawai.model.CartModel

class CartAdapter(val context: Context): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var arrayList = ArrayList<CartModel>()

    fun setData(arrayList: ArrayList<CartModel>){
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowCartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(model: CartModel, position: Int){
            var no: Int = position + 1
            binding.tvNo.text = no.toString()
            binding.tvProduct.text = model.productName
            binding.tvPrice.text = model.priceOfProduct.toString()
            binding.tvTotal.text = model.totalProduct.toString()
            binding.tvDaydate.text = model.daydate
            binding.tvDaytime.text = "${model.daytime}. "
            val gTotal: Int = (model.priceOfProduct) * (model.totalProduct)
            binding.tvGTotal.text = gTotal.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val PcartRowView = RowCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(PcartRowView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //        val model: CartModel = arrayList[position]
        holder.bindItem(arrayList[position], position)
        val item = arrayList[position]

        val name = item.productName
        val price = item.priceOfProduct
        val quantity = item.totalProduct
        val daydate = item.daydate
        val daytime = item.daytime

//        Intent(context, StrukActivity::class.java).also {
//            it.putExtra("CART_NAME", name)
//            it.putExtra("CART_PRICE", price)
//            it.putExtra("CART_QTY", quantity)
//            it.putExtra("CART_DAYDATE", daydate)
//            it.putExtra("CART_DAYTIME", daytime)
//        }

//        val price= model.priceOfProduct.toString()
//        val daydate = model.daydate
//        val daytime = model.daytime
//        Intent(context, StrukActivity::class.java).also {
////            it.putExtra("ORDER_ID", idProduct)
//            it.putExtra("ORDER_PRICE", price)
////            it.putExtra("ORDER_QTY", qty)
//            it.putExtra("ORDER_DAYDATE", daydate)
//            it.putExtra("ORDER_DAYTIME", daytime)
//        }
    }

}
