package com.developer.ivan.beerapp.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.databinding.ItemBeerBinding
import com.developer.ivan.beerapp.ui.main.UIBeer


class BeerListAdapter(val onPressItem: (UIBeer, TextView) -> Unit) :
    ListAdapter<UIBeer, BeerListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<UIBeer>() {
        override fun areItemsTheSame(oldItem: UIBeer, newItem: UIBeer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UIBeer, newItem: UIBeer): Boolean =
            oldItem == newItem

    }) {

    private lateinit var binding: ItemBeerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(getItem(position))

    }

    inner class ViewHolder(private val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: UIBeer) = with(binding) {
            txtName.apply {
                text = item.name
                transitionName = item.id.toString()
            }

            txtTagLine.text = item.tagline

            cardItem.setCardBackgroundColor(
                if (item.isAvailable)
                    itemView.context.getColor(R.color.greenLight)
                else
                    itemView.context.getColor(R.color.grayLight)
            )

            itemView.setOnClickListener {
                onPressItem(item, txtName)
            }
        }

    }


}