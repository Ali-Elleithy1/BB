package com.example.bb.donor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bb.R
import com.example.bb.database.Charity
import com.example.bb.databinding.CharityListItemBinding
import com.squareup.picasso.Picasso

class CharityAdapter(private val clickListener: CharityClickListener): ListAdapter<Charity,CharityAdapter.ViewHolder>(CharityAdapter.CharityDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, holder,clickListener)
    }



    class ViewHolder private constructor(val binding: CharityListItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        private val charityImage: ImageView = binding.charityImage
        private val charityName: TextView = binding.charityNameText
        //val res = itemView.context.resources

        fun bind(item: Charity, holder: ViewHolder, clickListener: CharityClickListener) {
            Picasso.get().load(item.image).placeholder(R.drawable.ic_launcher_background)
                .into(holder.charityImage)
            binding.charity = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: CharityListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.charity_list_item,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    class CharityDiffCallback: DiffUtil.ItemCallback<Charity>() {
        override fun areItemsTheSame(oldItem: Charity, newItem: Charity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Charity, newItem: Charity): Boolean {
            return oldItem == newItem
        }
    }
}

class CharityClickListener(val clickListener: (charityId:Long) -> Unit){
    fun onClick(charity: Charity) = clickListener(charity.id)
}
