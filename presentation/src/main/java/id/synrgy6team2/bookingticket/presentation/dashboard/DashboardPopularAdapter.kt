package id.synrgy6team2.bookingticket.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHomePopularDestinationBinding
import javax.inject.Inject

class DashboardPopularAdapter @Inject constructor() : ListAdapter<DashboardPopularModel, DashboardPopularAdapter.DashboardPopularModelViewHolder>(
        DashboardPopularModelComparator
    ) {

    private var _onClick: ((position: Int, item: DashboardPopularModel) -> Unit)? = null

    private object DashboardPopularModelComparator :
        DiffUtil.ItemCallback<DashboardPopularModel>() {
        override fun areItemsTheSame(
            oldItem: DashboardPopularModel,
            newItem: DashboardPopularModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: DashboardPopularModel,
            newItem: DashboardPopularModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardPopularModelViewHolder {
        return DashboardPopularModelViewHolder(
            ItemHomePopularDestinationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardPopularModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class DashboardPopularModelViewHolder(private val binding: ItemHomePopularDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: DashboardPopularModel) {
            binding.txtTitle.text = item.title
        }
    }

    fun onClick(listener: (position: Int, item: DashboardPopularModel) -> Unit) {
        _onClick = listener
    }
}