package com.zaich.toko_restmag.ui.adapter
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.databinding.ItemMenuBinding
import java.net.URL

class MenuAdapter(private val list:ArrayList<MenuModel>, val context: Context):RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    inner class MenuViewHolder(val binding:ItemMenuBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(menu:MenuModel){
            with(binding){
                tvName.text = menu.name
                tvPrice.text = menu.price.toString()
                tvDesc.text = menu.description
//                val url: URL = URL("http://rezaich.teknisitik.com/" + menu.image_link)
//                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//                imgPhoto.setImageBitmap(bmp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setMenu(menu: ArrayList<MenuModel>) {
        with(list) {
            clear()
            addAll(menu)
        }
        notifyDataSetChanged()
    }

}