package com.example.mediaplayer.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.databinding.GridViewItemBinding

class PlayListAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Result, PlayListAdapter.ResultViewHolder>(DiffCalback) {

    companion object DiffCalback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId == newItem.trackId
        }

    }

    class ResultViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ResultViewHolder {
        return ResultViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(result)
        }
        holder.bind(result)
    }

    class OnClickListener(val clickListener: (marsProperty: Result) -> Unit) {
        fun onClick(marsProperty: Result) = clickListener(marsProperty)
    }
}