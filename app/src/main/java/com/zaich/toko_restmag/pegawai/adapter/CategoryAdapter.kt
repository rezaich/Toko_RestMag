package com.zaich.toko_restmag.pegawai.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zaich.toko_restmag.databinding.RowItemCategoryBinding
import com.zaich.toko_restmag.pegawai.activity.ProductActivity
import com.zaich.toko_restmag.pegawai.model.CategoryModel

class CategoryAdapter(val context: Context): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(),
    Filterable {
    var arrayList = ArrayList<CategoryModel>()
    var CategoryListFilter = ArrayList<CategoryModel>()

    fun setData(arrayList: ArrayList<CategoryModel>) {
        this.arrayList = arrayList
        this.CategoryListFilter = arrayList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(model: CategoryModel) {
            binding.categoryName.text = "${model.name}"
//            //Menampilkan gambar yang didapatkan dari url
//            val url: URL = URL("http://10.0.2.2:8000" + model.image_link)
//            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            binding.imgCategory.setImageBitmap(bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val PcategoryItemView =
            RowItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(PcategoryItemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position])
        holder.itemView.setOnClickListener() {
            val model = arrayList[position]

            var categoryId: Int = model.id
            var categoryName = model.name

            Intent(context, ProductActivity::class.java).also { intent
                ->
                intent.putExtra("categoryId", categoryId)
                intent.putExtra("categoryName", categoryName)
                context.startActivity(intent)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?):
                    FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.length < 0) {
                    filterResults.count = CategoryListFilter.size
                    filterResults.values = CategoryListFilter
                } else {
                    var searchChr = constraint.toString()
                    val categorySearch = ArrayList<CategoryModel>()
                    for (item in CategoryListFilter) {
                        if (item.name.toLowerCase()
                                .contains(searchChr)) {
                            categorySearch.add(item)
                        }
                    }
                    filterResults.count = categorySearch.size
                    filterResults.values = categorySearch
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?,
                                        filterResults: FilterResults?) {
                arrayList = filterResults!!.values as ArrayList<CategoryModel>
                notifyDataSetChanged()
            }
        }
    }
}

