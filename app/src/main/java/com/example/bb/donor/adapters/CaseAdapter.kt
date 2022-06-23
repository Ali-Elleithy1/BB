package com.example.bb.donor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bb.R
import com.example.bb.database.Case
import com.example.bb.databinding.CaseListItemBinding

class CaseAdapter(private val clickListener: CaseClickListener): ListAdapter<Case, CaseAdapter.ViewHolder>(CaseAdapter.CaseDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, holder,clickListener)
    }

    class ViewHolder private constructor(val binding: CaseListItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        private val caseNum:TextView = binding.listCaseNumTV

        fun bind(item: Case, holder: CaseAdapter.ViewHolder, clickListener: CaseClickListener) {
            caseNum.text = item.id.toString()
            binding.case1 = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent:ViewGroup):ViewHolder
            {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: CaseListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.case_list_item,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    class CaseDiffCallback: DiffUtil.ItemCallback<Case>() {
        override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
            return oldItem == newItem
        }
    }
}

class CaseClickListener(val clickListener: (caseId:Long) -> Unit){
    fun onClick(case: Case) = clickListener(case.id)
}