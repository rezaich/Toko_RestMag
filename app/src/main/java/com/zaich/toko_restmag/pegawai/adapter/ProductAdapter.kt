package com.zaich.toko_restmag.pegawai.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zaich.toko_restmag.databinding.RowItemProductBinding
import com.zaich.toko_restmag.pegawai.activity.OrderActivity
import com.zaich.toko_restmag.pegawai.model.ProductIntent
import com.zaich.toko_restmag.pegawai.model.ProductModel

class ProductAdapter(val context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>(),
    Filterable {
    var arrayList = ArrayList<ProductModel>()
    var ProductListFilter = ArrayList<ProductModel>()

    fun setData(arrayList: ArrayList<ProductModel>){
        this.arrayList = arrayList
        this.ProductListFilter = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(model: ProductModel){
            binding.productName.text = "${model.name}"
            binding.productDesc.text = model.description
            binding.priceProduct.text = model.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val PproductItemView = RowItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(PproductItemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])
        holder.itemView.setOnClickListener {
            val model: ProductModel = arrayList[position]

            var gId: Int = model.id
            var gProduct: String = model.name
            var gDesc: String = model.description
            var gPrice: Int = model.price

            /** PRODUCT INTENT TO ORDER ACTIVITY*/
            val productIntent = ProductIntent(gId, gProduct, gDesc, gPrice)
            Intent(context, OrderActivity::class.java).also { intent
                ->
                intent.putExtra("EXTRA_PRODUCT", productIntent)
                context.startActivities(arrayOf(intent))
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.length < 0) {
                    filterResults.count = ProductListFilter.size
                    filterResults.values = ProductListFilter
                } else {
                    var searchChr = constraint.toString()
                    val productSearch = ArrayList<ProductModel>()
                    for (item in ProductListFilter) {
                        if (item.name.toLowerCase()
                                .contains(searchChr) || item.description.toLowerCase().contains(searchChr)) {
                            productSearch.add(item)
                        }
                    }
                    filterResults.count = productSearch.size
                    filterResults.values = productSearch
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                arrayList = filterResults!!.values as ArrayList<ProductModel>
                notifyDataSetChanged()
            }
        }
    }

}
