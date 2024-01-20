package id.synrgy6team2.bookingticket.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHomeHistoryBookingBinding

class DashboardHistoryAdapter :
    ListAdapter<DashboardHistoryModel, DashboardHistoryAdapter.DashboardHistoryModelViewHolder>(
        DashboardHistoryModelComparator
    ) {

    private var _onClick: ((position: Int, item: DashboardHistoryModel) -> Unit)? = null

    private object DashboardHistoryModelComparator :
        DiffUtil.ItemCallback<DashboardHistoryModel>() {
        override fun areItemsTheSame(
            oldItem: DashboardHistoryModel,
            newItem: DashboardHistoryModel
        ): Boolean {
            return oldItem.schedule == newItem.schedule
        }

        override fun areContentsTheSame(
            oldItem: DashboardHistoryModel,
            newItem: DashboardHistoryModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardHistoryModelViewHolder {
        return DashboardHistoryModelViewHolder(
            ItemHomeHistoryBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardHistoryModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class DashboardHistoryModelViewHolder(private val binding: ItemHomeHistoryBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: DashboardHistoryModel) {
            binding.txtFrom.text = item.from
            binding.txtTo.text = item.to
            binding.txtSchedule.text = item.schedule
        }
    }

    fun onClick(listener: (position: Int, item: DashboardHistoryModel) -> Unit) {
        _onClick = listener
    }
}