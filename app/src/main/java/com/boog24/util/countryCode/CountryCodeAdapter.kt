package com.boog24.util.countryCode

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.boog24.R
import com.boog24.databinding.ListItemCountryCodeBinding

class CountryCodeAdapter(
        private val countryList: ArrayList<Country>,
        val onClicked: (country: Country, position: Int) -> Unit
) : RecyclerView.Adapter<CountryCodeAdapter.ViewHolder>(), Filterable {

    var filteredList: ArrayList<Country> = countryList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context), R.layout.list_item_country_code, parent, false
                    )
            )

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    inner class ViewHolder(val mBinding: ListItemCountryCodeBinding) :
            RecyclerView.ViewHolder(mBinding.root) {
        fun bind(country: Country) {
            with(mBinding) {
                this.country = country
                this.clParent.setOnClickListener {
                    onClicked(country, adapterPosition)
                }
                if (country.isSelected)
                    this.clParent.setBackgroundColor(mBinding.root.context.resources.getColor(R.color.gray))
                else
                    this.clParent.setBackgroundColor(mBinding.root.context.resources.getColor(R.color.white))

            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().toUpperCase()
                filteredList = if (query.isEmpty()) {
                    countryList
                } else {
                    countryList.filter { it.countryName.toUpperCase().contains(query) } as ArrayList<Country>
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Country>
                notifyDataSetChanged()
            }
        }
    }
}