package id.synrgy6team2.bookingticket.presentation.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.synrgy6team2.bookingticket.presentation.databinding.ItemHomeMainMenuBinding

class DashboardMainMenuAdapter :
    ListAdapter<DashboardMainMenuModel, DashboardMainMenuAdapter.DashboardMainMenuModelViewHolder>(
        DashboardMainMenuModelComparator
    ) {

    private var _onClick: ((position: Int, item: DashboardMainMenuModel) -> Unit)? = null

    private object DashboardMainMenuModelComparator :
        DiffUtil.ItemCallback<DashboardMainMenuModel>() {
        override fun areItemsTheSame(
            oldItem: DashboardMainMenuModel,
            newItem: DashboardMainMenuModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: DashboardMainMenuModel,
            newItem: DashboardMainMenuModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardMainMenuModelViewHolder {
        return DashboardMainMenuModelViewHolder(
            ItemHomeMainMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DashboardMainMenuModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class DashboardMainMenuModelViewHolder(private val binding: ItemHomeMainMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: DashboardMainMenuModel) {
            binding.imgHomeMenuMain.setImageResource(item.img)
            binding.txtHomeMenuMain.text = item.title
        }
    }

    fun onClick(listener: (position: Int, item: DashboardMainMenuModel) -> Unit) {
        _onClick = listener
    }
}