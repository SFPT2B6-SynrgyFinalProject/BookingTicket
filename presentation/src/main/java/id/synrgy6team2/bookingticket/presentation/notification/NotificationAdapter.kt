package id.synrgy6team2.bookingticket.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.synrgy6team2.bookingticket.presentation.databinding.ItemNotificationBinding
import javax.inject.Inject

class NotificationAdapter :
    ListAdapter<NotificationModel, NotificationAdapter.NotificationModelViewHolder>(
        NotificationModelComparator
    ) {

    private var _onClick: ((position: Int, item: NotificationModel) -> Unit)? = null

    private object NotificationModelComparator : DiffUtil.ItemCallback<NotificationModel>() {
        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem.img == newItem.img
        }

        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationModelViewHolder {
        return NotificationModelViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class NotificationModelViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: NotificationModel) {
            binding.ivImage.setImageResource(item.img)
            binding.tvContent.text = item.description
        }
    }

    fun onClick(listener: (position: Int, item: NotificationModel) -> Unit) {
        _onClick = listener
    }
}