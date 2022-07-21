package com.example.android_graphql_example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_graphql_example.FindCountriesOfAContinentQuery
import com.example.android_graphql_example.databinding.ItemViewBinding

class CountryAdapter: RecyclerView.Adapter<CountryAdapter.VH>() {

    private val diff = AsyncListDiffer(this, ITEM_DIFF)

    companion object{
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<FindCountriesOfAContinentQuery.Country>(){
            override fun areItemsTheSame(
                oldItem: FindCountriesOfAContinentQuery.Country,
                newItem: FindCountriesOfAContinentQuery.Country
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: FindCountriesOfAContinentQuery.Country,
                newItem: FindCountriesOfAContinentQuery.Country
            ): Boolean {
                return false
            }

        }
    }

    inner class VH(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            val country = diff.currentList[adapterPosition]
            with(binding){
                tvCountryName.text = country.name.toString()
                tvCurrency.text = country.currency.toString()
                tvPhone.text = country.phone.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = diff.currentList.size
    fun submitData(list: List<FindCountriesOfAContinentQuery.Country>){
        diff.submitList(list)
    }
}