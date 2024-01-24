package id.synrgy6team2.bookingticket.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHistoryBinding

class HistoryChildAdapter :
    ListAdapter<HistoryModel, HistoryChildAdapter.HistoryModelViewHolder>(
        HistoryModelComparator
    ) {

    private var _onClick: ((position: Int, item: HistoryModel) -> Unit)? = null

    private object HistoryModelComparator : DiffUtil.ItemCallback<HistoryModel>() {
        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryModelViewHolder {
        return HistoryModelViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class HistoryModelViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: HistoryModel) {}
    }

    fun onClick(listener: (position: Int, item: HistoryModel) -> Unit) {
        _onClick = listener
    }
}