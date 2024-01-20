package id.synrgy6team2.bookingticket.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHomeRecomendedBookingBinding

class DashboardRecomendedAdapter :
    ListAdapter<DashboardRecomendedModel, DashboardRecomendedAdapter.DashboardRecomendedViewHolder>(
        DashboardRecomendedComparator
    ) {

    private var _onClick: ((position: Int, item: DashboardRecomendedModel) -> Unit)? = null

    private object DashboardRecomendedComparator : DiffUtil.ItemCallback<DashboardRecomendedModel>() {
        override fun areItemsTheSame(
            oldItem: DashboardRecomendedModel,
            newItem: DashboardRecomendedModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: DashboardRecomendedModel,
            newItem: DashboardRecomendedModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardRecomendedViewHolder {
        return DashboardRecomendedViewHolder(
            ItemHomeRecomendedBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardRecomendedViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class DashboardRecomendedViewHolder(private val binding: ItemHomeRecomendedBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: DashboardRecomendedModel) {
            binding.imgHomeRecomended.setImageResource(item.img)
            binding.txtTitle.text = item.title
            binding.txtDescription.text = item.description
        }
    }

    fun onClick(listener: (position: Int, item: DashboardRecomendedModel) -> Unit) {
        _onClick = listener
    }
}